package com.flysoloing.hyperpump.task;

import org.quartz.listeners.TriggerListenerSupport;

/**
 * @author laitao
 * @date 2016-05-19 00:42:45
 */
public class TaskTriggerListener extends TriggerListenerSupport {

    public String getName() {
        return "TaskTriggerListener";
    }
}
