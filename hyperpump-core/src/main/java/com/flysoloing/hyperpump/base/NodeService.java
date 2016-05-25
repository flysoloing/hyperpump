package com.flysoloing.hyperpump.base;

/**
 * @author laitao
 * @since 2016-05-20 14:18:13
 */
public interface NodeService {

    /**
     * 初始化节点服务
     */
    void init();

    /**
     * 在注册中心注册节点信息
     */
    void registerNodeInfo();

    /**
     * 在注册中心注册节点监听器
     */
    void registerNodeListener();

    //TODO 增加节点数据内容的操作，如CRUD等
}
