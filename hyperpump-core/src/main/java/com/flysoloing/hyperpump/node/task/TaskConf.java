package com.flysoloing.hyperpump.node.task;

/**
 * @author laitao
 * @since 2016-05-15 16:34:34
 */
public class TaskConf {

    private String taskName;

    private Class taskClass;

    private String cron;

    private String description;

    public TaskConf(String taskName, Class taskClass, String cron) {
        this.taskName = taskName;
        this.taskClass = taskClass;
        this.cron = cron;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Class getTaskClass() {
        return taskClass;
    }

    public void setTaskClass(Class taskClass) {
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
