package com.flysoloing.hyperpump.listener;

import com.flysoloing.hyperpump.registry.RegistryCenter;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;

/**
 * 节点监听器抽象类
 *
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

    /**
     * 数据变化后处理方法
     *
     * @param registryCenter 注册中心
     * @param event 事件
     * @param path 路径
     */
    protected abstract void dataChanged(RegistryCenter registryCenter, TreeCacheEvent event, String path);
}
