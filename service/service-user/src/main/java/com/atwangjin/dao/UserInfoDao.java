package com.atwangjin.dao;

import com.atwangjin.base.BaseDao;
import com.atwangjin.entity.UserInfo;

/**
 * @author WangJin
 * @create 2022-05-23 12:31
 */
public interface UserInfoDao extends BaseDao<UserInfo> {
    UserInfo getByPhone(String phone);
}
