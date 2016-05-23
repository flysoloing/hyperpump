package com.flysoloing.hyperpump.internal;

import com.flysoloing.hyperpump.common.Status;
import com.flysoloing.hyperpump.exception.HPExceptionHandler;
import com.flysoloing.hyperpump.registry.RegistryCenter;
import com.flysoloing.hyperpump.task.TaskConf;
import com.flysoloing.hyperpump.task.TaskNode;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.concurrent.TimeUnit;

/**
 * @author laitao
 * @since 2016-05-20 18:00:03
 */
public class InternalTask implements Job {

    private RegistryCenter registryCenter;

    private TaskConf taskConf;

    public void execute(JobExecutionContext context) throws JobExecutionException {
        //1、试图获取锁，如果能获取锁，则往下进行，否则退出并释放锁
        //2、获取TaskNode节点的status状态，若节点是运行状态，则退出并释放锁；否则往下进行
        //3、开启事务，将TaskNode节点的status状态修改为运行中，同时将batchNo加1后更新，然后提交。完成后释放锁
        TaskNode taskNode = new TaskNode(taskConf);
        InterProcessMutex lock = new InterProcessMutex(registryCenter.getCuratorFramework(), taskNode.getStatusNodePath());
        try {
            if (lock.acquire(2000, TimeUnit.MILLISECONDS)) {
                try {
                    System.out.println("已获取锁");
                    String status = registryCenter.get(taskNode.getStatusNodePath());
                    System.out.println("当前TaskNode节点状态：" + status);
                    if (status != null && status.equals(Status.READY.getStatus())) {
                        String batchNo = registryCenter.get(taskNode.getBatchNoNodePath());
                        System.out.println("当前TaskNode节点batchNo：" + batchNo);
                        Long longBatchNo = Long.valueOf(batchNo);
                        longBatchNo += 1l;
                        System.out.println("当前TaskNode节点batchNo修改后：" + longBatchNo);
                        //TODO 需要开通事务同步更新
                        registryCenter.update(taskNode.getBatchNoNodePath(), String.valueOf(longBatchNo));
                        registryCenter.update(taskNode.getStatusNodePath(), Status.RUNNING.getStatus());
                    }
                } finally {
                    lock.release();
                    System.out.println("已释放锁");
                }
            }
        } catch (Exception e) {
            HPExceptionHandler.handleException(e);
        }
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
