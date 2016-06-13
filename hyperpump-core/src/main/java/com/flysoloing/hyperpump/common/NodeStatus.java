package com.flysoloing.hyperpump.common;

/**
 * @author laitao
 * @since 2016-05-24 21:04:17
 */
public enum NodeStatus {

    /**
     * 正常
     */
    NORMAL("normal"),
    /**
     * 不可用
     */
    DISABLED("disabled");

    private String status;

    NodeStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
