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

    private String rootNodePath;

    private String taskClassNodePath;

    private String cronNodePath;

    private String descriptionNodePath;

    private String taskTypeNodePath = "taskType";

    private String batchNoNodePath = "batchNo";

    private String statusNodePath = "status";

    public TaskNode(TaskConf taskConf) {
        this.rootNodePath = Joiner.on(Constants.SEPARATOR_UNDERLINE).join(Constants.NODE_PREFIX_TASK, taskConf.getTaskName());
        this.taskClassNodePath = taskConf.getTaskClass().getCanonicalName();
        this.cronNodePath = taskConf.getCron();
        this.descriptionNodePath = taskConf.getDescription();
    }

    public String getRootNodePath() {
        return HPNodeUtils.getFullPath(rootNodePath);
    }

    public String getTaskClassNodePath() {
        return HPNodeUtils.getFullPath(rootNodePath, taskClassNodePath);
    }

    public String getCronNodePath() {
        return HPNodeUtils.getFullPath(rootNodePath, cronNodePath);
    }

    public String getDescriptionNodePath() {
        return HPNodeUtils.getFullPath(rootNodePath, descriptionNodePath);
    }

    public String getTaskTypeNodePath() {
        return HPNodeUtils.getFullPath(rootNodePath, taskTypeNodePath);
    }

    public String getBatchNoNodePath() {
        return HPNodeUtils.getFullPath(rootNodePath, batchNoNodePath);
    }

    public String getStatusNodePath() {
        return HPNodeUtils.getFullPath(rootNodePath, statusNodePath);
    }
}
