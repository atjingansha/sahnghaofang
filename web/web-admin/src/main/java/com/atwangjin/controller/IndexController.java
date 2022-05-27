package com.atwangjin.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atwangjin.entity.Admin;
import com.atwangjin.entity.Permission;
import com.atwangjin.service.AdminService;
import com.atwangjin.service.PermissionService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * @author WangJin
 * @create 2022-05-26 10:24
 */
@Controller
@RequestMapping
public class IndexController {

    private final static String PAGE_AUTH = "common/auth";
    @Reference
    AdminService adminService;

    @Reference
    PermissionService permissionService;


    @RequestMapping("/")
    public String index(Map map){

      // Long adminId=1L;


       // Admin admin = adminService.getById(adminId);


        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Admin admin = adminService.getByUserName(user.getUsername());

        //根据登录用户查询动态菜单
     List<Permission> permissionList=  permissionService.findMenuPermissionByAdminId(admin.getId());

        map.put("admin",admin);
        map.put("permissionList",permissionList);

        return "frame/index";
    }



    @RequestMapping("/login")
    public String toLogin(){
        return "frame/login";
    }



    @RequestMapping("/auth")
    public String auth() {
        return PAGE_AUTH;
    }
}
