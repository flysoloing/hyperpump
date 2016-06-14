package com.flysoloing.hyperpump.common;

/**
 * 任务类型枚举
 *
 * @author laitao
 * @since 2016-06-15 01:35:00
 */
public enum TaskType {

    /**
     * 简单任务类型
     */
    SIMPLE("simple"),
    /**
     * 其他
     */
    OTHER("other");

    private String type;

    TaskType(String type) {
        this.type = type;
    }
}
