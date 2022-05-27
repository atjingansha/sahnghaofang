package com.atwangjin.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atwangjin.base.BaseDao;
import com.atwangjin.base.BaseServiceImpl;
import com.atwangjin.dao.HouseBrokerDao;
import com.atwangjin.entity.HouseBroker;
import com.atwangjin.service.HouseBrokerService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author WangJin
 * @create 2022-05-20 16:46
 */
@Service(interfaceClass = HouseBrokerService.class)
public class HouseBrokerServiceImpl extends BaseServiceImpl<HouseBroker> implements HouseBrokerService {

    @Autowired
    HouseBrokerDao houseBrokerDao;


    @Override
    protected BaseDao<HouseBroker> getEntityDao() {
        return houseBrokerDao;
    }


    @Override
    public List<HouseBroker> findList(Long id) {
        return houseBrokerDao.findList(id);
    }
}
