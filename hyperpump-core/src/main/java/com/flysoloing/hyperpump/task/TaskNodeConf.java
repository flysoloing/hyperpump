package com.flysoloing.hyperpump.task;

import org.quartz.Job;

/**
 * @author laitao
 * @since 2016-05-15 16:34:34
 */
public class TaskNodeConf {

    private String taskName;

    private Class<? extends Job> taskClass;

    private String cron;

    private String description;

    public TaskNodeConf(String taskName, Class<? extends Job> taskClass, String cron) {
        this.taskName = taskName;
        this.taskClass = taskClass;
        this.cron = cron;
    }

    public TaskNodeConf(String taskName, Class<? extends Job> taskClass, String cron, String description) {
        this(taskName, taskClass, cron);
        this.description = description;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Class<? extends Job> getTaskClass() {
        return taskClass;
    }

    public void setTaskClass(Class<? extends Job> taskClass) {
        this.taskClass = taskClass;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
