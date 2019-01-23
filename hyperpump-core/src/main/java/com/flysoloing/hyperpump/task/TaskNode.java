package com.flysoloing.hyperpump.task;

import com.flysoloing.hyperpump.node.Node;
import com.flysoloing.hyperpump.common.Constants;
import com.flysoloing.hyperpump.util.NodeUtils;
import com.google.common.base.Joiner;

/**
 * 任务节点
 *
 * @author laitao
 * @date 2016-05-13 01:01:30
 */
public class TaskNode implements Node {

//    /TASK_taskName
//        |---taskClass：任务类
//        |---taskType：任务类型（通过taskClass继承的父类来判断）
//        |---cron：cron表达式
//        |---description：描述
//        |---batchNo：任务批次号
//        |---taskStatus：任务状态，待执行/运行中/已完成
//        |---nodeStatus：节点状态，正常/无效
//        |---
//        |---

    private String namespace = Constants.NODE_NAMESPACE_TASKS;

    private String rootNodeName;

    private String taskClassNodeName = "taskClass";

    private String taskTypeNodeName = "taskType";

    private String cronNodeName = "cron";

    private String descriptionNodeName = "description";

    private String batchNoNodeName = "batchNo";

    private String taskStatusNodeName = "taskStatus";

    private String nodeStatusNodeName = "nodeStatus";

    public TaskNode(TaskNodeConf taskNodeConf) {
        this.rootNodeName = Joiner.on(Constants.SEPARATOR_UNDERLINE).join(Constants.NODE_PREFIX_TASK, taskNodeConf.getTaskName());
    }

    public String getRootNodePath() {
        return NodeUtils.getPath(namespace, rootNodeName);
    }

    public String getTaskClassNodePath() {
        return NodeUtils.getPath(namespace, rootNodeName, taskClassNodeName);
    }

    public String getTaskTypeNodePath() {
        return NodeUtils.getPath(namespace, rootNodeName, taskTypeNodeName);
    }

    public String getCronNodePath() {
        return NodeUtils.getPath(namespace, rootNodeName, cronNodeName);
    }

    public String getDescriptionNodePath() {
        return NodeUtils.getPath(namespace, rootNodeName, descriptionNodeName);
    }

    public String getBatchNoNodePath() {
        return NodeUtils.getPath(namespace, rootNodeName, batchNoNodeName);
    }

    public String getTaskStatusNodePath() {
        return NodeUtils.getPath(namespace, rootNodeName, taskStatusNodeName);
    }

    public String getNodeStatusNodePath() {
        return NodeUtils.getPath(namespace, rootNodeName, nodeStatusNodeName);
    }

}
