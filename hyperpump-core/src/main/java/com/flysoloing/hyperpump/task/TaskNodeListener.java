package com.flysoloing.hyperpump.task;

import com.flysoloing.hyperpump.common.Constants;
import com.flysoloing.hyperpump.common.TaskStatus;
import com.flysoloing.hyperpump.listener.AbstractNodeListener;
import com.flysoloing.hyperpump.registry.RegistryCenter;
import com.flysoloing.hyperpump.scheduler.SchedulerNode;
import com.flysoloing.hyperpump.util.HPNodeUtils;
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
 * @since 2016-05-26 01:07:27
 */
public class TaskNodeListener extends AbstractNodeListener<TaskNode> {

    private static final Logger logger = LoggerFactory.getLogger(TaskNodeListener.class);

    public TaskNodeListener(RegistryCenter registryCenter, TaskNode taskNode) {
        super(registryCenter, taskNode);
    }

    protected void dataChanged(RegistryCenter registryCenter, TaskNode taskNode, TreeCacheEvent event, String path) {
        ChildData data = event.getData();
        TreeCacheEvent.Type type = event.getType();
        if (data != null) {
            String value = new String(data.getData(), Charset.forName(Constants.CHARSET_NAME_UTF8));
            logger.info("The task node listener - '{}' received event, type = {}, path = {}, value = {}", taskNode.getRootNodePath(), type, path, value);

            String taskName = StringUtils.isBlank(path) ? "" : HPNodeUtils.parseTaskName(path);

            //条件：type = NODE_UPDATED, path = /TASKS/TASK_${taskName}/taskStatus, value = running
            if (path.equals(taskNode.getTaskStatusNodePath()) && type == TreeCacheEvent.Type.NODE_UPDATED && value.equals(TaskStatus.RUNNING.getStatus())) {
                electSchedulerNode(registryCenter, taskNode, taskName);
            }
            //条件：type = NODE_UPDATED, path = /TASKS/TASK_${taskName}/taskStatus, value = completed
            if (path.equals(taskNode.getTaskStatusNodePath()) && type == TreeCacheEvent.Type.NODE_UPDATED && value.equals(TaskStatus.COMPLETED.getStatus())) {
                resetTaskStatus(registryCenter, taskNode);
            }
        }
    }

    /**
     * 选举当前任务的调度节点
     *
     * @param registryCenter 注册中心
     */
    private void electSchedulerNode(RegistryCenter registryCenter, TaskNode taskNode, String taskName) {
        //根据当前任务种类（一次性or重复性）和类型（简单类型or批量类型or顺序批量类型）????
        //获取所有的状态为正常的SchedulerNode列表，依据任务类型和策略，选择一个或多个SchedulerNode
        logger.info("got it, do whatever u want to do");
        List<String> children = registryCenter.getChildren(HPNodeUtils.getPath(Constants.NODE_NAMESPACE_SCHEDULERS));

        if (children.isEmpty()) {
            resetTaskStatus(registryCenter, taskNode);
            return;
        }
        if (StringUtils.isBlank(taskName)) {
            resetTaskStatus(registryCenter, taskNode);
            return;
        }

        //根据特定策略选择一个SchedulerNode
        String schedulerNodePath = children.get(0);
        SchedulerNode schedulerNode = HPNodeUtils.restoreSchedulerNode(schedulerNodePath);

        //尝试获取这个SchedulerNode的锁，并执行往其taskList节点增加新任务，采取事务的方式创建
        if (schedulerNode != null) {
            registryCenter.persist(schedulerNode.getTaskClassNodePath(taskName), registryCenter.get(taskNode.getTaskClassNodePath()));
            registryCenter.persist(schedulerNode.getTaskTypeNodePath(taskName), registryCenter.get(taskNode.getTaskTypeNodePath()));
            registryCenter.persist(schedulerNode.getBatchNoNodePath(taskName), registryCenter.get(taskNode.getBatchNoNodePath()));
            registryCenter.persist(schedulerNode.getTaskStatusNodePath(taskName), TaskStatus.RUNNING.getStatus());
        }
    }

    /**
     * 将当前任务状态由completed修改为ready状态
     *
     * @param registryCenter 注册中心
     * @param taskNode 任务节点
     */
    private void resetTaskStatus(RegistryCenter registryCenter, TaskNode taskNode) {
        //更新TaskNode节点的任务状态为待执行状态（ready）
        logger.info("Reset completed task status to ready");
        registryCenter.update(taskNode.getTaskStatusNodePath(), TaskStatus.READY.getStatus());
    }
}
