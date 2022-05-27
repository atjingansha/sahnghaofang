package com.atwangjin.service;

import com.atwangjin.base.BaseDao;
import com.atwangjin.base.BaseService;
import com.atwangjin.entity.HouseBroker;

import java.util.List;

/**
 * @author WangJin
 * @create 2022-05-20 16:38
 */
public interface HouseBrokerService extends BaseService<HouseBroker> {


    List<HouseBroker> findList(Long id);
}
