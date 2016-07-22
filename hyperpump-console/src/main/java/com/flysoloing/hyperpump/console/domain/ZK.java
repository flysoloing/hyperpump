package com.flysoloing.hyperpump.console.domain;

import java.io.Serializable;

/**
 * @author laitao
 * @since 2016-07-22 12:01:32
 */
public class ZK implements Serializable{

    private String id;

    private String des;

    private String connectStr;

    private String sessionTimeout;

    public ZK() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getConnectStr() {
        return connectStr;
    }

    public void setConnectStr(String connectStr) {
        this.connectStr = connectStr;
    }

    public String getSessionTimeout() {
        return sessionTimeout;
    }

    public void setSessionTimeout(String sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
    }
}
