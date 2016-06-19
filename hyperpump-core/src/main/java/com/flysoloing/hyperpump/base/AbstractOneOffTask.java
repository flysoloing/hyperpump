package com.flysoloing.hyperpump.base;

/**
 * 一次性的任务
 *
 * @author laitao
 * @since 2016-06-18 16:10:15
 */
public abstract class AbstractOneOffTask implements HyperPumpTask {

    public void execute() {
        executeTask();
    }

    protected abstract void executeTask();
}
