package com.flysoloing.hyperpump.scheduler;

import com.flysoloing.hyperpump.util.LocalhostUtils;

/**
 * @author laitao
 * @since 2016-05-19 01:10:43
 */
public class SchedulerNodeConf {

    private String ip;

    private String pid;

    private String objName;

    private String description;

    public SchedulerNodeConf(String objName) {
//        this.objName = this.getClass().getSimpleName();
        this.objName = objName;
    }

    public SchedulerNodeConf(String objName, String description) {
        this(objName);
        this.description = description;
    }

    public String getIp() {
        return LocalhostUtils.getIp();
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPid() {
        return LocalhostUtils.getPid();
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
