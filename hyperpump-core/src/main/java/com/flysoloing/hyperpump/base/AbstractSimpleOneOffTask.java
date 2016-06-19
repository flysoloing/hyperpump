package com.flysoloing.hyperpump.base;

/**
 * @author laitao
 * @since 2016-06-18 16:12:16
 */
public abstract class AbstractSimpleOneOffTask extends AbstractOneOffTask {

    protected void executeTask() {
        process();
    }

    public abstract void process();
}
