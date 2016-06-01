package com.flysoloing.hyperpump.internal;

import com.flysoloing.hyperpump.common.TaskStatus;
import com.flysoloing.hyperpump.exception.HPExceptionHandler;
import com.flysoloing.hyperpump.registry.RegistryCenter;
import com.flysoloing.hyperpump.task.TaskNode;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author laitao
 * @since 2016-05-20 18:00:03
 */
public class InternalTask implements Job {

    private static final Logger logger = LoggerFactory.getLogger(InternalTask.class);

    private RegistryCenter registryCenter;

    private TaskNode taskNode;

    public void execute(JobExecutionContext context) throws JobExecutionException {
        //1、试图获取锁，如果能获取锁，则往下进行，否则退出并释放锁
        //2、获取TaskNode节点的status状态，若节点是运行状态，则退出并释放锁；否则往下进行
        //3、开启事务，将TaskNode节点的status状态修改为运行中，同时将batchNo加1后更新，然后提交。完成后释放锁
        InterProcessMutex lock = new InterProcessMutex(registryCenter.getCuratorFramework(), taskNode.getStatusNodePath());
        try {
            if (lock.acquire(2000, TimeUnit.MILLISECONDS)) {
                try {
                    logger.info("已获取锁");
                    String status = registryCenter.get(taskNode.getStatusNodePath());
                    logger.info("当前TaskNode节点状态：{}", status);
                    if (status != null && status.equals(TaskStatus.READY.getStatus())) {
                        String batchNo = registryCenter.get(taskNode.getBatchNoNodePath());
                        logger.info("当前TaskNode节点batchNo：{}", batchNo);
                        Long longBatchNo = Long.valueOf(batchNo);
                        longBatchNo += 1l;
                        logger.info("当前TaskNode节点batchNo修改后：{}", longBatchNo);
                        //TODO 需要开通事务同步更新
                        registryCenter.update(taskNode.getBatchNoNodePath(), String.valueOf(longBatchNo));
                        registryCenter.update(taskNode.getStatusNodePath(), TaskStatus.RUNNING.getStatus());
                    }
                } finally {
                    lock.release();
                    logger.info("已释放锁");
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

    public TaskNode getTaskNode() {
        return taskNode;
    }

    public void setTaskNode(TaskNode taskNode) {
        this.taskNode = taskNode;
    }
}
