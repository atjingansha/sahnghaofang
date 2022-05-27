package com.atwangjin.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atwangjin.base.BaseDao;
import com.atwangjin.base.BaseServiceImpl;
import com.atwangjin.dao.HouseUserDao;
import com.atwangjin.entity.HouseUser;
import com.atwangjin.service.HouseUserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author WangJin
 * @create 2022-05-20 16:56
 */
@Service(interfaceClass = HouseUserService.class)
public class HouseUserServiceImpl extends BaseServiceImpl<HouseUser> implements HouseUserService {

    @Autowired
    HouseUserDao houseUserDao;


    @Override
    protected BaseDao<HouseUser> getEntityDao() {
        return houseUserDao;
    }


    @Override
    public List<HouseUser> findList(Long id) {
        return houseUserDao.findList(id);
    }
}
