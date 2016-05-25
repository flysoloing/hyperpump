package com.flysoloing.hyperpump.task;

import com.flysoloing.hyperpump.base.NodeService;
import com.flysoloing.hyperpump.common.TaskStatus;
import com.flysoloing.hyperpump.registry.RegistryCenter;
import org.apache.curator.framework.recipes.cache.TreeCache;

/**
 * @author laitao
 * @since 2016-05-19 01:09:37
 */
public class TaskNodeService implements NodeService {

    private RegistryCenter registryCenter;

    private TaskNodeConf taskNodeConf;

    private TaskNode taskNode;

    public TaskNodeService(RegistryCenter registryCenter, TaskNodeConf taskNodeConf) {
        this.registryCenter = registryCenter;
        this.taskNodeConf = taskNodeConf;
        this.taskNode = new TaskNode(taskNodeConf);
    }

    public void init() {
        registryCenter.addTreeCache(taskNode.getRootNodePath());
        registerNodeInfo();
        registerNodeListener();
    }

    public void registerNodeInfo() {
        if (!registryCenter.isExisted(taskNode.getRootNodePath())) {
            registryCenter.persist(taskNode.getTaskClassNodePath(), taskNodeConf.getTaskClass().getCanonicalName());
            registryCenter.persist(taskNode.getCronNodePath(), taskNodeConf.getCron());
            registryCenter.persist(taskNode.getDescriptionNodePath(), taskNodeConf.getDescription());
            registryCenter.persist(taskNode.getTaskTypeNodePath(), "");
            registryCenter.persist(taskNode.getBatchNoNodePath(), "0000000001");  //格式化，如：0000000000000000001
            registryCenter.persist(taskNode.getStatusNodePath(), TaskStatus.READY.getStatus());
        }
    }

    public void registerNodeListener() {
        //从RegistryCenter中获取TreeCache，根据taskNode的rootNodePath
        //如果不为空，则为该treeCache添加监听器
        TreeCache treeCache = registryCenter.getTreeCache(taskNode.getRootNodePath());
        if (treeCache != null)
            treeCache.getListenable().addListener(new TaskNodeListener());
    }
}
