package com.atwangjin.dao;

import com.atwangjin.base.BaseDao;
import com.atwangjin.entity.Admin;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author WangJin
 * @create 2022-05-16 14:44
 */
@Mapper
public interface AdminDao extends BaseDao<Admin> {
    List<Admin> findAll();

    Admin getByUserName(String username);
}
