package com.flysoloing.hyperpump.scheduler;

import com.flysoloing.hyperpump.base.Node;
import com.flysoloing.hyperpump.common.Constants;
import com.flysoloing.hyperpump.common.TaskStatus;
import com.flysoloing.hyperpump.listener.AbstractNodeListener;
import com.flysoloing.hyperpump.registry.RegistryCenter;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;

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

            //条件：type = NODE_ADDED, path = /SCHEDULERS/SCHEDULER_${IP:PID:OBJ_NAME}/taskQueue/TASK_${taskName}/taskStatus, value = running
            //通过正则表达式来匹配path
//            if (path.equals(schedulerNode.getTaskStatusNodePath()) && type == TreeCacheEvent.Type.NODE_UPDATED && value.equals(TaskStatus.RUNNING.getStatus())) {
                executorNodeDispatch();
//            }

            //type = NODE_ADDED, path = /SCHEDULERS/SCHEDULER_${IP:PID:OBJ_NAME}/taskQueue/TASK_${taskName}/taskStatus, value = completed
            resetTaskStatus();
        }
    }

    private void executorNodeDispatch() {
        //获取所有状态可用的ExecutorNode节点列表，依据具体的策略和任务类型进行任务分发，并将任务添加到选中的ExecutorNode子节点下
    }

    private void resetTaskStatus() {
        //更新对应的TaskNode节点的任务状态为已完成（completed）
    }
}
