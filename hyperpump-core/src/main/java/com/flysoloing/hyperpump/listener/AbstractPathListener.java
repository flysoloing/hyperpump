package com.flysoloing.hyperpump.listener;

import com.flysoloing.hyperpump.registry.RegistryCenter;
import org.apache.commons.lang3.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author laitao
 * @date 2016-08-25 12:14:53
 */
public abstract class AbstractPathListener implements TreeCacheListener {

    private static final Logger logger = LoggerFactory.getLogger(AbstractPathListener.class);

    private RegistryCenter registryCenter;
    private String path;

    public AbstractPathListener(RegistryCenter registryCenter, String path) {
        this.registryCenter = registryCenter;
        this.path = path;
    }

    public void childEvent(CuratorFramework client, TreeCacheEvent event) throws Exception {
        String path = null == event.getData() ? "" : event.getData().getPath();
        if (StringUtils.isBlank(path)) {
            logger.info("the event path is blank, do nothing return.");
            return;
        }
        dataChanged(registryCenter, event, path);
    }

    protected abstract void dataChanged(RegistryCenter registryCenter, TreeCacheEvent event, String path);
}
