package com.flysoloing.hyperpump.scheduler;

import com.flysoloing.hyperpump.common.Constants;
import com.flysoloing.hyperpump.common.TaskStatus;
import com.flysoloing.hyperpump.listener.AbstractNodeListener;
import com.flysoloing.hyperpump.registry.RegistryCenter;
import com.flysoloing.hyperpump.task.TaskNode;
import com.flysoloing.hyperpump.util.NodeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
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
            logger.info("The scheduler node listener - '{}' received event, type = {}, path = {}, value = {}", schedulerNode.getRootNodePath(), type, path, value);

            String taskName = StringUtils.isBlank(path) ? "" : NodeUtils.parseTaskNodeName(path);

            String regEx = "/SCHEDULERS/SCHEDULER_.*/taskQueue/TASK_.*/taskStatus";
            Pattern pattern = Pattern.compile(regEx);
            Matcher matcher = pattern.matcher(path);

            //条件：type = NODE_ADDED, path = /SCHEDULERS/SCHEDULER_${IP:PID:OBJ_NAME}/taskQueue/TASK_${taskName}/taskStatus, value = running
            if (matcher.matches() && type == TreeCacheEvent.Type.NODE_ADDED && value.equals(TaskStatus.RUNNING.getStatus())) {
                dispatchExecutorNode(taskName);
            }

            //条件：type = NODE_UPDATED, path = /SCHEDULERS/SCHEDULER_${IP:PID:OBJ_NAME}/taskQueue/TASK_${taskName}/taskStatus, value = completed
            if (matcher.matches() && type == TreeCacheEvent.Type.NODE_UPDATED && value.equals(TaskStatus.COMPLETED.getStatus())) {
                resetTaskStatus(registryCenter, taskName);
                clearTaskQueue(registryCenter, schedulerNode, taskName);
            }
        }
    }

    private void dispatchExecutorNode(String taskName) {
        //获取所有状态可用的ExecutorNode节点列表，依据具体的策略和任务类型进行任务分发，并将任务添加到选中的ExecutorNode子节点下
        logger.info("dispatchExecutorNode() {}", taskName);
        //TODO
    }

    private void resetTaskStatus(RegistryCenter registryCenter, String taskName) {
        //更新对应的TaskNode节点的任务状态为已完成（completed）
        logger.info("resetTaskStatus() {}", taskName);
        TaskNode taskNode = NodeUtils.restoreTaskNode(taskName);
        if (taskNode == null)
            return;
        registryCenter.update(taskNode.getTaskStatusNodePath(), TaskStatus.COMPLETED.getStatus());
    }

    private void clearTaskQueue(RegistryCenter registryCenter, SchedulerNode schedulerNode, String taskName) {
        //清除任务队列里已完成的任务
        logger.info("clearTaskQueue() {}", taskName);
        registryCenter.remove(schedulerNode.getTaskNameNodePath(taskName));
    }
}
