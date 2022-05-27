package com.atwangjin.service;

import com.atwangjin.base.BaseService;
import com.atwangjin.entity.UserFollow;
import com.atwangjin.entity.UserInfo;
import com.atwangjin.vo.UserFollowVo;
import com.github.pagehelper.PageInfo;

/**
 * @author WangJin
 * @create 2022-05-23 22:29
 */
public interface UserFollowService extends BaseService<UserFollow> {



    boolean isFollow(Long userInfoId, Long houseId);

    boolean follow(Long userInfoId, Long houseId);

    PageInfo<UserFollowVo> findListPage(Integer pageNum, Integer pageSize, Long userInfoId);

    void cancelFollow(Integer id);
}
