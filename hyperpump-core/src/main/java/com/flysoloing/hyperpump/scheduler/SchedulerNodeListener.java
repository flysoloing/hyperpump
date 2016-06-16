package com.flysoloing.hyperpump.scheduler;

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

            String taskName = StringUtils.isBlank(path) ? "" : HPNodeUtils.parseTaskName(path);

            String regEx = "/SCHEDULERS/SCHEDULER_.*/taskQueue/TASK_.*/taskStatus";
            Pattern pattern = Pattern.compile(regEx);
            Matcher matcher = pattern.matcher(path);

            //条件：type = NODE_ADDED, path = /SCHEDULERS/SCHEDULER_${IP:PID:OBJ_NAME}/taskQueue/TASK_${taskName}/taskStatus, value = running
            if (matcher.matches() && type == TreeCacheEvent.Type.NODE_ADDED && value.equals(TaskStatus.RUNNING.getStatus())) {
                dispatchExecutorNode();
            }

            //条件：type = NODE_ADDED, path = /SCHEDULERS/SCHEDULER_${IP:PID:OBJ_NAME}/taskQueue/TASK_${taskName}/taskStatus, value = completed
            if (matcher.matches() && type == TreeCacheEvent.Type.NODE_ADDED && value.equals(TaskStatus.COMPLETED.getStatus())) {
                resetTaskStatus();
                clearTaskQueue();
            }
        }
    }

    private void dispatchExecutorNode() {
        //获取所有状态可用的ExecutorNode节点列表，依据具体的策略和任务类型进行任务分发，并将任务添加到选中的ExecutorNode子节点下
    }

    private void resetTaskStatus() {
        //更新对应的TaskNode节点的任务状态为已完成（completed）
    }

    private void clearTaskQueue() {
        //清除任务队列里已完成的任务
    }
}
