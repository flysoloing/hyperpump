package com.flysoloing.hyperpump.task;

import com.flysoloing.hyperpump.common.Constants;
import com.flysoloing.hyperpump.util.HPNodeUtils;
import com.google.common.base.Joiner;

/**
 * @author laitao
 * @since 2016-05-13 01:01:30
 */
public class TaskNode {

//    /TASK_taskName
//        |---taskClass：任务类
//        |---taskType：任务类型（通过taskClass继承的父类来判断）
//        |---cron：cron表达式
//        |---description：描述
//        |---batchNo：任务批次号
//        |---status：状态，有效/无效
//        |---
//        |---

    private String rootNodeName;

    private String taskClassNodeName = "taskClass";

    private String taskTypeNodeName = "taskType";

    private String cronNodeName = "cron";

    private String descriptionNodeName = "description";

    private String batchNoNodeName = "batchNo";

    private String statusNodeName = "status";

    public TaskNode(TaskConf taskConf) {
        this.rootNodeName = Joiner.on(Constants.SEPARATOR_UNDERLINE).join(Constants.NODE_PREFIX_TASK, taskConf.getTaskName());
    }

    public String getRootNodePath() {
        return HPNodeUtils.getPath(rootNodeName);
    }

    public String getTaskClassNodePath() {
        return HPNodeUtils.getPath(rootNodeName, taskClassNodeName);
    }

    public String getTaskTypeNodePath() {
        return HPNodeUtils.getPath(rootNodeName, taskTypeNodeName);
    }

    public String getCronNodePath() {
        return HPNodeUtils.getPath(rootNodeName, cronNodeName);
    }

    public String getDescriptionNodePath() {
        return HPNodeUtils.getPath(rootNodeName, descriptionNodeName);
    }

    public String getBatchNoNodePath() {
        return HPNodeUtils.getPath(rootNodeName, batchNoNodeName);
    }

    public String getStatusNodePath() {
        return HPNodeUtils.getPath(rootNodeName, statusNodeName);
    }
}
