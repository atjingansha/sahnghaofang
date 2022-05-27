package com.atwangjin.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atwangjin.base.BaseController;
import com.atwangjin.entity.Admin;
import com.atwangjin.entity.HouseBroker;
import com.atwangjin.service.AdminService;
import com.atwangjin.service.HouseBrokerService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author WangJin
 * @create 2022-05-20 19:10
 */
@Controller
@RequestMapping("/houseBroker")
public class HouseBrokerController extends BaseController {




    private final static String LIST_ACTION = "redirect:/house/";
    private final static String PAGE_CREATE = "houseBroker/create";
    private final static String PAGE_EDIT = "houseBroker/edit";



    @Reference
    HouseBrokerService houseBrokerService;

    @Reference
    AdminService adminService;

    @RequestMapping("/delete/{houseId}/{id}")
    public String delete(@PathVariable("houseId")Long houseId ,@PathVariable("id")Long id){

        houseBrokerService.delete(id);

        return LIST_ACTION+houseId;

    }



    @RequestMapping("/create")
    public String create(Map map,HouseBroker houseBroker){

     List<Admin> adminList=  adminService.findAll();

        map.put("adminList",adminList);
        map.put("houseBroker",houseBroker);

     return PAGE_CREATE;
    }


    @RequestMapping("/save")
    public String save(HouseBroker houseBroker, HttpServletRequest request){


        Admin admin=adminService.getById(houseBroker.getBrokerId());


        houseBroker.setBrokerName(admin.getName());
        houseBroker.setBrokerHeadUrl(admin.getHeadUrl());


        houseBrokerService.save(houseBroker);

        return successPage(MESSAGE_SUCCESS,request);
    }


    @RequestMapping("/edit/{id}")
    public String edit(HouseBroker houseBroker,Map map){

        List<Admin> adminList = adminService.findAll();

        HouseBroker houseBrokerDb = houseBrokerService.getById(houseBroker.getId());

        map.put("adminList",adminList);
        map.put("houseBroker",houseBrokerDb);

        return PAGE_EDIT;
    }


    @RequestMapping("/update/{id}")
    public String update(@PathVariable("id")Long id,HouseBroker houseBroker,HttpServletRequest request){


        HouseBroker brokerDb = houseBrokerService.getById(id);

        //brokerDb.setBrokerId(houseBroker.getBrokerId());

        BeanUtils.copyProperties(houseBroker,brokerDb);



        Admin admin = adminService.getById(houseBroker.getBrokerId());

        brokerDb.setBrokerName(admin.getName());
        brokerDb.setBrokerHeadUrl(admin.getHeadUrl());

        houseBrokerService.update(brokerDb);

        return successPage(MESSAGE_SUCCESS,request);

    }
}
