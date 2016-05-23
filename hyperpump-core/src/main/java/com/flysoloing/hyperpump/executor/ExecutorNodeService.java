package com.flysoloing.hyperpump.executor;

import com.flysoloing.hyperpump.base.NodeService;
import com.flysoloing.hyperpump.registry.RegistryCenter;

/**
 * @author laitao
 * @since 2016-05-19 01:10:11
 */
public class ExecutorNodeService implements NodeService {

    private RegistryCenter registryCenter;

    private ExecutorConf executorConf;

    private ExecutorNode executorNode;

    public ExecutorNodeService(RegistryCenter registryCenter, ExecutorConf executorConf) {
        this.registryCenter = registryCenter;
        this.executorConf = executorConf;
        this.executorNode = new ExecutorNode(executorConf);
    }

    public void registerNodeInfo() {
        if (!registryCenter.isExisted(executorNode.getRootNodePath())) {
            //TODO
        }
    }
}
