package com.atwangjin.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.atwangjin.base.BaseController;
import com.atwangjin.entity.Permission;
import com.atwangjin.entity.Role;
import com.atwangjin.service.PermissionService;
import com.atwangjin.service.RoleService;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author WangJin
 * @create 2022-05-13 16:40
 */
@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {


    @Reference
    private RoleService roleService;

    @Reference
    private PermissionService permissionService;


    private final static String LIST_ACTION = "redirect:/role";
    private final static String PAGE_INDEX = "role/index";
    private final static String PAGE_CREATE = "role/create";
    private final static String PAGE_EDIT = "role/edit";

    private final static String PAGE_ASSGIN_SHOW = "role/assginShow";

    @RequestMapping
    public String index(Map map,HttpServletRequest request) {


      Map<String,Object> filters=getFilters(request);

        PageInfo<Role> page = roleService.findPage(filters);

        map.put("filters",filters);
        map.put("page",page);

        return PAGE_INDEX;

    }

    @PreAuthorize("hasAuthority('role.create111')")
    @RequestMapping("/create")
    public String create(){
        return PAGE_CREATE;
    }


    @RequestMapping("/save")
    public String save(Role role, HttpServletRequest request){
        roleService.save(role);
        return successPage(MESSAGE_SUCCESS,request);
    }


    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id,Map<String,Object> map){
        Role role = roleService.getById(id);

        map.put("role",role);

        return PAGE_EDIT;
    }


    @RequestMapping("/update")
    public String upadte(Role role,HttpServletRequest request){


        System.out.println("role.getId() = " + role.getId());

        roleService.update(role);

        return successPage(MESSAGE_SUCCESS,request);

    }

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id){

        roleService.delete(id);
        return LIST_ACTION;
    }

    @RequestMapping("/assginShow/{roleId}")
    public String assginShow(@PathVariable("roleId") Long roleId,Map map){



        return PAGE_ASSGIN_SHOW;
    }


    /**
     * 进入分配权限页面
     * @param roleId
     * @return
     */
    @GetMapping("/assginShow/{roleId}")
    public String assignShow(ModelMap model, @PathVariable Long roleId) {
        List<Map<String,Object>> zNodes = permissionService.findPermissionByRoleId(roleId);
        model.addAttribute("zNodes", JSON.toJSONString(zNodes));
        model.addAttribute("roleId", roleId);
        return PAGE_ASSGIN_SHOW;
    }

    /**
     * 给角色分配权限
     * @param roleId
     * @param permissionIds
     * @return
     */
    @PostMapping("/assignPermission")
    public String assignPermission(Long roleId, Long[] permissionIds, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        permissionService.saveRolePermissionRealtionShip(roleId, permissionIds);
        return this.successPage(MESSAGE_SUCCESS, request);
    }

}
