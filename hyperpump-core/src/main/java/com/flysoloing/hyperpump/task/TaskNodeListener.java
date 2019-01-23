package com.flysoloing.hyperpump.task;

import com.flysoloing.hyperpump.common.Constants;
import com.flysoloing.hyperpump.common.TaskStatus;
import com.flysoloing.hyperpump.listener.AbstractNodeListener;
import com.flysoloing.hyperpump.registry.RegistryCenter;
import com.flysoloing.hyperpump.scheduler.SchedulerNode;
import com.flysoloing.hyperpump.util.NodeUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.List;

/**
 * 任务节点监听器
 *
 * @author laitao
 * @date 2016-05-26 01:07:27
 */
public class TaskNodeListener extends AbstractNodeListener<TaskNode> {

    private static final Logger logger = LoggerFactory.getLogger(TaskNodeListener.class);

    private static final int DEFAULT_LOCK_ACQUIRE_TIME_MS = 2 * 1000;

    public TaskNodeListener(RegistryCenter registryCenter, TaskNode taskNode) {
        super(registryCenter, taskNode);
    }

    protected void dataChanged(RegistryCenter registryCenter, TaskNode taskNode, TreeCacheEvent event, String path) {
        ChildData data = event.getData();
        TreeCacheEvent.Type type = event.getType();
        if (data != null) {
            String value = new String(data.getData(), Charset.forName(Constants.CHARSET_NAME_UTF8));
            String taskNodeName = StringUtils.isBlank(path) ? "" : NodeUtils.parseTaskNodeName(path);
            logger.debug("The task node listener - '{}' received event, type = {}, path = {}, value = {}", taskNodeName, type, path, value);

            //条件：type = NODE_UPDATED, path = /TASKS/TASK_${taskName}/taskStatus, value = running
            if (path.equals(taskNode.getTaskStatusNodePath()) && type == TreeCacheEvent.Type.NODE_UPDATED && value.equals(TaskStatus.RUNNING.getStatus())) {
                electSchedulerNode(registryCenter, taskNode, taskNodeName);
            }
            //条件：type = NODE_UPDATED, path = /TASKS/TASK_${taskName}/taskStatus, value = completed
            if (path.equals(taskNode.getTaskStatusNodePath()) && type == TreeCacheEvent.Type.NODE_UPDATED && value.equals(TaskStatus.COMPLETED.getStatus())) {
                resetTaskStatus(registryCenter, taskNode, taskNodeName);
            }
        }
    }

    /**
     * 选举当前任务的调度节点
     *
     * @param registryCenter 注册中心
     * @param taskNode 任务节点
     * @param taskNodeName 任务节点名称
     */
    private void electSchedulerNode(RegistryCenter registryCenter, TaskNode taskNode, String taskNodeName) {
        //根据当前任务种类（一次性or重复性）和类型（简单类型or批量类型or顺序批量类型）????
        //获取所有的状态为正常的SchedulerNode列表，依据任务类型和策略，选择一个或多个SchedulerNode
        logger.info("The task node - '{}' is electing scheduler node", taskNodeName);
        List<String> children = registryCenter.getChildren(NodeUtils.getPath(Constants.NODE_NAMESPACE_SCHEDULERS));

        if (children.isEmpty()) {
            resetTaskStatus(registryCenter, taskNode, taskNodeName);
            return;
        }
        if (StringUtils.isBlank(taskNodeName)) {
            resetTaskStatus(registryCenter, taskNode, taskNodeName);
            return;
        }

        //根据特定策略选择一个SchedulerNode
        String schedulerNodeName = children.get(RandomUtils.nextInt(0, (children.size()-1)));
        SchedulerNode schedulerNode = NodeUtils.restoreSchedulerNode(schedulerNodeName);

        //尝试获取这个SchedulerNode的锁，并执行往其taskList节点增加新任务，采取事务的方式创建
        //TODO 锁
        if (schedulerNode != null && !registryCenter.isExisted(schedulerNode.getTaskNameNodePath(taskNodeName))) {
            registryCenter.persist(schedulerNode.getTaskClassNodePath(taskNodeName), registryCenter.get(taskNode.getTaskClassNodePath()));
            registryCenter.persist(schedulerNode.getTaskTypeNodePath(taskNodeName), registryCenter.get(taskNode.getTaskTypeNodePath()));
            registryCenter.persist(schedulerNode.getBatchNoNodePath(taskNodeName), registryCenter.get(taskNode.getBatchNoNodePath()));
            registryCenter.persist(schedulerNode.getTaskStatusNodePath(taskNodeName), TaskStatus.RUNNING.getStatus());
        }
    }

    /**
     * 将当前任务状态由completed修改为ready状态
     *
     * @param registryCenter 注册中心
     * @param taskNode 任务节点
     * @param taskName 任务名称
     */
    private void resetTaskStatus(RegistryCenter registryCenter, TaskNode taskNode, String taskName) {
        //更新TaskNode节点的任务状态为待执行状态（ready）
        logger.info("The task node - '{}' reset to ready", taskName);
        registryCenter.update(taskNode.getTaskStatusNodePath(), TaskStatus.READY.getStatus());
    }
}
