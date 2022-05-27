package com.atwangjin.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atwangjin.base.BaseController;
import com.atwangjin.entity.Community;
import com.atwangjin.entity.Dict;
import com.atwangjin.service.CommunityService;
import com.atwangjin.service.DictService;
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
 * @create 2022-05-18 20:33
 */
@Controller
@RequestMapping("/community")
public class CommunityController extends BaseController {




    private final static String LIST_ACTION = "redirect:/community";
    private final static String PAGE_INDEX = "community/index";
    private final static String PAGE_CREATE = "community/create";
    private final static String PAGE_EDIT = "community/edit";

    @Reference
 private    CommunityService communityService;

    @Reference
    private DictService dictService;

    @RequestMapping
    public String index(Map map, HttpServletRequest request){


        Map<String,Object> filters=getFilters(request);


        if (!filters.containsKey("areaId")){

            filters.put("areaId","");
        }
        if (!filters.containsKey("plateId")){

            filters.put("plateId","");
        }

        List<Dict> areaList = dictService.findListByDictCode("beijing");
        PageInfo<Community> page=communityService.findPage(filters);


        map.put("filters",filters);
        map.put("areaList",areaList);
      map.put("page",page);

      return PAGE_INDEX;
    }


    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id){

        communityService.delete(id);

        return LIST_ACTION;
    }


    @RequestMapping("/update/{id}")
    public String update(@PathVariable("id") Long id,Map map){

        List<Dict> areaList = dictService.findListByDictCode("beijing");
        map.put("areaList",areaList);

        Community community = communityService.getById(id);

        map.put("community",community);


        return PAGE_EDIT;
    }

    @RequestMapping("/edit")
    public String edit(Community community,HttpServletRequest request){

         communityService.update(community);

        return successPage(MESSAGE_SUCCESS,request);
    }



    @RequestMapping("/save")
    public String save(Community community,HttpServletRequest request){



        communityService.save(community);
        return successPage(MESSAGE_SUCCESS,request);
    }


    @RequestMapping("/create")
    public String create(Map map){

        List<Dict> areaList = dictService.findListByDictCode("beijing");
        map.put("areaList",areaList);
        return PAGE_CREATE;
    }
}
