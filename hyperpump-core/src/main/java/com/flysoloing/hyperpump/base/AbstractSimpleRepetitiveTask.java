package com.flysoloing.hyperpump.base;

/**
 * @author laitao
 * @since 2016-06-18 16:16:13
 */
public abstract class AbstractSimpleRepetitiveTask extends AbstractRepetitiveTask {

    protected void executeTask() {
        process();
    }

    public abstract void process();
}
