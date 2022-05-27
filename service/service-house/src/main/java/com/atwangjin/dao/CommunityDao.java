package com.atwangjin.dao;

import com.atwangjin.base.BaseDao;
import com.atwangjin.entity.Community;

import java.util.List;

/**
 * @author WangJin
 * @create 2022-05-18 20:52
 */
public interface CommunityDao extends BaseDao<Community> {
    List<Community> findAll();
}
