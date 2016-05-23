package com.flysoloing.hyperpump.registry;

import com.flysoloing.hyperpump.exception.HPExceptionHandler;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.apache.zookeeper.CreateMode;

import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;

/**
 * 注册中心，基于zookeeper实现
 *
 * @author laitao
 * @since 2016-05-13 01:05:25
 */
public class RegistryCenter {

    public static final String CHARSET_NAME_UTF8 = "UTF-8";

    private RegistryCenterConf registryCenterConf;

    private CuratorFramework curatorFramework;

    /**
     * 构造器
     *
     * @param registryCenterConf 注册中心配置对象
     */
    public RegistryCenter(RegistryCenterConf registryCenterConf) {
        this.registryCenterConf = registryCenterConf;
    }

    /**
     * 初始化注册中心
     */
    public void init() {
        CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder()
                .connectString(registryCenterConf.getConnectString())
                .connectionTimeoutMs(registryCenterConf.getConnectionTimeoutMs())
                .sessionTimeoutMs(registryCenterConf.getSessionTimeoutMs())
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
        try {
            Thread.sleep(500L);
        } catch (InterruptedException e) {
            HPExceptionHandler.handleException(e);
        }
        CloseableUtils.closeQuietly(curatorFramework);
    }

    /**
     * 获取节点内容
     *
     * @param nodePath 节点路径
     * @return 节点内容，异常时返回null
     */
    public String get(String nodePath) {
        try {
            return new String(curatorFramework.getData().forPath(nodePath), Charset.forName(CHARSET_NAME_UTF8));
        } catch (Exception e) {
            HPExceptionHandler.handleException(e);
            return null;
        }
    }

    /**
     * 判断节点是否存在
     *
     * @param nodePath 节点路径
     * @return 存在，返回true；不存在或异常，返回false
     */
    public boolean isExisted(String nodePath) {
        try {
            return null != curatorFramework.checkExists().forPath(nodePath);
        } catch (Exception e) {
            HPExceptionHandler.handleException(e);
            return false;
        }
    }

    /**
     * 更新节点内容
     *
     * @param nodePath 节点路径
     * @param value 节点内容
     */
    public void update(String nodePath, String value) {
        try {
            curatorFramework.inTransaction().check().forPath(nodePath).and().setData().forPath(nodePath, value.getBytes(Charset.forName(CHARSET_NAME_UTF8))).and().commit();
        } catch (Exception e) {
            HPExceptionHandler.handleException(e);
        }
    }

    /**
     * 删除节点
     *
     * @param nodePath 节点路径
     */
    public void remove(String nodePath) {
        try {
            curatorFramework.delete().deletingChildrenIfNeeded().forPath(nodePath);
        } catch (Exception e) {
            HPExceptionHandler.handleException(e);
        }
    }

    /**
     * 持久化节点，节点不存在，则以持久化模式创建；节点存在，则进行更新
     *
     * @param nodePath 节点路径
     * @param value 节点内容
     */
    public void persist(String nodePath, String value) {
        if (!isExisted(nodePath)) {
            try {
                curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(nodePath, value.getBytes(Charset.forName(CHARSET_NAME_UTF8)));
            } catch (Exception e) {
                HPExceptionHandler.handleException(e);
            }
        } else {
            update(nodePath, value);
        }
    }

    public List<String> getChildrenPaths(String nodePath) {
        try {
            return curatorFramework.getChildren().forPath(nodePath);
        } catch (Exception e) {
            HPExceptionHandler.handleException(e);
            return Collections.emptyList();
        }
    }

    public RegistryCenterConf getRegistryCenterConf() {
        return registryCenterConf;
    }

    public void setRegistryCenterConf(RegistryCenterConf registryCenterConf) {
        this.registryCenterConf = registryCenterConf;
    }

    public CuratorFramework getCuratorFramework() {
        return curatorFramework;
    }

    public void setCuratorFramework(CuratorFramework curatorFramework) {
        this.curatorFramework = curatorFramework;
    }
}
