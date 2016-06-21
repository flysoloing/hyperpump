package com.flysoloing.hyperpump.scheduler;

import com.flysoloing.hyperpump.common.Constants;
import com.flysoloing.hyperpump.common.TaskStatus;
import com.flysoloing.hyperpump.executor.ExecutorNode;
import com.flysoloing.hyperpump.listener.AbstractNodeListener;
import com.flysoloing.hyperpump.registry.RegistryCenter;
import com.flysoloing.hyperpump.task.TaskNode;
import com.flysoloing.hyperpump.util.NodeUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 调度器节点监听器
 *
 * @author laitao
 * @since 2016-05-26 01:40:59
 */
public class SchedulerNodeListener extends AbstractNodeListener<SchedulerNode> {

    private static final Logger logger = LoggerFactory.getLogger(SchedulerNodeListener.class);

    public SchedulerNodeListener(RegistryCenter registryCenter, SchedulerNode schedulerNode) {
        super(registryCenter, schedulerNode);
    }

    protected void dataChanged(RegistryCenter registryCenter, SchedulerNode schedulerNode, TreeCacheEvent event, String path) {
        ChildData data = event.getData();
        TreeCacheEvent.Type type = event.getType();
        if (data != null) {
            String value = new String(data.getData(), Charset.forName(Constants.CHARSET_NAME_UTF8));
            String taskNodeName = StringUtils.isBlank(path) ? "" : NodeUtils.parseTaskNodeName(path);
            String schedulerNodeName = StringUtils.isBlank(path) ? "" : NodeUtils.parseSchedulerNodeName(path);
            logger.debug("The scheduler node listener - '{}' received event, type = {}, path = {}, value = {}", schedulerNodeName, type, path, value);

            String regEx = "/SCHEDULERS/SCHEDULER_.*/taskQueue/TASK_.*/taskStatus";
            Pattern pattern = Pattern.compile(regEx);
            Matcher matcher = pattern.matcher(path);

            //条件：type = NODE_ADDED, path = /SCHEDULERS/SCHEDULER_${IP:PID:OBJ_NAME}/taskQueue/TASK_${taskName}/taskStatus, value = running
            if (matcher.matches() && type == TreeCacheEvent.Type.NODE_ADDED && value.equals(TaskStatus.RUNNING.getStatus())) {
                dispatchExecutorNode(registryCenter, schedulerNode, taskNodeName);
            }

            //条件：type = NODE_UPDATED, path = /SCHEDULERS/SCHEDULER_${IP:PID:OBJ_NAME}/taskQueue/TASK_${taskName}/taskStatus, value = completed
            if (matcher.matches() && type == TreeCacheEvent.Type.NODE_UPDATED && value.equals(TaskStatus.COMPLETED.getStatus())) {
                resetTaskStatus(registryCenter, taskNodeName);
                clearTaskQueue(registryCenter, schedulerNode, taskNodeName);
            }
        }
    }

    /**
     * 将调度器节点持有的任务分发给执行器节点，可以根据不同的策略选择一个多多个执行器节点
     *
     * @param registryCenter 注册中心
     * @param schedulerNode 调度器节点
     * @param taskNodeName 任务节点名称
     */
    private void dispatchExecutorNode(RegistryCenter registryCenter, SchedulerNode schedulerNode, String taskNodeName) {
        //获取所有状态可用的ExecutorNode节点列表，依据具体的策略和任务类型进行任务分发，并将任务添加到选中的ExecutorNode子节点下
        logger.info("dispatchExecutorNode() {}", taskNodeName);
        //TODO
        List<String> children = registryCenter.getChildren(NodeUtils.getPath(Constants.NODE_NAMESPACE_EXECUTORS));

        if (children.isEmpty()) {
            logger.info("");
            return;
        }
        if (StringUtils.isBlank(taskNodeName)) {
            logger.info("");
            return;
        }

        String executorNodeName = children.get(RandomUtils.nextInt(0, (children.size()-1)));
        ExecutorNode executorNode = NodeUtils.restoreExecutorNode(executorNodeName);

        //尝试获取锁后再执行，避免并发情况下竞争
        if (executorNode != null && !registryCenter.isExisted(executorNode.getTaskNameNodePath(taskNodeName))) {
            registryCenter.persist(executorNode.getTaskClassNodePath(taskNodeName), registryCenter.get(schedulerNode.getTaskClassNodePath(taskNodeName)));
            registryCenter.persist(executorNode.getTaskTypeNodePath(taskNodeName), registryCenter.get(schedulerNode.getTaskTypeNodePath(taskNodeName)));
            registryCenter.persist(executorNode.getBatchNoNodePath(taskNodeName), registryCenter.get(schedulerNode.getBatchNoNodePath(taskNodeName)));
            registryCenter.persist(executorNode.getOffsetNodePath(taskNodeName), "");
            registryCenter.persist(executorNode.getTaskRefererNodePath(taskNodeName), NodeUtils.parseSchedulerNodeName(schedulerNode.getRootNodePath()));
            registryCenter.persist(executorNode.getTaskStatusNodePath(taskNodeName), TaskStatus.RUNNING.getStatus());
        }
    }

    /**
     * 更新对应的TaskNode节点的任务状态为已完成（completed）
     *
     * @param registryCenter 注册中心
     * @param taskNodeName 任务节点名称
     */
    private void resetTaskStatus(RegistryCenter registryCenter, String taskNodeName) {
        logger.info("resetTaskStatus() {}", taskNodeName);
        TaskNode taskNode = NodeUtils.restoreTaskNode(taskNodeName);
        if (taskNode == null)
            return;
        registryCenter.update(taskNode.getTaskStatusNodePath(), TaskStatus.COMPLETED.getStatus());
    }

    /**
     * 清除任务队列里已完成（completed）的任务
     *
     * @param registryCenter 注册中心
     * @param schedulerNode 调度器节点
     * @param taskNodeName 任务节点名称
     */
    private void clearTaskQueue(RegistryCenter registryCenter, SchedulerNode schedulerNode, String taskNodeName) {
        logger.info("clearTaskQueue() {}", taskNodeName);
        registryCenter.remove(schedulerNode.getTaskNameNodePath(taskNodeName));
    }
}
