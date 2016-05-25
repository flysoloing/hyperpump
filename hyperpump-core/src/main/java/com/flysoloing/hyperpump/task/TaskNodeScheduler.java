package com.flysoloing.hyperpump.task;

import com.flysoloing.hyperpump.exception.HPExceptionHandler;
import com.flysoloing.hyperpump.internal.InternalTask;
import com.flysoloing.hyperpump.registry.RegistryCenter;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @author laitao
 * @since 2016-05-19 00:19:44
 */
public class TaskNodeScheduler {

    private RegistryCenter registryCenter;

    private TaskNodeConf taskNodeConf;

    private TaskNode taskNode;

    private JobDetail jobDetail;

    private Scheduler scheduler;

    public TaskNodeScheduler(RegistryCenter registryCenter, TaskNodeConf taskNodeConf) {
        this.registryCenter = registryCenter;
        this.taskNodeConf = taskNodeConf;
        this.taskNode = new TaskNode(taskNodeConf);
        //TODO 把TaskClass换成内置的Task，其作用是修改TaskNode节点的status状态并为batchNo加一操作
        //this.jobDetail = JobBuilder.newJob(taskNodeConf.getTaskClass()).withIdentity(taskNodeConf.getTaskName()).build();
        this.jobDetail = JobBuilder.newJob(InternalTask.class).withIdentity(taskNodeConf.getTaskName()).build();
    }

    public void init() {
        jobDetail.getJobDataMap().put("registryCenter", registryCenter);
        jobDetail.getJobDataMap().put("taskNode", taskNode);
        try {
            scheduler = initializeScheduler(taskNodeConf);
            scheduleJob(buildCronTrigger(taskNodeConf));
        } catch (SchedulerException e) {
            HPExceptionHandler.handleException(e);
        }
    }

    private Scheduler initializeScheduler(TaskNodeConf taskNodeConf) throws SchedulerException {
        StdSchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        scheduler.getListenerManager().addTriggerListener(new TaskTriggerListener());
        return scheduler;
    }

    private CronTrigger buildCronTrigger(TaskNodeConf taskNodeConf) {
        CronScheduleBuilder cronSchedule = CronScheduleBuilder.cronSchedule(taskNodeConf.getCron());
        return TriggerBuilder
                .newTrigger()
                .withIdentity(taskNodeConf.getTaskName())
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
