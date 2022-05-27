package com.atwangjin.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atwangjin.base.BaseController;
import com.atwangjin.entity.Admin;
import com.atwangjin.service.AdminService;

import com.atwangjin.service.RoleService;
import com.atwangjin.util.QiniuUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

/**
 * @author WangJin
 * @create 2022-05-16 14:49
 */
@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {

    @Reference
    private AdminService adminService;

    @Reference
    private RoleService roleService;

    @Autowired
    PasswordEncoder passwordEncoder;

    public static final String URL="http://47.93.148.192:8080/group1/M00/03/F0/rBHu8mHqbpSAU0jVAAAgiJmKg0o148.jpg";

    private final static String LIST_ACTION = "redirect:/admin";
    private final static String PAGE_INDEX = "admin/index";
    private final static String PAGE_CREATE = "admin/create";
    private final static String PAGE_EDIT = "admin/edit";
    private final static String PAGE_ASSGIN_SHOW = "admin/assginShow";


    @RequestMapping
    public String index(Map map, HttpServletRequest request) {


        Map<String,Object> filters=getFilters(request);

        PageInfo<Admin> page = adminService.findPage(filters);

        map.put("filters",filters);
        map.put("page",page);

        return PAGE_INDEX;

    }


    @RequestMapping("/create")
    public String create(){
        return PAGE_CREATE;
    }


    @RequestMapping("/save")
    public String save(Admin admin, HttpServletRequest request){
        admin.setHeadUrl(URL);
        //将密码加密后保存到数据库
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        adminService.save(admin);
        return successPage(MESSAGE_SUCCESS,request);
    }


    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id,Map<String,Object> map){
        Admin admin = adminService.getById(id);

        map.put("admin",admin);

        return PAGE_EDIT;
    }


    @RequestMapping("/update")
    public String upadte(Admin admin, HttpServletRequest request){




        adminService.update(admin);

        return successPage(MESSAGE_SUCCESS,request);

    }

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id){

        adminService.delete(id);
        return LIST_ACTION;
    }

    @RequestMapping("/uploadShow/{id}")
    public String uploadShow(@PathVariable("id") Long id,Map map){

        map.put("id",id);
        return "admin/upload";
    }

    @RequestMapping("/upload/{id}")
    public String upload(@PathVariable("id")Long id,
                         @RequestParam("file") MultipartFile file,
                         HttpServletRequest request) throws IOException {

        String fileName= UUID.randomUUID().toString();

        QiniuUtils.upload2Qiniu(file.getBytes(),fileName);

        String url= "http://rc7nzkna5.hn-bkt.clouddn.com/"+ fileName;
        Admin admin=new Admin();

        admin.setHeadUrl(url);
        admin.setId(id);
        adminService.update(admin);

        return successPage(MESSAGE_SUCCESS,request);
    }

    @RequestMapping("/assignShow/{adminId}")
    public String assignShow(ModelMap model, @PathVariable Long adminId) {
        Map<String, Object> roleMap = roleService.findRoleByAdminId(adminId);
        model.addAllAttributes(roleMap);
        model.addAttribute("adminId", adminId);
        return PAGE_ASSGIN_SHOW;
    }

    /**
     * 根据用户分配角色
     * @param adminId
     * @param roleIds
     * @return
     */
    @PostMapping("/assignRole")
    public String assignRole(Long adminId, Long[] roleIds, HttpServletRequest request) {
        roleService.saveUserRoleRealtionShip(adminId,roleIds);
        return successPage(MESSAGE_SUCCESS, request);
    }

}
