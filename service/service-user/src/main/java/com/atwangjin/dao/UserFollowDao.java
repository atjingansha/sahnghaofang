package com.atwangjin.dao;

import com.atwangjin.base.BaseDao;
import com.atwangjin.entity.UserFollow;
import com.atwangjin.vo.UserFollowVo;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

/**
 * @author WangJin
 * @create 2022-05-23 22:28
 */
public interface UserFollowDao extends BaseDao<UserFollow> {
    Integer countByUserIdAndHouserId(@Param("userId") Long userId,@Param("houseId") Long houseId);

    Page<UserFollowVo> findListPage(Long userInfoId);


    void cancelFollow(Integer id);
}
