package com.flysoloing.hyperpump.base;

/**
 * @author laitao
 * @since 2016-06-18 16:13:06
 */
public abstract class AbstractBatchOneOffTask implements OneOffTask {

    public void execute() {
        fetchData();
        process();
    }

    protected abstract void fetchData();

    protected abstract void process();
}
