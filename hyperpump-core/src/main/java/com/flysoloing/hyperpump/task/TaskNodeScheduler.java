package com.flysoloing.hyperpump.task;

import com.flysoloing.hyperpump.exception.HPExceptionHandler;
import com.flysoloing.hyperpump.internal.InternalScheduleTask;
import com.flysoloing.hyperpump.registry.RegistryCenter;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 任务节点调度器
 *
 * @author laitao
 * @since 2016-05-19 00:19:44
 */
public class TaskNodeScheduler {

    private static final Logger logger = LoggerFactory.getLogger(TaskNodeScheduler.class);

    private RegistryCenter registryCenter;

    private TaskNodeConf taskNodeConf;

    private TaskNode taskNode;

    private JobDetail jobDetail;

    private Scheduler scheduler;

    /**
     * 构造器
     *
     * @param registryCenter 注册中心
     * @param taskNodeConf 任务节点配置
     */
    public TaskNodeScheduler(RegistryCenter registryCenter, TaskNodeConf taskNodeConf) {
        this.registryCenter = registryCenter;
        this.taskNodeConf = taskNodeConf;
        this.taskNode = new TaskNode(taskNodeConf);
        //TODO 把TaskClass换成内置的Task，其作用是修改TaskNode节点的status状态并为batchNo加一操作
        //this.jobDetail = JobBuilder.newJob(taskNodeConf.getTaskClass()).withIdentity(taskNodeConf.getTaskName()).build();
        this.jobDetail = JobBuilder.newJob(InternalScheduleTask.class).withIdentity(taskNodeConf.getTaskName()).build();
    }

    /**
     * 任务节点调度器初始化
     */
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
