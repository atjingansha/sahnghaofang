package com.atwangjin.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atwangjin.base.BaseController;
import com.atwangjin.entity.*;
import com.atwangjin.service.*;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author WangJin
 * @create 2022-05-20 10:03
 */
@Controller
@RequestMapping("/house")
public class HouseController extends BaseController {




    private final static String LIST_ACTION = "redirect:/house";
    private final static String PAGE_INDEX = "house/index";
    private final static String PAGE_CREATE = "house/create";
    private final static String PAGE_EDIT = "house/edit";



    @Reference
    HouseService houseService;

    @Reference
    DictService dictService;

    @Reference
    CommunityService communityService;


    @Reference
    HouseImageService houseImageService;

    @Reference
    HouseBrokerService houseBrokerService;

    @Reference
    HouseUserService houseUserService;

    @RequestMapping
    public  String index(Map map, HttpServletRequest request){

        Map<String,Object> filters=getFilters(request);

        PageInfo<House> page = houseService.findPage(filters);

        map.put("filters",filters);
        map.put("page",page);

      List<Community> communityList= communityService.findAll();
        map.put("communityList",communityList);
        map.put("houseTypeList",dictService.findListByDictCode("houseType"));
        map.put("floorList",dictService.findListByDictCode("floor"));
        map.put("buildStructureList",dictService.findListByDictCode("buildStructure"));
        map.put("directionList",dictService.findListByDictCode("decoration"));
        map.put("decorationList",dictService.findListByDictCode("direction"));
        map.put("houseUseList",dictService.findListByDictCode("houseUse"));

        return PAGE_INDEX;
    }


    @RequestMapping("/create")
    public String create(Map map){


        List<Community> communityList= communityService.findAll();
        map.put("communityList",communityList);
        map.put("houseTypeList",dictService.findListByDictCode("houseType"));
        map.put("floorList",dictService.findListByDictCode("floor"));
        map.put("buildStructureList",dictService.findListByDictCode("buildStructure"));
        map.put("directionList",dictService.findListByDictCode("decoration"));
        map.put("decorationList",dictService.findListByDictCode("direction"));
        map.put("houseUseList",dictService.findListByDictCode("houseUse"));

        return PAGE_CREATE;
    }


    @RequestMapping("/save")
    public String save(House house,HttpServletRequest request){

          houseService.save(house);

        return successPage(MESSAGE_SUCCESS,request);
    }

    @RequestMapping("/edit/{id}")
    public String edit(Map map, @PathVariable("id")Long id){

        House house = houseService.getById(id);

        List<Community> communityList= communityService.findAll();
        map.put("communityList",communityList);
        map.put("houseTypeList",dictService.findListByDictCode("houseType"));
        map.put("floorList",dictService.findListByDictCode("floor"));
        map.put("buildStructureList",dictService.findListByDictCode("buildStructure"));
        map.put("directionList",dictService.findListByDictCode("decoration"));
        map.put("decorationList",dictService.findListByDictCode("direction"));
        map.put("houseUseList",dictService.findListByDictCode("houseUse"));
        map.put("house",house);
        return PAGE_EDIT;
    }


    @RequestMapping("/update")
    public String update(HttpServletRequest request,House house){


        houseService.update(house);

        return successPage(MESSAGE_SUCCESS,request);

    }

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable("id")Long id){


        houseService.delete(id);

        return LIST_ACTION;

    }

    @RequestMapping("/publish/{id}/{status}")
    public String publish(@PathVariable("id")Long id,@PathVariable("status")Integer status){


        houseService.publish(id,status);

        return LIST_ACTION;

    }



    @RequestMapping("/{id}")
    public String show(@PathVariable("id")Long id,Map map){

        House house = houseService.getById(id);

        Community community = communityService.getById(house.getCommunityId());

         List<HouseImage>  houseImage1List = houseImageService.findList(id,1);
         List<HouseImage>  houseImage2List = houseImageService.findList(id,2);

        List<HouseBroker> houseBrokerList = houseBrokerService.findList(id);

        List<HouseUser> houseUserList = houseUserService.findList(id);


        map.put("community",community);
        map.put("house",house);
        map.put("houseImage1List",houseImage1List);
        map.put("houseImage2List",houseImage2List);
        map.put("houseBrokerList",houseBrokerList);
        map.put("houseUserList",houseUserList);
        return "house/show";
    }

}
