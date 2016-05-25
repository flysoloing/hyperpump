package com.flysoloing.hyperpump.scheduler;

import com.flysoloing.hyperpump.base.NodeService;
import com.flysoloing.hyperpump.registry.RegistryCenter;

/**
 * @author laitao
 * @since 2016-05-19 01:10:00
 */
public class SchedulerNodeService implements NodeService {

    private RegistryCenter registryCenter;

    private SchedulerConf schedulerConf;

    private SchedulerNode schedulerNode;

    public SchedulerNodeService(RegistryCenter registryCenter, SchedulerConf schedulerConf) {
        this.registryCenter = registryCenter;
        this.schedulerConf = schedulerConf;
        this.schedulerNode = new SchedulerNode(schedulerConf);
    }

    public void registerNodeInfo() {
        if (!registryCenter.isExisted(schedulerNode.getRootNodePath())) {
            //TODO
        }
    }
}