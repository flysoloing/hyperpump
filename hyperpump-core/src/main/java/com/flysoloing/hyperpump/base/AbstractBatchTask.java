package com.flysoloing.hyperpump.base;

import com.flysoloing.hyperpump.exception.HPExceptionHandler;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 批量任务
 *
 * @author laitao
 * @date 2016-06-18 16:10:48
 */
public abstract class AbstractBatchTask<T> implements HyperPumpTask {

    private ExecutorService executorService;

    protected AbstractBatchTask() {
        this.executorService = getExecutorService();
    }

    public void execute() {
        List<T> taskList = fetch();
        final CountDownLatch countDownLatch = new CountDownLatch(taskList.size());

        for (final T t : taskList) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        process(t);
                    } finally {
                        countDownLatch.countDown();
                    }
                }
            });
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            HPExceptionHandler.handleException(e);
        }
    }

    public abstract List<T> fetch();

    public abstract void process(T t);

    public ExecutorService getExecutorService() {
        return Executors.newCachedThreadPool();
    }

    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }
}
