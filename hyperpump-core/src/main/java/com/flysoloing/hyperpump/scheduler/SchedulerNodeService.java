package com.flysoloing.hyperpump.scheduler;

import com.flysoloing.hyperpump.node.NodeService;
import com.flysoloing.hyperpump.common.NodeStatus;
import com.flysoloing.hyperpump.registry.RegistryCenter;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 调度器节点服务
 *
 * @author laitao
 * @since 2016-05-19 01:10:00
 */
public class SchedulerNodeService implements NodeService {

    private static final Logger logger = LoggerFactory.getLogger(SchedulerNodeService.class);

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

    public void clearDisabledNode() {
        //TODO
    }

    public void registerNodeInfo() {
        if (!registryCenter.isExisted(schedulerNode.getRootNodePath())) {
            registryCenter.persist(schedulerNode.getIpNodePath(), schedulerNodeConf.getIp());
            registryCenter.persist(schedulerNode.getPidNodePath(), schedulerNodeConf.getPid());
            registryCenter.persist(schedulerNode.getObjNameNodePath(), schedulerNodeConf.getObjName());
            registryCenter.persist(schedulerNode.getDescriptionNodePath(), schedulerNodeConf.getDescription());
            registryCenter.persist(schedulerNode.getQueueCapacityNodePath(), String.valueOf(schedulerNodeConf.getQueueCapacity()));
            registryCenter.persist(schedulerNode.getTaskQueueNodePath(), "");
            registryCenter.persist(schedulerNode.getNodeStatusNodePath(), NodeStatus.NORMAL.getStatus());
        }
    }

    public void registerNodeListener() {
        TreeCache treeCache = registryCenter.getTreeCache(schedulerNode.getRootNodePath());
        if (treeCache != null)
            treeCache.getListenable().addListener(new SchedulerNodeListener(registryCenter, schedulerNode));
    }

    public void registerConnListener() {
        //TODO
    }
}
