package com.flysoloing.hyperpump.task;

import com.flysoloing.hyperpump.node.NodeService;
import com.flysoloing.hyperpump.registry.RegistryCenter;
import com.flysoloing.hyperpump.util.HPNodeUtils;

/**
 * @author laitao
 * @since 2016-05-19 01:09:37
 */
public class TaskNodeService implements NodeService {

    private RegistryCenter registryCenter;

    private TaskConf taskConf;

    private TaskNode taskNode;

    public TaskNodeService(RegistryCenter registryCenter, TaskConf taskConf) {
        this.registryCenter = registryCenter;
        this.taskConf = taskConf;
        this.taskNode = new TaskNode(taskConf);
    }

    public void registerNodeInfo() {
        if (!registryCenter.isExisted(taskNode.getRootNodePath())) {
            registryCenter.persist(taskNode.getTaskClassNodePath(), taskConf.getTaskClass().getCanonicalName());
            registryCenter.persist(taskNode.getCronNodePath(), taskConf.getCron());
            registryCenter.persist(taskNode.getDescriptionNodePath(), taskConf.getDescription());
            registryCenter.persist(taskNode.getTaskTypeNodePath(), "");
            registryCenter.persist(taskNode.getBatchNoNodePath(), "");
            registryCenter.persist(taskNode.getStatusNodePath(), "");
        }
    }
}
