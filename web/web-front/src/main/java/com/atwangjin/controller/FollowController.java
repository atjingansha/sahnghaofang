package com.atwangjin.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atwangjin.entity.UserInfo;
import com.atwangjin.result.Result;
import com.atwangjin.result.ResultCodeEnum;
import com.atwangjin.service.UserFollowService;
import com.atwangjin.service.UserInfoService;
import com.atwangjin.vo.UserFollowVo;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author WangJin
 * @create 2022-05-24 9:50
 */
@RestController
@RequestMapping("/userFollow")
public class FollowController {

@Reference
UserFollowService userFollowService;

    @RequestMapping("/auth/follow/{hosueId}")
    public Result follow(@PathVariable("hosueId")Long houseId, HttpServletRequest request){

        UserInfo userInfo = (UserInfo)request.getSession().getAttribute("USER");


            Long userInfoId = userInfo.getId();

            boolean follow=userFollowService.follow(userInfoId,houseId);

            return Result.ok(follow);

    }




    @RequestMapping("/auth/list/{pageNum}/{pageSize}")
    public Result findPage(@PathVariable("pageNum")Integer pageNum,
                           @PathVariable("pageSize")Integer pageSize,
                           HttpServletRequest request){

        UserInfo userInfo = (UserInfo)request.getSession().getAttribute("USER");

       PageInfo<UserFollowVo>  paegs=userFollowService.findListPage(pageNum,pageSize,userInfo.getId());
        return Result.ok(paegs);

    }


    @RequestMapping("/auth/cancelFollow/{id}")
    public Result cancelFollow(@PathVariable("id") Integer id){

        userFollowService.cancelFollow(id);
        return Result.ok();

    }
}
