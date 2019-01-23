package com.flysoloing.hyperpump.console.service;

import com.flysoloing.hyperpump.console.domain.ZK;

import java.util.List;

/**
 * @author laitao
 * @date 2016-07-25 15:51:20
 */
public interface ZKService {

    int deleteByPrimaryKey(String id);

    int insert(ZK zk);

    ZK selectByPrimaryKey(String id);

    int updateByPrimaryKey(ZK zk);

    List<ZK> selectAll();
}
