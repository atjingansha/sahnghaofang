package com.atwangjin.dao;

import com.atwangjin.base.BaseDao;
import com.atwangjin.entity.AdminRole;

import java.util.List;

/**
 * @author WangJin
 * @create 2022-05-24 21:49
 */
public interface AdminRoleDao extends BaseDao<AdminRole> {

    void deleteByAdminId(Long adminId);

    List<Long> findRoleIdByAdminId(Long adminId);
}
