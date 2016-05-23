package com.flysoloing.hyperpump.scheduler;

import com.flysoloing.hyperpump.common.Constants;
import com.flysoloing.hyperpump.util.HPNodeUtils;
import com.google.common.base.Joiner;

/**
 * @author laitao
 * @since 2016-05-13 01:01:20
 */
public class SchedulerNode {

//    /SCHEDULER_IP_PORT_OBJNAME
//        |---ip：ip地址
//        |---port：端口号
//        |---objName：对象名
//        |---description：描述
//        |---taskList
//        |---|---taskName
//        |---|---|---taskClass：任务类
//        |---|---|---batchNo：任务批次号
//        |---|---|---taskStatus：状态
//        |---|---taskName
//        |---|---|---taskClass：任务类
//        |---|---|---batchNo：任务批次号
//        |---|---|---taskStatus：状态
//        |---|---taskName
//        |---|---|---taskClass：任务类
//        |---|---|---batchNo：任务批次号
//        |---|---|---taskStatus：状态
//        |---status：状态，有效/无效
//        |---
//        |---

    private String rootNodeName;

    private String ipNodeName = "ip";

    private String portNodeName = "port";

    private String objNameNodeName = "objName";

    private String descriptionNodeName = "description";

    private String taskListNodeName = "taskList";

    private String taskNameNodeName;

    private String taskClassNodeName = "taskClass";

    private String batchNoNodeName = "batchNo";

    private String taskStatusNodeName = "taskStatus";

    private String statusNodeName = "status";

    public SchedulerNode(SchedulerConf schedulerConf) {
        this.rootNodeName = Joiner.on(Constants.SEPARATOR_UNDERLINE).join(Constants.NODE_PREFIX_SCHEDULER, schedulerConf.getIp(), String.valueOf(schedulerConf.getPort()), schedulerConf.getObjName());
    }

    public String getRootNodePath() {
        return HPNodeUtils.getPath(rootNodeName);
    }

    public String getIpNodePath() {
        return HPNodeUtils.getPath(rootNodeName, ipNodeName);
    }

    public String getPortNodePath() {
        return HPNodeUtils.getPath(rootNodeName, portNodeName);
    }

    public String getObjNameNodePath() {
        return HPNodeUtils.getPath(rootNodeName, objNameNodeName);
    }

    public String getDescriptionNodePath() {
        return HPNodeUtils.getPath(rootNodeName, descriptionNodeName);
    }

    public String getTaskListNodePath() {
        return HPNodeUtils.getPath(rootNodeName, taskListNodeName);
    }

    public String getTaskNameNodePath(String taskName) {
        taskNameNodeName = taskName;
        return HPNodeUtils.getPath(rootNodeName, taskListNodeName, taskNameNodeName);
    }

    public String getBatchNoNodePath(String taskName) {
        taskNameNodeName = taskName;
        return HPNodeUtils.getPath(rootNodeName, taskListNodeName, taskName, batchNoNodeName);
    }

    public String getTaskClassNodePath(String taskName) {
        taskNameNodeName = taskName;
        return HPNodeUtils.getPath(rootNodeName, taskListNodeName, taskName, taskClassNodeName);
    }

    public String getTaskStatusNodePath(String taskName) {
        taskNameNodeName = taskName;
        return HPNodeUtils.getPath(rootNodeName, taskListNodeName, taskName, taskStatusNodeName);
    }

    public String getStatusNodePath() {
        return HPNodeUtils.getPath(rootNodeName, statusNodeName);
    }

}
