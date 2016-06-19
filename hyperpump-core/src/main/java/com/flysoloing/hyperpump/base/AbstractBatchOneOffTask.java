package com.flysoloing.hyperpump.base;

/**
 * @author laitao
 * @since 2016-06-18 16:13:06
 */
public abstract class AbstractBatchOneOffTask extends AbstractOneOffTask {

    public void executeTask() {
        fetchData();
        process();
    }

    public abstract void fetchData();

    public abstract void process();
}
