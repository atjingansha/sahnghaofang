package com.atwangjin.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atwangjin.entity.*;
import com.atwangjin.result.Result;
import com.atwangjin.service.*;
import com.atwangjin.vo.HouseQueryVo;
import com.atwangjin.vo.HouseVo;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author WangJin
 * @create 2022-05-22 16:47
 */
@RestController
@RequestMapping("/house")
public class HouseController {


    @Reference
    HouseService houseService;

    @Reference
    CommunityService communityService;

    @Reference
    HouseImageService houseImageService;

    @Reference
    HouseBrokerService houseBrokerService;

    @Reference
    UserFollowService userFollowService;


    @RequestMapping("/list/{pageNum}/{pageSize}")
    public Result findListPage(@PathVariable("pageNum") Integer pageNum,
                               @PathVariable("pageSize")Integer pageSize,
                               @RequestBody HouseQueryVo houseQueryVo){



        if (pageNum==null){
            pageNum=1;
        }


        if (pageSize==null){
            pageNum=1;
        }
             PageInfo<HouseVo> pageInfo= houseService.findPage(pageNum,pageSize,houseQueryVo);

             return Result.ok(pageInfo);
    }

    @GetMapping("info/{id}")
    public Result info(@PathVariable Long id, HttpServletRequest request) {
        House house = houseService.getById(id);
        Community community = communityService.getById(house.getCommunityId());
        List<HouseBroker> houseBrokerList = houseBrokerService.findList(id);
        List<HouseImage> houseImage1List = houseImageService.findList(id, 1);

        Map<String, Object> map = new HashMap<>();
        map.put("house",house);
        map.put("community",community);
        map.put("houseBrokerList",houseBrokerList);
        map.put("houseImage1List",houseImage1List);
        //关注业务后续补充，当前默认返回未关注

        boolean follow=false;

        HttpSession session = request.getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute("USER");

        if (userInfo!=null){

            Long userInfoId = userInfo.getId();

            follow=  userFollowService.isFollow(userInfoId,id);
        }

        map.put("isFollow",follow);

        return Result.ok(map);
    }
}
