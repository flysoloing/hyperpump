package com.flysoloing.hyperpump.scheduler;

/**
 * @author laitao
 * @since 2016-05-19 01:10:43
 */
public class SchedulerConf {

    private String ip;

    private int port;

    private String objName;

    private String description;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
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
