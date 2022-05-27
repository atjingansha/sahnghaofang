package com.atwangjin.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atwangjin.base.BaseController;
import com.atwangjin.entity.UserInfo;
import com.atwangjin.result.Result;
import com.atwangjin.result.ResultCodeEnum;
import com.atwangjin.service.UserInfoService;
import com.atwangjin.util.MD5;
import com.atwangjin.vo.LoginVo;
import com.atwangjin.vo.RegisterVo;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author WangJin
 * @create 2022-05-23 19:05
 */
@RestController
@RequestMapping("/userInfo")
public class UserInfoController extends BaseController {


    @Reference
    UserInfoService userInfoService;

    @RequestMapping("/regist")
    public Result regist(@RequestBody RegisterVo registerVo,HttpServletRequest request){

        UserInfo userInfo=new UserInfo();
        String code = registerVo.getCode();
        String nickName = registerVo.getNickName();
        String password = registerVo.getPassword();
        String phone = registerVo.getPhone();

        //校验参数
        if(StringUtils.isEmpty(nickName) ||
                StringUtils.isEmpty(phone) ||
                StringUtils.isEmpty(password) ||
                StringUtils.isEmpty(code)) {
            return Result.build(null, ResultCodeEnum.PARAM_ERROR);
        }

        HttpSession session = request.getSession();

        Object attribute = session.getAttribute("CODE");
//验证码
        if (attribute==null || !attribute.equals(code)){
            return Result.build(null, ResultCodeEnum.CODE_ERROR);
        }


        UserInfo userPhone = userInfoService.getByPhone(phone);

        if (userPhone!=null){
            return Result.build(null, ResultCodeEnum.PHONE_REGISTER_ERROR);
        }





        userInfo.setNickName(nickName);
        userInfo.setPassword(MD5.encrypt(password));
        userInfo.setPhone(phone);


        userInfoService.save(userInfo);

        return Result.ok();

    }

    @RequestMapping("/sendCode/{phone}")
    public Result sendCode(@PathVariable("phone") String phone, HttpServletRequest request){

        userInfoService.getByPhone(phone);

        HttpSession session = request.getSession();

        String code="1111";

        session.setAttribute("CODE",code);
        return Result.ok(code);
    }



    @RequestMapping("/login")
    public Result login(@RequestBody LoginVo loginVo,HttpServletRequest request){
        String phone = loginVo.getPhone();
        String password = loginVo.getPassword();

        //校验参数
        if(StringUtils.isEmpty(phone) ||
                StringUtils.isEmpty(password)) {
            return Result.build(null, ResultCodeEnum.PARAM_ERROR);
        }

        UserInfo userInfo = userInfoService.getByPhone(phone);


        if(null == userInfo) {
            return Result.build(null, ResultCodeEnum.ACCOUNT_ERROR);
        }

        //校验密码
        if(!MD5.encrypt(password).equals(userInfo.getPassword())) {
            return Result.build(null, ResultCodeEnum.PASSWORD_ERROR);
        }

        //校验是否被禁用
        if(userInfo.getStatus() == 0) {
            return Result.build(null, ResultCodeEnum.ACCOUNT_LOCK_ERROR);
        }


        HttpSession session = request.getSession();
        session.setAttribute("USER",userInfo);

        Map<String, Object> map = new HashMap<>();
        map.put("phone", userInfo.getPhone());
        map.put("nickName", userInfo.getNickName());
        return Result.ok(map);
    }


    @RequestMapping("/logout")
  public Result logout(HttpServletRequest request) {


        HttpSession session = request.getSession();
        if (session!=null){
            session.removeAttribute("USER");

            session.invalidate();
        }


        return Result.ok();
    }
}
