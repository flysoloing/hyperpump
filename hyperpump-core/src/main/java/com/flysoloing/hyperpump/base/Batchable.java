package com.flysoloing.hyperpump.base;

import java.util.List;

/**
 * @author laitao
 * @since 2016-06-19 19:53:23
 */
public interface Batchable<T> {

    List<T> fetchTaskData();

    void processTask(List<T> list);
}
