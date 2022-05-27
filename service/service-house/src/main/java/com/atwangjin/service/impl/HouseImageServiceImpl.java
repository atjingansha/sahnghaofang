package com.atwangjin.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atwangjin.base.BaseDao;
import com.atwangjin.base.BaseServiceImpl;
import com.atwangjin.dao.HouseImageDao;
import com.atwangjin.entity.HouseImage;
import com.atwangjin.service.HouseImageService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author WangJin
 * @create 2022-05-20 16:20
 */
@Service(interfaceClass = HouseImageService.class)
public class HouseImageServiceImpl extends BaseServiceImpl<HouseImage> implements HouseImageService {

    @Autowired
    HouseImageDao houseImageDao;


    @Override
    protected BaseDao<HouseImage> getEntityDao() {
        return houseImageDao;
    }

    @Override
    public List<HouseImage> findList(Long houseId, Integer type) {
        return houseImageDao.findList(houseId,type);
    }
}
