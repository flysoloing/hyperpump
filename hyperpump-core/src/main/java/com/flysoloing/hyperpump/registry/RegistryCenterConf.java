package com.flysoloing.hyperpump.registry;

/**
 * 注册中心配置，基于zookeeper实现
 *
 * @author laitao
 * @since 2016-05-13 01:03:29
 */
public class RegistryCenterConf {

    /**
     * 注册中心集群连接字符串，如：host1:port,host2:port
     */
    private String connectString;

    /**
     * 命名空间
     */
    private String namespace;

    /**
     * 初始重试间隔时间
     */
    private int baseSleepTimeMs;

    /**
     * 最大重试次数
     */
    private int maxRetries;

    /**
     * 最大重试间隔时间
     */
    private int maxSleepMs;

    /**
     * 构造器
     *
     * @param connectString 注册中心集群连接字符串
     * @param namespace 命名空间
     * @param baseSleepTimeMs 初始重试间隔时间
     * @param maxRetries 最大重试次数
     * @param maxSleepMs 最大重试间隔时间
     */
    public RegistryCenterConf(String connectString, String namespace, int baseSleepTimeMs, int maxRetries, int maxSleepMs) {
        this.connectString = connectString;
        this.namespace = namespace;
        this.baseSleepTimeMs = baseSleepTimeMs;
        this.maxRetries = maxRetries;
        this.maxSleepMs = maxSleepMs;
    }

    public String getConnectString() {
        return connectString;
    }

    public void setConnectString(String connectString) {
        this.connectString = connectString;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public int getBaseSleepTimeMs() {
        return baseSleepTimeMs;
    }

    public void setBaseSleepTimeMs(int baseSleepTimeMs) {
        this.baseSleepTimeMs = baseSleepTimeMs;
    }

    public int getMaxRetries() {
        return maxRetries;
    }

    public void setMaxRetries(int maxRetries) {
        this.maxRetries = maxRetries;
    }

    public int getMaxSleepMs() {
        return maxSleepMs;
    }

    public void setMaxSleepMs(int maxSleepMs) {
        this.maxSleepMs = maxSleepMs;
    }
}
