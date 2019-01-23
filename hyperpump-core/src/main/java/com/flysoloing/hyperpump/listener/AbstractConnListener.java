package com.flysoloing.hyperpump.listener;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;

/**
 * 连接状态监听器抽象类
 *
 * @author laitao
 * @date 2016-05-31 18:56:22
 */
public abstract class AbstractConnListener implements ConnectionStateListener {

    public void stateChanged(CuratorFramework client, ConnectionState newState) {
        //TODO
        connStateChanged(client, newState);
    }

    /**
     * 连接状态改变后处理方法
     *
     * @param client 客户端
     * @param newState 新连接状态
     */
    protected abstract void connStateChanged(CuratorFramework client, ConnectionState newState);
}
