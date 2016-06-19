package com.flysoloing.hyperpump.base;

/**
 * @author laitao
 * @since 2016-06-18 16:15:50
 */
public abstract class AbstractSequenceBatchRepetitiveTask extends AbstractRepetitiveTask {

    public void executeTask() {
        fetchData();
        process();
    }

    public abstract void fetchData();

    public abstract void process();
}
