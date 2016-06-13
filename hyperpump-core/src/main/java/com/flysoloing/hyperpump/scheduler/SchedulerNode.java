package com.flysoloing.hyperpump.scheduler;

import com.flysoloing.hyperpump.base.Node;
import com.flysoloing.hyperpump.common.Constants;
import com.flysoloing.hyperpump.util.HPNodeUtils;
import com.google.common.base.Joiner;

/**
 * 调度器节点
 *
 * @author laitao
 * @since 2016-05-13 01:01:20
 */
public class SchedulerNode implements Node {

//    /SCHEDULER_IP_PID_OBJNAME
//        |---ip：ip地址
//        |---pid：端口号
//        |---objName：对象名
//        |---description：描述
//        |---taskList
//        |---|---taskName
//        |---|---|---taskClass：任务类
//        |---|---|---taskType：任务类型
//        |---|---|---batchNo：任务批次号
//        |---|---|---taskStatus：任务状态，待执行/运行中/已完成
//        |---|---taskName
//        |---|---|---taskClass：任务类
//        |---|---|---taskType：任务类型
//        |---|---|---batchNo：任务批次号
//        |---|---|---taskStatus：任务状态，待执行/运行中/已完成
//        |---|---taskName
//        |---|---|---taskClass：任务类
//        |---|---|---taskType：任务类型
//        |---|---|---batchNo：任务批次号
//        |---|---|---taskStatus：任务状态，待执行/运行中/已完成
//        |---nodeStatus：节点状态，正常/无效
//        |---
//        |---

    private String namespace = Constants.NODE_NAMESPACE_SCHEDULERS;

    private String rootNodeName;

    private String ipNodeName = "ip";

    private String pidNodeName = "pid";

    private String objNameNodeName = "objName";

    private String descriptionNodeName = "description";

    private String taskListNodeName = "taskList";

    private String taskNameNodeName;

    private String taskClassNodeName = "taskClass";

    private String taskTypeNodeName = "taskType";

    private String batchNoNodeName = "batchNo";

    private String taskStatusNodeName = "taskStatus";

    private String nodeStatusNodeName = "nodeStatus";

    public SchedulerNode(SchedulerNodeConf schedulerNodeConf) {
        this.rootNodeName = Joiner.on(Constants.SEPARATOR_UNDERLINE).join(Constants.NODE_PREFIX_SCHEDULER, schedulerNodeConf.getIp(), schedulerNodeConf.getPid(), schedulerNodeConf.getObjName());
    }

    public String getRootNodePath() {
        return HPNodeUtils.getPath(namespace, rootNodeName);
    }

    public String getIpNodePath() {
        return HPNodeUtils.getPath(namespace, rootNodeName, ipNodeName);
    }

    public String getPidNodePath() {
        return HPNodeUtils.getPath(namespace, rootNodeName, pidNodeName);
    }

    public String getObjNameNodePath() {
        return HPNodeUtils.getPath(namespace, rootNodeName, objNameNodeName);
    }

    public String getDescriptionNodePath() {
        return HPNodeUtils.getPath(namespace, rootNodeName, descriptionNodeName);
    }

    public String getTaskListNodePath() {
        return HPNodeUtils.getPath(namespace, rootNodeName, taskListNodeName);
    }

    public String getTaskNameNodePath(String taskName) {
        taskNameNodeName = taskName;
        return HPNodeUtils.getPath(namespace, rootNodeName, taskListNodeName, taskNameNodeName);
    }

    public String getTaskClassNodePath(String taskName) {
        taskNameNodeName = taskName;
        return HPNodeUtils.getPath(namespace, rootNodeName, taskListNodeName, taskName, taskClassNodeName);
    }

    public String getTaskTypeNodePath(String taskName) {
        taskNameNodeName = taskName;
        return HPNodeUtils.getPath(namespace, rootNodeName, taskListNodeName, taskName, taskTypeNodeName);
    }

    public String getBatchNoNodePath(String taskName) {
        taskNameNodeName = taskName;
        return HPNodeUtils.getPath(namespace, rootNodeName, taskListNodeName, taskName, batchNoNodeName);
    }

    public String getTaskStatusNodePath(String taskName) {
        taskNameNodeName = taskName;
        return HPNodeUtils.getPath(namespace, rootNodeName, taskListNodeName, taskName, taskStatusNodeName);
    }

    public String getNodeStatusNodePath() {
        return HPNodeUtils.getPath(namespace, rootNodeName, nodeStatusNodeName);
    }

}
