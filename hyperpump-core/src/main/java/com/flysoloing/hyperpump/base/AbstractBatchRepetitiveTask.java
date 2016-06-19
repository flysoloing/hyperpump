package com.flysoloing.hyperpump.base;

/**
 * @author laitao
 * @since 2016-06-18 16:15:17
 */
public abstract class AbstractBatchRepetitiveTask extends AbstractRepetitiveTask {

    public void executeTask() {
        fetchData();
        process();
    }

    public abstract void fetchData();

    public abstract void process();
}
