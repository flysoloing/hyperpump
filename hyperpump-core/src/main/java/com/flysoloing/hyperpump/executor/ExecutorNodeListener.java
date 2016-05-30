package com.flysoloing.hyperpump.executor;

import com.flysoloing.hyperpump.listener.AbstractNodeListener;
import com.flysoloing.hyperpump.registry.RegistryCenter;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;

/**
 * @author laitao
 * @since 2016-05-26 01:41:36
 */
public class ExecutorNodeListener extends AbstractNodeListener {

    public ExecutorNodeListener(RegistryCenter registryCenter) {
        super(registryCenter);
    }

    @Override
    protected void dataChanged(RegistryCenter registryCenter, TreeCacheEvent event, String path) {
        //TODO
        ChildData data = event.getData();
        if (data == null) {
            System.out.println("Executor Node Listener No data in event[" + event + "]");
        } else {
            System.out.println("Executor Node Listener Receive event: "
                    + "type=" + event.getType()
                    + ", path=" + data.getPath()
                    + ", data=" + new String(data.getData())
                    + ", stat=" + data.getStat());
        }
    }
}
