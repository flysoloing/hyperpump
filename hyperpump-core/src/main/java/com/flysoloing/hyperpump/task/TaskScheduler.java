package com.flysoloing.hyperpump.task;

import com.flysoloing.hyperpump.exception.HPExceptionHandler;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @author laitao
 * @since 2016-05-19 00:19:44
 */
public class TaskScheduler {

    private TaskConf taskConf;

    private JobDetail jobDetail;

    private Scheduler scheduler;

    public TaskScheduler(TaskConf taskConf) {
        this.taskConf = taskConf;
        //TODO 把TaskClass换成内置的Task，其作用是修改TaskNode节点的status状态并为batchNo加一操作
        this.jobDetail = JobBuilder.newJob(taskConf.getTaskClass()).withIdentity(taskConf.getTaskName()).build();
    }

    public void init() {
        try {
            scheduler = initializeScheduler(taskConf);
            scheduleJob(buildCronTrigger(taskConf));
        } catch (SchedulerException e) {
            HPExceptionHandler.handleException(e);
        }
    }

    private Scheduler initializeScheduler(TaskConf taskConf) throws SchedulerException {
        StdSchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        scheduler.getListenerManager().addTriggerListener(new TaskTriggerListener());
        return scheduler;
    }

    private CronTrigger buildCronTrigger(TaskConf taskConf) {
        CronScheduleBuilder cronSchedule = CronScheduleBuilder.cronSchedule(taskConf.getCron());
        return TriggerBuilder
                .newTrigger()
                .withIdentity(taskConf.getTaskName())
                .withSchedule(cronSchedule)
                .build();
    }

    private void scheduleJob(CronTrigger cronTrigger) throws SchedulerException {
        if (!scheduler.checkExists(jobDetail.getKey())) {
            scheduler.scheduleJob(jobDetail, cronTrigger);
        }
        scheduler.start();
    }
}
