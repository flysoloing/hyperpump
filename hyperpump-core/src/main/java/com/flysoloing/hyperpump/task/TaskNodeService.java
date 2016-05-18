package com.flysoloing.hyperpump.task;

import com.flysoloing.hyperpump.registry.RegistryCenter;
import com.flysoloing.hyperpump.util.HPNodeUtils;

/**
 * @author laitao
 * @since 2016-05-19 01:09:37
 */
public class TaskNodeService {

    private RegistryCenter registryCenter;

    private TaskConf taskConf;

    public TaskNodeService(RegistryCenter registryCenter, TaskConf taskConf) {
        this.registryCenter = registryCenter;
        this.taskConf = taskConf;
    }

    public void registerNodeInfo() {
        registryCenter.persist(HPNodeUtils.getFullPath(taskConf.getTaskName(), "taskClass"), taskConf.getTaskClass().getCanonicalName());
        registryCenter.persist(HPNodeUtils.getFullPath(taskConf.getTaskName(), "cron"), taskConf.getCron());
        registryCenter.persist(HPNodeUtils.getFullPath(taskConf.getTaskName(), "description"), taskConf.getDescription());
    }
}
