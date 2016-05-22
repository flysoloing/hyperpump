package com.flysoloing.hyperpump.base;

import com.flysoloing.hyperpump.registry.RegistryCenter;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author laitao
 * @since 2016-05-19 01:31:26
 */
public abstract class AbstractBaseTask implements Job {

    private RegistryCenter registryCenter;

    public void execute(JobExecutionContext context) throws JobExecutionException {
        //TODO
        System.out.println("HaHaHa...");
        //在process执行之前，对注册的节点数据做一些处理
        process(context);
    }

    protected abstract void process(JobExecutionContext context);

    public RegistryCenter getRegistryCenter() {
        return registryCenter;
    }

    public void setRegistryCenter(RegistryCenter registryCenter) {
        this.registryCenter = registryCenter;
    }
}
