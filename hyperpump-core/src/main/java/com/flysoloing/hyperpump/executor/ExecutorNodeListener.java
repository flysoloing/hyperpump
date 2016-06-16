package com.flysoloing.hyperpump.executor;

import com.flysoloing.hyperpump.common.Constants;
import com.flysoloing.hyperpump.common.TaskStatus;
import com.flysoloing.hyperpump.listener.AbstractNodeListener;
import com.flysoloing.hyperpump.registry.RegistryCenter;
import com.flysoloing.hyperpump.scheduler.SchedulerNode;
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
 * 执行器节点监听器
 *
 * @author laitao
 * @since 2016-05-26 01:41:36
 */
public class ExecutorNodeListener extends AbstractNodeListener<ExecutorNode> {

    private static final Logger logger = LoggerFactory.getLogger(ExecutorNodeService.class);

    public ExecutorNodeListener(RegistryCenter registryCenter, ExecutorNode executorNode) {
        super(registryCenter, executorNode);
    }

    protected void dataChanged(RegistryCenter registryCenter, ExecutorNode executorNode, TreeCacheEvent event, String path) {
        ChildData data = event.getData();
        TreeCacheEvent.Type type = event.getType();
        if (data != null) {
            String value = new String(data.getData(), Charset.forName(Constants.CHARSET_NAME_UTF8));
            logger.info("The executor node listener - '{}' received event, type = {}, path = {}, value = {}", executorNode.getRootNodePath(), type, path, value);

            String taskName = StringUtils.isBlank(path) ? "" : NodeUtils.parseTaskNodeName(path);

            String regEx = "/EXECUTORS/EXECUTOR_.*/taskArea/TASK_.*/taskStatus";
            Pattern pattern = Pattern.compile(regEx);
            Matcher matcher = pattern.matcher(path);

            //条件：type = NODE_ADDED, path = /EXECUTORS/EXECUTOR_${IP:PID:OBJ_NAME}/taskArea/TASK_${taskName}/taskStatus, value = running
            if (matcher.matches() && type == TreeCacheEvent.Type.NODE_ADDED && value.equals(TaskStatus.RUNNING.getStatus())) {
                executeTaskClass(taskName);
            }

            //条件：type = NODE_UPDATED, path = /EXECUTORS/EXECUTOR_${IP:PID:OBJ_NAME}/taskArea/TASK_${taskName}/taskStatus, value = completed
            if (matcher.matches() && type == TreeCacheEvent.Type.NODE_UPDATED && value.equals(TaskStatus.COMPLETED.getStatus())) {
                resetTaskStatus(registryCenter, executorNode, taskName);
                clearTaskArea(registryCenter, executorNode, taskName);
            }
        }
    }

    private void executeTaskClass(String taskName) {
        //开始执行对应的TaskClass任务，任务执行完后回调更新ExecutorNode节点的状态
        logger.info("executeTaskClass() {}", taskName);
        //TODO
    }

    private void resetTaskStatus(RegistryCenter registryCenter, ExecutorNode executorNode, String taskName) {
        //更新对应的SchedulerNode节点的任务状态为已完成（completed）
        logger.info("resetTaskStatus() {}", taskName);
        String schedulerNodeName = registryCenter.get(executorNode.getTaskRefererNodePath(taskName));
        SchedulerNode schedulerNode = NodeUtils.restoreSchedulerNode(schedulerNodeName);
        if (schedulerNode == null)
            return;
        registryCenter.update(schedulerNode.getTaskStatusNodePath(taskName), TaskStatus.COMPLETED.getStatus());
    }

    private void clearTaskArea(RegistryCenter registryCenter, ExecutorNode executorNode, String taskName) {
        //清除任务区里已完成的任务
        logger.info("clearTaskArea() {}", taskName);
        registryCenter.remove(executorNode.getTaskNameNodePath(taskName));
    }
}
