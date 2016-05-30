package com.flysoloing.hyperpump.task;

import com.flysoloing.hyperpump.listener.AbstractNodeListener;
import com.flysoloing.hyperpump.registry.RegistryCenter;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;

/**
 * @author laitao
 * @since 2016-05-26 01:07:27
 */
public class TaskNodeListener extends AbstractNodeListener {

    public TaskNodeListener(RegistryCenter registryCenter) {
        super(registryCenter);
    }

    @Override
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

    }
}
