package com.flysoloing.hyperpump.node;

/**
 * 节点服务接口
 *
 * @author laitao
 * @since 2016-05-20 14:18:13
 */
public interface NodeService {

    /**
     * 初始化节点服务
     */
    void init();

    /**
     * 清除当前IP下所有不可用的节点
     */
    void clearDisabledNode();

    /**
     * 在注册中心注册节点信息
     */
    void registerNodeInfo();

    /**
     * 在注册中心注册节点监听器
     */
    void registerNodeListener();

    /**
     * 在注册中心注册连接状态监听器
     */
    void registerConnListener();
    //TODO 增加节点数据内容的操作，如CRUD等
}
