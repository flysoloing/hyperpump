package com.flysoloing.hyperpump.executor;

import com.flysoloing.hyperpump.common.Constants;
import com.flysoloing.hyperpump.util.HPNodeUtils;
import com.google.common.base.Joiner;

/**
 * @author laitao
 * @since 2016-05-13 01:01:04
 */
public class ExecutorNode {

//    /EXECUTOR_IP_PID_OBJNAME
//        |---ip：ip地址
//        |---pid：端口号
//        |---objName：对象名
//        |---description：描述
//        |---taskName：任务名
//        |---taskClass：任务类
//        |---batchNo：批次号
//        |---taskStatus：任务类
//        |---offset：偏移量？待定
//        |---status：状态
//        |---
//        |---

    private String namespace = "EXECUTORS";

    private String rootNodeName;

    private String ipNodeName = "ip";

    private String pidNodeName = "pid";

    private String objNameNodeName = "objName";

    private String descriptionNodeName = "description";

    private String taskNameNodeName = "taskName";

    private String taskClassNodeName = "taskClass";

    private String batchNoNodeName = "batchNo";

    private String taskStatusNodeName = "taskStatus";

    private String offsetNodeName = "offset";

    private String statusNodeName = "status";

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

    public String getTaskNameNodePath() {
        return HPNodeUtils.getPath(namespace, rootNodeName, taskNameNodeName);
    }

    public String getTaskClassNodePath() {
        return HPNodeUtils.getPath(namespace, rootNodeName, taskClassNodeName);
    }

    public String getBatchNoNodePath() {
        return HPNodeUtils.getPath(namespace, rootNodeName, batchNoNodeName);
    }

    public String getTaskStatusNodePath() {
        return HPNodeUtils.getPath(namespace, rootNodeName, taskStatusNodeName);
    }

    public String getOffsetNodePath() {
        return HPNodeUtils.getPath(namespace, rootNodeName, offsetNodeName);
    }

    public String getStatusNodePath() {
        return HPNodeUtils.getPath(namespace, rootNodeName, statusNodeName);
    }

}
