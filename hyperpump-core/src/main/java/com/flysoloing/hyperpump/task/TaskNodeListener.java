package com.flysoloing.hyperpump.task;

import com.flysoloing.hyperpump.listener.AbstractNodeListener;
import com.flysoloing.hyperpump.registry.RegistryCenter;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author laitao
 * @since 2016-05-26 01:07:27
 */
public class TaskNodeListener extends AbstractNodeListener {

    private static final Logger logger = LoggerFactory.getLogger(TaskNodeListener.class);

    public TaskNodeListener(RegistryCenter registryCenter) {
        super(registryCenter);
    }

    protected void dataChanged(RegistryCenter registryCenter, TreeCacheEvent event, String path) {
        //TODO 监听到TaskNode节点数据变化后需要处理的事儿，如果path是status状态变化，则进行相关处理
        ChildData data = event.getData();
        if (data == null) {
            System.out.println("Task Node Listener No data in event[" + event + "]");
        } else {
            System.out.println("Task Node Listener Receive event: "
                    + "type=" + event.getType()
                    + ", path=" + data.getPath()
                    + ", data=" + new String(data.getData())
                    + ", stat=" + data.getStat());
        }
        for (Map.Entry entry : registryCenter.getTreeCacheMap().entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
    }
}
