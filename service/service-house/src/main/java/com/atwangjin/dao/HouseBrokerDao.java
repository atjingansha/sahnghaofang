package com.atwangjin.dao;

import com.atwangjin.base.BaseDao;
import com.atwangjin.entity.HouseBroker;

import java.util.List;

/**
 * @author WangJin
 * @create 2022-05-20 16:38
 */
public interface HouseBrokerDao extends BaseDao<HouseBroker> {


    List<HouseBroker> findList(Long id);
}
