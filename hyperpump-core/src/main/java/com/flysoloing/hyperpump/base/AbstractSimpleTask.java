package com.flysoloing.hyperpump.base;

/**
 * 简单任务
 *
 * @author laitao
 * @date 2016-06-18 16:10:15
 */
public abstract class AbstractSimpleTask implements HyperPumpTask {

    public void execute() {
        process();
    }

    public abstract void process();
}
