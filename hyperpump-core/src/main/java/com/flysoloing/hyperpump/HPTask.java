package com.flysoloing.hyperpump;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author laitao
 * @since 2016-05-19 01:31:26
 */
public class HPTask implements Job {

    public void execute(JobExecutionContext context) throws JobExecutionException {
        //TODO
        System.out.println("HaHaHa...");
    }
}
