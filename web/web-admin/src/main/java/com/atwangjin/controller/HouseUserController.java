package com.atwangjin.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atwangjin.base.BaseController;
import com.atwangjin.entity.HouseUser;
import com.atwangjin.service.HouseUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author WangJin
 * @create 2022-05-20 21:39
 */
@Controller
@RequestMapping("/houseUser")
public class HouseUserController extends BaseController {





    private final static String LIST_ACTION = "redirect:/house/";
    private final static String PAGE_CREATE = "houseUser/create";
    private final static String PAGE_EDIT = "houseUser/edit";



    @Reference
    HouseUserService houseUserService;



    @RequestMapping("/delete/{houseId}/{id}")
    public String delete(@PathVariable("houseId")Long houseId ,@PathVariable("id")Long id){

        houseUserService.delete(id);

        return LIST_ACTION+houseId;

    }



    @RequestMapping("/create")
    public String create(Map map, HouseUser houseUser){

        map.put("houseUser",houseUser);

        return PAGE_CREATE;
    }

    @RequestMapping("/save")
    public String save(HouseUser houseUser, HttpServletRequest request){

        houseUserService.save(houseUser);


        return successPage(MESSAGE_SUCCESS,request);
    }

    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable("id")Long id,Map map){


        HouseUser houseUser = houseUserService.getById(id);


        map.put("houseUser",houseUser);

        return PAGE_EDIT;
    }

    @RequestMapping("/update/{id}")
    public String update(@PathVariable("id")Long id,HouseUser houseUser,HttpServletRequest request){


        houseUserService.update(houseUser);

        return successPage(MESSAGE_SUCCESS,request);

    }

}
