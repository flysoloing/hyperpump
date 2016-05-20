package com.flysoloing.hyperpump.executor;

/**
 * @author laitao
 * @since 2016-05-13 01:01:04
 */
public class ExecutorNode {

//    /EXECUTOR_${IP:PORT:OBJ_NAME}
//        |---ip：IP地址
//        |---port：端口号
//        |---objName：对象名
//        |---taskName：任务名
//        |---batchNo：批次号
//        |---taskClass：任务类
//        |---offset：偏移量
//        |---description：描述
//        |---status：状态
//        |---
//        |---

    private ExecutorConf executorConf;

    private String rootNodePath;

    private String ipNodePath;

    private String portNodePath;

    private String objNameNodePath;

    private String taskListNodePath;

    private String descriptionNodePath;

    private String statusNodePath = "status";

    public ExecutorNode(ExecutorConf executorConf) {
        this.executorConf = executorConf;
    }
}
