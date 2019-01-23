package com.flysoloing.hyperpump.scheduler;

import com.flysoloing.hyperpump.util.LocalhostUtils;

/**
 * 调度器节点配置
 *
 * @author laitao
 * @date 2016-05-19 01:10:43
 */
public class SchedulerNodeConf {

    /*缺省默认值*/
    private static final int DEFAULT_QUEUE_CAPACITY = 10;

    private String ip;

    private String pid;

    private String objName;

    private int queueCapacity;

    private String description;

    public SchedulerNodeConf(String objName) {
        this(objName,DEFAULT_QUEUE_CAPACITY);
    }

    public SchedulerNodeConf(String objName, int queueCapacity) {
        this(objName, queueCapacity, "");
    }

    public SchedulerNodeConf(String objName, String description) {
        this(objName, DEFAULT_QUEUE_CAPACITY, description);
    }

    public SchedulerNodeConf(String objName, int queueCapacity, String description) {
        this.ip = LocalhostUtils.getIp();
        this.pid = LocalhostUtils.getPid();
        this.objName = objName;
        this.queueCapacity = queueCapacity;
        this.description = description;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getObjName() {
        return objName;
    }

    public void setObjName(String objName) {
        this.objName = objName;
    }

    public int getQueueCapacity() {
        return queueCapacity;
    }

    public void setQueueCapacity(int queueCapacity) {
        this.queueCapacity = queueCapacity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
