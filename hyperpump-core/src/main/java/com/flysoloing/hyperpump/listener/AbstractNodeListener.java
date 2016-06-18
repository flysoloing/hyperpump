package com.flysoloing.hyperpump.listener;

import com.flysoloing.hyperpump.node.Node;
import com.flysoloing.hyperpump.registry.RegistryCenter;
import org.apache.commons.lang3.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 节点监听器抽象类
 *
 * @author laitao
 * @since 2016-05-23 00:56:07
 */
public abstract class AbstractNodeListener<T extends Node> implements TreeCacheListener {

    private static final Logger logger = LoggerFactory.getLogger(AbstractNodeListener.class);

    private RegistryCenter registryCenter;

    private T node;

    public AbstractNodeListener(RegistryCenter registryCenter, T node) {
        this.registryCenter = registryCenter;
        this.node = node;
    }

    public void childEvent(CuratorFramework client, TreeCacheEvent event) throws Exception {
        String path = null == event.getData() ? "" : event.getData().getPath();
        if (StringUtils.isBlank(path)) {
            logger.info("the event path is blank, do nothing return.");
            return;
        }
        dataChanged(registryCenter, node, event, path);
    }

    /**
     * 数据变化后处理方法
     *
     * @param registryCenter 注册中心
     * @param event 事件
     * @param path 路径
     */
    protected abstract void dataChanged(RegistryCenter registryCenter, T node, TreeCacheEvent event, String path);
}
