package com.flysoloing.hyperpump.listener;

import com.flysoloing.hyperpump.registry.RegistryCenter;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;

/**
 * @author laitao
 * @since 2016-05-23 00:56:07
 */
public abstract class AbstractNodeListener implements TreeCacheListener {

    private RegistryCenter registryCenter;

    public AbstractNodeListener(RegistryCenter registryCenter) {
        this.registryCenter = registryCenter;
    }

    public void childEvent(CuratorFramework client, TreeCacheEvent event) throws Exception {
        String path = null == event.getData() ? "" : event.getData().getPath();
        if (path.isEmpty()) {
            return;
        }
        dataChanged(registryCenter, event, path);
    }

//    protected abstract void dataChanged(CuratorFramework client, TreeCacheEvent event, String path);

    protected abstract void dataChanged(RegistryCenter registryCenter, TreeCacheEvent event, String path);
}
