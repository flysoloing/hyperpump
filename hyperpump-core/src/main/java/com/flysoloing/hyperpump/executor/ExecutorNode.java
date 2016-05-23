package com.flysoloing.hyperpump.executor;

import com.flysoloing.hyperpump.common.Constants;
import com.flysoloing.hyperpump.util.HPNodeUtils;
import com.google.common.base.Joiner;

/**
 * @author laitao
 * @since 2016-05-13 01:01:04
 */
public class ExecutorNode {

//    /EXECUTOR_IP_PORT_OBJNAME
//        |---ip：ip地址
//        |---port：端口号
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

    private String rootNodeName;

    private String ipNodeName = "ip";

    private String portNodeName = "port";

    private String objNameNodeName = "objName";

    private String descriptionNodeName = "description";

    private String taskNameNodeName = "taskName";

    private String taskClassNodeName = "taskClass";

    private String batchNoNodeName = "batchNo";

    private String taskStatusNodeName = "taskStatus";

    private String offsetNodeName = "offset";

    private String statusNodeName = "status";

    public ExecutorNode(ExecutorConf executorConf) {
        this.rootNodeName = Joiner.on(Constants.SEPARATOR_UNDERLINE).join(Constants.NODE_PREFIX_EXECUTOR, executorConf.getIp(), String.valueOf(executorConf.getPort()), executorConf.getObjName());
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

    public String getTaskNameNodePath() {
        return HPNodeUtils.getPath(rootNodeName, taskNameNodeName);
    }

    public String getTaskClassNodePath() {
        return HPNodeUtils.getPath(rootNodeName, taskClassNodeName);
    }

    public String getBatchNoNodePath() {
        return HPNodeUtils.getPath(rootNodeName, batchNoNodeName);
    }

    public String getTaskStatusNodePath() {
        return HPNodeUtils.getPath(rootNodeName, taskStatusNodeName);
    }

    public String getOffsetNodePath() {
        return HPNodeUtils.getPath(rootNodeName, offsetNodeName);
    }

    public String getStatusNodePath() {
        return HPNodeUtils.getPath(rootNodeName, statusNodeName);
    }

}
