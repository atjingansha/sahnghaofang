package com.atwangjin.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atwangjin.base.BaseDao;
import com.atwangjin.base.BaseServiceImpl;
import com.atwangjin.dao.PermissionDao;
import com.atwangjin.dao.RolePermissionDao;
import com.atwangjin.entity.Permission;
import com.atwangjin.entity.RolePermission;
import com.atwangjin.helper.PermissionHelper;
import com.atwangjin.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author WangJin
 * @create 2022-05-24 23:39
 */
@Service(interfaceClass = PermissionService.class)
public class PermissionServiceImpl extends BaseServiceImpl<Permission> implements PermissionService {


    @Autowired
    PermissionDao permissionDao;

    @Autowired
    RolePermissionDao rolePermissionDao;

    @Override
    protected BaseDao<Permission> getEntityDao() {
        return permissionDao;
    }



    // var zNodes =[
    //     { id:1, pId:0, name:"随意勾选 1", open:true},
    //     { id:2, pId:0, name:"随意勾选 2", checked:true, open:true},
    // ];
    @Override
    public List<Map<String, Object>> findPermissionByRoleId(Long roleId) {

        List<Permission> permissionList = permissionDao.findAll();

        List<Long> permissionIdList = rolePermissionDao.findPermissionIdListByRoleId(roleId);

        List<Map<String, Object>> result=new ArrayList<>();
        for (Permission permission : permissionList) {
            Map<String, Object> map=new HashMap<>();
            map.put("id",permission.getId());
            map.put("pId",permission.getParentId());
            map.put("name",permission.getName());

            if (permissionIdList.contains(permission.getId())){
                map.put("checked",true);
            }
            result.add(map);
        }
        return result;
    }

    @Override
    public void saveRolePermissionRealtionShip(Long roleId, Long[] permissionIds) {
        rolePermissionDao.deleteByRoleId(roleId);

        List<RolePermission> rolePermissionList=new ArrayList<>();
        if (permissionIds!=null){
            for (Long permissionId : permissionIds) {
                RolePermission rolePermission=new RolePermission();
                rolePermission.setRoleId(roleId);
                rolePermission.setPermissionId(permissionId);

                rolePermissionList.add(rolePermission);
            }
        }
        rolePermissionDao.insertBatch(rolePermissionList);
    }

    @Override
    public List<Permission> findMenuPermissionByAdminId(Long adminId) {

        //如果是超级管理员显示所有菜单,否则按照分配的权限加载

        List<Permission> permissionList=null;

        if (adminId.intValue()==1){
            permissionList = permissionDao.findAll();
        }else {
            permissionList= permissionDao.findListByAdminId(adminId);
        }

        List<Permission> result = PermissionHelper.bulid(permissionList);
        return result;
    }

    @Override
    public List<Permission> findAllMenu() {
        //全部权限列表
        List<Permission> permissionList = permissionDao.findAll();
        if(CollectionUtils.isEmpty(permissionList)) return null;

        //构建树形数据,总共三级
        //把权限数据构建成树形结构数据
        List<Permission> result = PermissionHelper.bulid(permissionList);
        return result;
    }

    @Override
    public List<String> findCdoeListByAdminId(Long id) {
        if (id.intValue()==1){
            return permissionDao.findAllCode();
        }else {
            return permissionDao.findCodeByAdminId(id);
        }
    }
}
