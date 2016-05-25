package com.flysoloing.hyperpump.executor;

import com.flysoloing.hyperpump.listener.AbstractNodeListener;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;

/**
 * @author laitao
 * @since 2016-05-26 01:41:36
 */
public class ExecutorNodeListener extends AbstractNodeListener {

    @Override
    protected void dataChanged(CuratorFramework client, TreeCacheEvent event, String path) {
        //TODO
    }
}
