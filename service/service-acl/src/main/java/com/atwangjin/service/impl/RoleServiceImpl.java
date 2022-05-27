package com.atwangjin.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atwangjin.base.BaseDao;
import com.atwangjin.base.BaseServiceImpl;
import com.atwangjin.dao.AdminDao;
import com.atwangjin.dao.AdminRoleDao;
import com.atwangjin.dao.RoleDao;
import com.atwangjin.entity.AdminRole;
import com.atwangjin.entity.Role;
import com.atwangjin.service.RoleService;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author WangJin
 * @create 2022-05-13 16:35
 */
//@Service("roleService")

@Service(interfaceClass = RoleService.class)
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {

    @Resource
    private RoleDao roleDao;


    @Resource
    AdminRoleDao adminRoleDao;


    @Override
    protected BaseDao<Role> getEntityDao() {
        return roleDao;
    }

    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }

    @Override
    public Map<String, Object> findRoleByAdminId(Long adminId) {

        List<Role> roleList = roleDao.findAll();

        List<Long> roleIdList = adminRoleDao.findRoleIdByAdminId(adminId);

        List<Role> assignRoleList=new ArrayList<>();
        List<Role> noAssignRoleList=new ArrayList<>();
        for (Role role : roleList) {
            if (roleIdList.contains(role.getId())){
                assignRoleList.add(role);
            }else {
                noAssignRoleList.add(role);
            }
        }
        Map<String, Object> dataMap=new HashMap<>();

        dataMap.put("assignRoleList",assignRoleList);
        dataMap.put("noAssignRoleList",noAssignRoleList);

        return dataMap;
    }

    @Override
    public void saveUserRoleRealtionShip(Long adminId, Long[] roleIds) {

        //添加数据之前需要把之前的数据清空再重新加要添加的数据
        adminRoleDao.deleteByAdminId(adminId);

        if (roleIds.length>0 || roleIds!=null){
            List<AdminRole> adminRoleList=new ArrayList<>();
            for (Long roleId : roleIds) {
                if(StringUtils.isEmpty(roleId)) continue;
                AdminRole adminRole=new AdminRole();
                adminRole.setRoleId(roleId);
                adminRole.setAdminId(adminId);

                adminRoleList.add(adminRole);
            }
            adminRoleDao.insertBatch(adminRoleList);
        }

    }

}
