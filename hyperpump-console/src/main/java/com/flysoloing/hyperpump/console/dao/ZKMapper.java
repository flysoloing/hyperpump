package com.flysoloing.hyperpump.console.dao;

import com.flysoloing.hyperpump.console.domain.ZK;

import java.util.List;

/**
 * @author laitao
 * @date 2016-07-22 12:06:17
 */
public interface ZKMapper {

    int deleteByPrimaryKey(String id);

    int insert(ZK zk);

    ZK selectByPrimaryKey(String id);

    int updateByPrimaryKey(ZK zk);

    List<ZK> selectAll();

}
