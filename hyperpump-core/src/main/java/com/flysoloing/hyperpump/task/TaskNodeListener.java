package com.flysoloing.hyperpump.task;

import com.flysoloing.hyperpump.common.Constants;
import com.flysoloing.hyperpump.common.TaskStatus;
import com.flysoloing.hyperpump.listener.AbstractNodeListener;
import com.flysoloing.hyperpump.registry.RegistryCenter;
import com.flysoloing.hyperpump.scheduler.SchedulerNode;
import com.flysoloing.hyperpump.util.HPNodeUtils;
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
            logger.info("The 'TaskNodeListener' received event, type = {}, path = {}, value = {}", type, path, value);

            if (path.equals(taskNode.getTaskStatusNodePath()) && type == TreeCacheEvent.Type.NODE_UPDATED && value.equals(TaskStatus.RUNNING.getStatus())) {
                //如果type=NODE_UPDATED, path=/TASKS/TASK_hptasktest/status, data=running
                //获取所有的状态为正常的SchedulerNode列表，依据任务类型和策略，选择一个或多个SchedulerNode
                logger.info("got it, do whatever u want to do");
                List<String> children = registryCenter.getChildren(HPNodeUtils.getPath(Constants.NODE_NAMESPACE_SCHEDULERS));
                //根据特定策略选择一个SchedulerNode
                //尝试获取这个SchedulerNode的锁，并执行往其taskList节点增加新任务
                //TODO
                String schedulerNodePath = children.get(0);
                SchedulerNode schedulerNode = HPNodeUtils.restoreSchedulerNode(schedulerNodePath);
                if (schedulerNode != null) {
                    //TODO
//                    registryCenter.persist(schedulerNode.getTaskNameNodePath(""), "");
                }
            }
        }

//        for (Map.Entry entry : registryCenter.getTreeCacheMap().entrySet()) {
//            logger.info(entry.getKey() + " - " + entry.getValue());
//        }

//        logger.info("-------------------------------{}", taskNode.getRootNodePath());
    }
}
