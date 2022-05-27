package com.atwangjin.dao;

import com.atwangjin.base.BaseDao;
import com.atwangjin.entity.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author WangJin
 * @create 2022-05-13 16:29
 */
@Mapper
public interface RoleDao extends BaseDao<Role> {
    List<Role> findAll();

}
