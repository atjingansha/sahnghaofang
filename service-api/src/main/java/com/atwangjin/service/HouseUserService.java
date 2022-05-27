package com.atwangjin.service;

import com.atwangjin.base.BaseService;
import com.atwangjin.entity.HouseUser;

import java.util.List;

/**
 * @author WangJin
 * @create 2022-05-20 16:56
 */
public interface HouseUserService extends BaseService<HouseUser> {


    List<HouseUser> findList(Long id);
}
