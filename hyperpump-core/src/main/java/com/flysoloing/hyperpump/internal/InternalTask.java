package com.flysoloing.hyperpump.internal;

import com.flysoloing.hyperpump.registry.RegistryCenter;
import com.flysoloing.hyperpump.task.TaskConf;
import com.flysoloing.hyperpump.task.TaskNode;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author laitao
 * @since 2016-05-20 18:00:03
 */
public class InternalTask implements Job {

    private RegistryCenter registryCenter;

    private TaskConf taskConf;

    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println(Thread.currentThread().getId());
        TaskNode taskNode = new TaskNode(taskConf);
        System.out.println(taskNode.getRootNodePath());
        System.out.println(registryCenter.get(taskNode.getTaskClassNodePath()));
        //TODO 将可运行的TaskNode节点的status修改为运行中，同时将batchNo做加1操作，注意事务性
        //1、试图获取锁，如果能获取锁，则往下进行，否则退出并释放锁
        //2、获取TaskNode节点的status状态，若节点是运行状态，则退出并释放锁；否则往下进行
        //3、开启事务，将TaskNode节点的status状态修改为运行中，同时将batchNo加1后更新，然后提交。完成后释放锁
        String batchNo = registryCenter.get(taskNode.getBatchNoNodePath());
        System.out.println(batchNo);
        Long longBatchNo = Long.valueOf(batchNo);
        longBatchNo += 1l;
        registryCenter.update(taskNode.getBatchNoNodePath(), String.valueOf(longBatchNo));
        System.out.println(registryCenter.get(taskNode.getBatchNoNodePath()));

    }

    public RegistryCenter getRegistryCenter() {
        return registryCenter;
    }

    public void setRegistryCenter(RegistryCenter registryCenter) {
        this.registryCenter = registryCenter;
    }

    public TaskConf getTaskConf() {
        return taskConf;
    }

    public void setTaskConf(TaskConf taskConf) {
        this.taskConf = taskConf;
    }
}
