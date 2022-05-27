package com.atwangjin.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.atwangjin.base.BaseDao;
import com.atwangjin.base.BaseServiceImpl;
import com.atwangjin.dao.UserFollowDao;
import com.atwangjin.entity.Dict;
import com.atwangjin.entity.UserFollow;
import com.atwangjin.entity.UserInfo;
import com.atwangjin.service.DictService;
import com.atwangjin.service.UserFollowService;
import com.atwangjin.vo.UserFollowVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 * @author WangJin
 * @create 2022-05-23 22:29
 */
@Service(interfaceClass = UserFollowService.class)
@CrossOrigin
public class UserFollowServiceImpl extends BaseServiceImpl<UserFollow> implements UserFollowService {


    @Autowired
    private UserFollowDao userFollowDao;

   @Reference(check = false)
    DictService dictService;

    @Override
    protected BaseDao<UserFollow> getEntityDao() {
        return userFollowDao;
    }


    @Override
    public boolean isFollow(Long userInfoId, Long houseId) {

      Integer count=  userFollowDao.countByUserIdAndHouserId(userInfoId,houseId);

      if(count.intValue()==1){
          return true;
      }
        return false;
    }

    @Override
    public boolean follow(Long userInfoId, Long houseId) {

        Integer count=   userFollowDao.countByUserIdAndHouserId(userInfoId,houseId);

        if(count.intValue()==0){
            UserFollow userFollow=new UserFollow();

            userFollow.setUserId(userInfoId);
            userFollow.setHouseId(houseId);

            userFollowDao.save(userFollow);

        }
        return true;
    }

    @Override
    public PageInfo<UserFollowVo> findListPage(Integer pageNum, Integer pageSize, Long userInfoId) {

        PageHelper.startPage(pageNum,pageSize);

       Page<UserFollowVo>  page=userFollowDao.findListPage(userInfoId);

        for (UserFollowVo userFollowVo : page) {
           userFollowVo.setHouseTypeName(dictService.getNameById(userFollowVo.getHouseTypeId()));
           userFollowVo.setFloorName(dictService.getNameById(userFollowVo.getFloorId()));
           userFollowVo.setDirectionName(dictService.getNameById(userFollowVo.getDirectionId()));
        }

        return new PageInfo<>(page,5);
    }

    @Override
    public void cancelFollow(Integer id) {
        userFollowDao.cancelFollow(id);
    }
}
