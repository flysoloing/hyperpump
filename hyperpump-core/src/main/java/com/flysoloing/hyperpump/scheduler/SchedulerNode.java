package com.flysoloing.hyperpump.scheduler;

import com.flysoloing.hyperpump.common.Constants;
import com.google.common.base.Joiner;

/**
 * @author laitao
 * @since 2016-05-13 01:01:20
 */
public class SchedulerNode {

//    /SCHEDULER_${IP:PORT:OBJ_NAME}
//        |---ip：ip地址
//        |---port：端口号
//        |---objName：对象名
//        |---taskList
//        |---|---taskName_batchNo
//        |---|---|---taskClass：任务类
//        |---|---|---status：状态
//        |---|---taskName_batchNo
//        |---|---|---taskClass：任务类
//        |---|---|---status：状态
//        |---|---taskName_batchNo
//        |---|---|---taskClass：任务类
//        |---|---|---status：状态
//        |---description：描述
//        |---status：状态，有效/无效
//        |---
//        |---

    private String rootNodePath;

    private String ipNodePath;

    private String portNodePath;

    private String objNameNodePath;

    private String descriptionNodePath;

    private String taskListNodePath = "taskList";

    private String statusNodePath = "status";

    public SchedulerNode(SchedulerConf schedulerConf) {
        this.rootNodePath = Joiner.on(Constants.SEPARATOR_UNDERLINE).join(Constants.NODE_PREFIX_SCHEDULER, schedulerConf.getIp(), schedulerConf.getPort(), schedulerConf.getObjName());
        //TODO
    }
}
