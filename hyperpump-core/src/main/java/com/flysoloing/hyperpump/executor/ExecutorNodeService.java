package com.flysoloing.hyperpump.executor;

import com.flysoloing.hyperpump.base.NodeService;
import com.flysoloing.hyperpump.registry.RegistryCenter;
import org.apache.curator.framework.recipes.cache.TreeCache;

/**
 * @author laitao
 * @since 2016-05-19 01:10:11
 */
public class ExecutorNodeService implements NodeService {

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
            //TODO
        }
    }

    public void registerNodeListener() {
        TreeCache treeCache = registryCenter.getTreeCache(executorNode.getRootNodePath());
        if (treeCache != null)
            treeCache.getListenable().addListener(new ExecutorNodeListener());
    }
}
