package com.flysoloing.hyperpump.executor;

import com.flysoloing.hyperpump.base.Node;
import com.flysoloing.hyperpump.common.Constants;
import com.flysoloing.hyperpump.util.HPNodeUtils;
import com.google.common.base.Joiner;

/**
 * 执行器节点
 *
 * @author laitao
 * @since 2016-05-13 01:01:04
 */
public class ExecutorNode implements Node {

//    /EXECUTOR_IP_PID_OBJNAME
//        |---ip：ip地址
//        |---pid：端口号
//        |---objName：对象名
//        |---description：描述
//        |---taskArea：任务区
//        |---|---taskName：任务名
//        |---|---|---taskClass：任务类
//        |---|---|---taskType：任务类型
//        |---|---|---batchNo：批次号
//        |---|---|---offset：偏移量？待定
//        |---|---|---taskStatus：任务状态，待执行/运行中/已完成
//        |---nodeStatus：节点状态，正常/无效
//        |---
//        |---

    private String namespace = Constants.NODE_NAMESPACE_EXECUTORS;

    private String rootNodeName;

    private String ipNodeName = "ip";

    private String pidNodeName = "pid";

    private String objNameNodeName = "objName";

    private String descriptionNodeName = "description";

    private String taskAreaNodeName = "taskArea";

    private String taskNameNodeName;

    private String taskClassNodeName = "taskClass";

    private String taskTypeNodeName = "taskType";

    private String batchNoNodeName = "batchNo";

    private String offsetNodeName = "offset";

    private String taskStatusNodeName = "taskStatus";

    private String nodeStatusNodeName = "nodeStatus";

    public ExecutorNode(ExecutorNodeConf executorNodeConf) {
        this.rootNodeName = Joiner.on(Constants.SEPARATOR_UNDERLINE).join(Constants.NODE_PREFIX_EXECUTOR, executorNodeConf.getIp(), executorNodeConf.getPid(), executorNodeConf.getObjName());
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

    public String getTaskAreaNodePath() {
        return HPNodeUtils.getPath(namespace, rootNodeName, taskAreaNodeName);
    }

    public String getTaskNameNodePath(String taskName) {
        taskNameNodeName = taskName;
        return HPNodeUtils.getPath(namespace, rootNodeName, taskAreaNodeName, taskName);
    }

    public String getTaskClassNodePath(String taskName) {
        taskNameNodeName = taskName;
        return HPNodeUtils.getPath(namespace, rootNodeName, taskAreaNodeName, taskName, taskClassNodeName);
    }

    public String getTaskTypeNodePath(String taskName) {
        taskNameNodeName = taskName;
        return HPNodeUtils.getPath(namespace, rootNodeName, taskAreaNodeName, taskName, taskTypeNodeName);
    }

    public String getBatchNoNodePath(String taskName) {
        taskNameNodeName = taskName;
        return HPNodeUtils.getPath(namespace, rootNodeName, taskAreaNodeName, taskName, batchNoNodeName);
    }

    public String getOffsetNodePath(String taskName) {
        taskNameNodeName = taskName;
        return HPNodeUtils.getPath(namespace, rootNodeName, taskAreaNodeName, taskName, offsetNodeName);
    }

    public String getTaskStatusNodePath(String taskName) {
        taskNameNodeName = taskName;
        return HPNodeUtils.getPath(namespace, rootNodeName, taskAreaNodeName, taskName, taskStatusNodeName);
    }

    public String getNodeStatusNodePath() {
        return HPNodeUtils.getPath(namespace, rootNodeName, nodeStatusNodeName);
    }

}
