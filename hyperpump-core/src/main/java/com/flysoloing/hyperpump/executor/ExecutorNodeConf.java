package com.flysoloing.hyperpump.executor;

/**
 * @author laitao
 * @since 2016-05-19 01:10:33
 */
public class ExecutorNodeConf {

    private String ip;

    private int pid;

    private String objName;

    private String description;

    public ExecutorNodeConf(String objName) {
        this.objName = objName;
    }

    public ExecutorNodeConf(String objName, String description) {
        this(objName);
        this.description = description;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
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
