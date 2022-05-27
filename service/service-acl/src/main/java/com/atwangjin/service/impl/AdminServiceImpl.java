package com.atwangjin.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atwangjin.base.BaseDao;
import com.atwangjin.base.BaseServiceImpl;
import com.atwangjin.dao.AdminDao;
import com.atwangjin.entity.Admin;
import com.atwangjin.service.AdminService;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author WangJin
 * @create 2022-05-16 14:46
 */
//@Service("adminService")

    @Service(interfaceClass = AdminService.class)
public class AdminServiceImpl extends BaseServiceImpl<Admin> implements AdminService {

    @Resource
    private AdminDao adminDao;


    @Override
    protected BaseDao<Admin> getEntityDao() {
        return adminDao;
    }

    @Override
    public List<Admin> findAll() {
        return adminDao.findAll();
    }

    @Override
    public Admin getByUserName(String username) {
        return adminDao.getByUserName(username);
    }
}
