package com.atwangjin.dao;

import com.atwangjin.base.BaseDao;
import com.atwangjin.entity.Permission;
import com.atwangjin.entity.RolePermission;

import java.util.List;

/**
 * @author WangJin
 * @create 2022-05-24 23:49
 */
public interface RolePermissionDao extends BaseDao<RolePermission>  {


    void deleteByRoleId(Long roleId);

    List<Long> findPermissionIdListByRoleId(Long roleId);
}
