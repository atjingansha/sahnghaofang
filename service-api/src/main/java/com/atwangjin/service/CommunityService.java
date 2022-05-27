package com.atwangjin.service;

import com.atwangjin.base.BaseService;
import com.atwangjin.entity.Community;

import java.util.List;

/**
 * @author WangJin
 * @create 2022-05-18 20:49
 */
public interface CommunityService extends BaseService<Community> {
    List<Community> findAll();
}
