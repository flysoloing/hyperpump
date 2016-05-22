package com.flysoloing.hyperpump.executor;

/**
 * @author laitao
 * @since 2016-05-19 01:10:33
 */
public class ExecutorConf {

    private String ip;

    private int port;

    private String objId;

    private String status;

    public ExecutorConf(String ip, int port, String objId) {
        this.ip = ip;
        this.port = port;
        this.objId = objId;
    }

    public ExecutorConf(String ip, int port, String objId, String status) {
        this(ip, port, objId);
        this.status = status;
    }

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

    public String getObjId() {
        return objId;
    }

    public void setObjId(String objId) {
        this.objId = objId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
