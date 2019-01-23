package com.flysoloing.hyperpump.executor;

import com.flysoloing.hyperpump.util.LocalhostUtils;

/**
 * 执行器节点配置
 *
 * @author laitao
 * @date 2016-05-19 01:10:33
 */
public class ExecutorNodeConf {

    private String ip;

    private String pid;

    private String objName;

    private String description;

    public ExecutorNodeConf(String objName) {
        this(objName, "");
    }

    public ExecutorNodeConf(String objName, String description) {
        this.ip = LocalhostUtils.getIp();
        this.pid = LocalhostUtils.getPid();
        this.objName = objName;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
