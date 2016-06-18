package com.flysoloing.hyperpump.base;

/**
 * @author laitao
 * @since 2016-06-18 16:12:16
 */
public abstract class AbstractSimpleOneOffTask implements OneOffTask {

    public void execute() {
        process();
    }

    protected abstract void process();
}
