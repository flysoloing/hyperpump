package com.flysoloing.hyperpump.console.service;

import com.flysoloing.hyperpump.console.dao.ZKMapper;
import com.flysoloing.hyperpump.console.domain.ZK;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author laitao
 * @date 2016-07-25 15:51:35
 */
@Service("zkService")
public class ZKServiceImpl implements ZKService {

    @Autowired
    private ZKMapper zkMapper;

    public int deleteByPrimaryKey(String id) {
        return zkMapper.deleteByPrimaryKey(id);
    }

    public int insert(ZK zk) {
        return zkMapper.insert(zk);
    }

    public ZK selectByPrimaryKey(String id) {
        return zkMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKey(ZK zk) {
        return zkMapper.updateByPrimaryKey(zk);
    }

    public List<ZK> selectAll() {
        return zkMapper.selectAll();
    }
}
