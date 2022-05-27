package com.atwangjin.dao;

import com.atwangjin.base.BaseDao;
import com.atwangjin.entity.HouseUser;

import java.util.List;

/**
 * @author WangJin
 * @create 2022-05-20 16:51
 */
public interface HouseUserDao extends BaseDao<HouseUser> {

    List<HouseUser> findList(Long id);
}
