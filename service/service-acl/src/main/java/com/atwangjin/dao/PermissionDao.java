package com.atwangjin.dao;

import com.atwangjin.base.BaseDao;
import com.atwangjin.entity.Permission;

import java.util.List;

/**
 * @author WangJin
 * @create 2022-05-24 23:41
 */
public interface PermissionDao extends BaseDao<Permission> {

    List<Permission> findAll();

    List<Permission> findListByAdminId(Long adminId);

    List<String> findAllCode();

    List<String> findCodeByAdminId(Long id);
}
