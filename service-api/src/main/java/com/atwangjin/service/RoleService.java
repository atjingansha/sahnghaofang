package com.atwangjin.service;

import com.atwangjin.base.BaseService;
import com.atwangjin.entity.Role;

import java.util.List;
import java.util.Map;


/**
 * @author WangJin
 * @create 2022-05-13 16:35
 */
public interface RoleService extends BaseService<Role> {
    List<Role> findAll();


    Map<String, Object> findRoleByAdminId(Long adminId);

    void saveUserRoleRealtionShip(Long adminId, Long[] roleIds);
}
