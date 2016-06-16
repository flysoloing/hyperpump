package com.flysoloing.hyperpump.executor;

import com.flysoloing.hyperpump.base.Node;
import com.flysoloing.hyperpump.common.Constants;
import com.flysoloing.hyperpump.common.TaskStatus;
import com.flysoloing.hyperpump.listener.AbstractNodeListener;
import com.flysoloing.hyperpump.registry.RegistryCenter;
import com.flysoloing.hyperpump.util.HPNodeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.curator.framework.CuratorFramework;
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

            String taskName = StringUtils.isBlank(path) ? "" : HPNodeUtils.parseTaskName(path);

            String regEx = "/EXECUTORS/EXECUTOR_.*/taskArea/TASK_.*/taskStatus";
            Pattern pattern = Pattern.compile(regEx);
            Matcher matcher = pattern.matcher(path);

            //条件：type = NODE_ADDED, path = /EXECUTORS/EXECUTOR_${IP:PID:OBJ_NAME}/taskArea/TASK_${taskName}/taskStatus, value = running
            if (matcher.matches() && type == TreeCacheEvent.Type.NODE_ADDED && value.equals(TaskStatus.RUNNING.getStatus())) {
                executeTaskClass();
            }

            //条件：type = NODE_UPDATED, path = /EXECUTORS/EXECUTOR_${IP:PID:OBJ_NAME}/taskArea/TASK_${taskName}/taskStatus, value = completed
            if (matcher.matches() && type == TreeCacheEvent.Type.NODE_UPDATED && value.equals(TaskStatus.COMPLETED.getStatus())) {
                resetTaskStatus();
                clearTaskArea();
            }
        }
    }

    private void executeTaskClass() {
        //开始执行对应的TaskClass任务，任务执行完后回调更新ExecutorNode节点的状态
    }

    private void resetTaskStatus() {
        //更新对应的SchedulerNode节点的任务状态为已完成（completed）
    }

    private void clearTaskArea() {
        //清除任务区里已完成的任务
    }
}
