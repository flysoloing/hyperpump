package com.flysoloing.hyperpump.common;

/**
 * @author laitao
 * @date 2016-06-18 15:49:06
 */
public enum TaskCategory {

    /**
     * 一次性的
     */
    ONEOFF("oneOff"),
    /**
     * 重复性的
     */
    REPETITIVE("repetitive");

    private String category;

    TaskCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
