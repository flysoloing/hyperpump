package com.flysoloing.hyperpump.common;

/**
 * @author laitao
 * @since 2016-05-23 22:32:38
 */
public enum Status {

    READY("ready"), RUNNING("running");

    private String status;

    Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
