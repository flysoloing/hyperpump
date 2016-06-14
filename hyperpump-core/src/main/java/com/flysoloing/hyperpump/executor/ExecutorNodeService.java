package com.flysoloing.hyperpump.executor;

import com.flysoloing.hyperpump.base.NodeService;
import com.flysoloing.hyperpump.common.NodeStatus;
import com.flysoloing.hyperpump.registry.RegistryCenter;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 执行器节点服务
 *
 * @author laitao
 * @since 2016-05-19 01:10:11
 */
public class ExecutorNodeService implements NodeService {

    private static final Logger logger = LoggerFactory.getLogger(ExecutorNodeService.class);

    private RegistryCenter registryCenter;

    private ExecutorNodeConf executorNodeConf;

    private ExecutorNode executorNode;

    public ExecutorNodeService(RegistryCenter registryCenter, ExecutorNodeConf executorNodeConf) {
        this.registryCenter = registryCenter;
        this.executorNodeConf = executorNodeConf;
        this.executorNode = new ExecutorNode(executorNodeConf);
    }

    public void init() {
        registryCenter.addTreeCache(executorNode.getRootNodePath());
        registerNodeInfo();
        registerNodeListener();
    }

    public void registerNodeInfo() {
        if (!registryCenter.isExisted(executorNode.getRootNodePath())) {
            registryCenter.persist(executorNode.getIpNodePath(), executorNodeConf.getIp());
            registryCenter.persist(executorNode.getPidNodePath(), executorNodeConf.getPid());
            registryCenter.persist(executorNode.getObjNameNodePath(), executorNodeConf.getObjName());
            registryCenter.persist(executorNode.getDescriptionNodePath(), executorNodeConf.getDescription());
            registryCenter.persist(executorNode.getTaskAreaNodePath(), "");
            registryCenter.persist(executorNode.getNodeStatusNodePath(), NodeStatus.NORMAL.getStatus());
        }
    }

    public void registerNodeListener() {
        TreeCache treeCache = registryCenter.getTreeCache(executorNode.getRootNodePath());
        if (treeCache != null)
            treeCache.getListenable().addListener(new ExecutorNodeListener(registryCenter, executorNode));
    }

    public void registerConnListener() {
        //TODO
    }
}
