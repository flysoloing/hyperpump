package com.flysoloing.hyperpump.common;

/**
 * 节点状态枚举
 *
 * @author laitao
 * @since 2016-05-24 21:04:17
 */
public enum NodeStatus {

    /**
     * 可用
     */
    ENABLED("enabled"),
    /**
     * 阻塞
     */
    BLOCKED("blocked"),
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
