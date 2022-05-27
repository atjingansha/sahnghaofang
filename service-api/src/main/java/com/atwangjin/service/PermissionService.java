package com.atwangjin.service;

import com.atwangjin.base.BaseService;
import com.atwangjin.entity.Permission;

import java.util.List;
import java.util.Map;

/**
 * @author WangJin
 * @create 2022-05-24 23:39
 */
public interface PermissionService extends BaseService<Permission> {
    List<Map<String, Object>> findPermissionByRoleId(Long roleId);

    void saveRolePermissionRealtionShip(Long roleId, Long[] permissionIds);

    List<Permission> findMenuPermissionByAdminId(Long adminId);

    List<Permission> findAllMenu();

    List<String> findCdoeListByAdminId(Long id);
}
