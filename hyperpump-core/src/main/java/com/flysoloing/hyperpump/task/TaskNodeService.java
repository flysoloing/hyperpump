package com.flysoloing.hyperpump.task;

import com.flysoloing.hyperpump.base.NodeService;
import com.flysoloing.hyperpump.common.TaskStatus;
import com.flysoloing.hyperpump.registry.RegistryCenter;

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
            registryCenter.persist(taskNode.getBatchNoNodePath(), "0000000001");  //格式化，如：0000000000000000001
            registryCenter.persist(taskNode.getStatusNodePath(), TaskStatus.READY.getStatus());
        }
    }
}
