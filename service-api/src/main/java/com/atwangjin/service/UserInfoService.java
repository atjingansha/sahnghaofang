package com.atwangjin.service;

import com.atwangjin.base.BaseService;
import com.atwangjin.entity.UserInfo;

/**
 * @author WangJin
 * @create 2022-05-23 12:32
 */
public interface UserInfoService extends BaseService<UserInfo> {

    UserInfo getByPhone(String phone);
}
