package com.atwangjin.service;

import com.atwangjin.base.BaseService;
import com.atwangjin.entity.Admin;

import java.util.List;

/**
 * @author WangJin
 * @create 2022-05-16 14:46
 */
public interface AdminService extends BaseService<Admin> {
    List<Admin> findAll();

    Admin getByUserName(String username);
}
