package com.flysoloing.hyperpump.scheduler;

import com.flysoloing.hyperpump.base.NodeService;
import com.flysoloing.hyperpump.registry.RegistryCenter;

/**
 * @author laitao
 * @since 2016-05-19 01:10:00
 */
public class SchedulerNodeService implements NodeService {

    private RegistryCenter registryCenter;

    private SchedulerNodeConf schedulerNodeConf;

    private SchedulerNode schedulerNode;

    public SchedulerNodeService(RegistryCenter registryCenter, SchedulerNodeConf schedulerNodeConf) {
        this.registryCenter = registryCenter;
        this.schedulerNodeConf = schedulerNodeConf;
        this.schedulerNode = new SchedulerNode(schedulerNodeConf);
    }

    public void init() {
        registryCenter.addTreeCache(schedulerNode.getRootNodePath());
        registerNodeInfo();
        registerNodeListener();
    }

    public void registerNodeInfo() {
        if (!registryCenter.isExisted(schedulerNode.getRootNodePath())) {
            //TODO
        }
    }

    public void registerNodeListener() {

    }
}
