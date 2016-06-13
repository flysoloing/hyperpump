package com.flysoloing.hyperpump.scheduler;

import com.flysoloing.hyperpump.base.Node;
import com.flysoloing.hyperpump.common.Constants;
import com.flysoloing.hyperpump.listener.AbstractNodeListener;
import com.flysoloing.hyperpump.registry.RegistryCenter;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;

/**
 * 调度器节点监听器
 *
 * @author laitao
 * @since 2016-05-26 01:40:59
 */
public class SchedulerNodeListener extends AbstractNodeListener<SchedulerNode> {

    private static final Logger logger = LoggerFactory.getLogger(SchedulerNodeListener.class);

    public SchedulerNodeListener(RegistryCenter registryCenter, SchedulerNode schedulerNode) {
        super(registryCenter, schedulerNode);
    }

    protected void dataChanged(RegistryCenter registryCenter, SchedulerNode schedulerNode, TreeCacheEvent event, String path) {
        //TODO
        ChildData data = event.getData();
        if (data == null) {
            logger.info("Scheduler Node Listener No data in event[" + event + "]");
        } else {
            logger.info("Scheduler Node Listener Receive event: "
                    + "type=" + event.getType()
                    + ", path=" + data.getPath()
                    + ", data=" + new String(data.getData(), Charset.forName(Constants.CHARSET_NAME_UTF8))
                    + ", stat=" + data.getStat());
        }
    }
}
