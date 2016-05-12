package com.flysoloing.hyperpump.registry;

import com.flysoloing.hyperpump.exception.HPExceptionHandler;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * 注册中心，基于zookeeper实现
 *
 * @author laitao
 * @since 2016-05-13 01:05:25
 */
public class RegistryCenter {

    private RegistryCenterConf registryCenterConf;

    private CuratorFramework curatorFramework;

    public RegistryCenter(RegistryCenterConf registryCenterConf) {
        this.registryCenterConf = registryCenterConf;
    }

    /**
     * 初始化注册中心
     */
    public void init() {
        CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder()
                .connectString(registryCenterConf.getConnectString())
                .retryPolicy(new ExponentialBackoffRetry(registryCenterConf.getBaseSleepTimeMs(), registryCenterConf.getMaxRetries(), registryCenterConf.getMaxSleepMs()))
                .namespace(registryCenterConf.getNamespace());
        curatorFramework = builder.build();

        curatorFramework.start();
        try {
            curatorFramework.blockUntilConnected();
        } catch (InterruptedException e) {
            HPExceptionHandler.handleException(e);
        }
    }

    /**
     * 关闭注册中心
     */
    public void close() {

    }
}
