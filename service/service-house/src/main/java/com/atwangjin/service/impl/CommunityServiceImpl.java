package com.atwangjin.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atwangjin.base.BaseDao;
import com.atwangjin.base.BaseServiceImpl;
import com.atwangjin.dao.CommunityDao;
import com.atwangjin.dao.DictDao;
import com.atwangjin.entity.Community;
import com.atwangjin.service.CommunityService;
import com.atwangjin.util.CastUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author WangJin
 * @create 2022-05-18 20:50
 */
@Service(interfaceClass = CommunityService.class)
public class CommunityServiceImpl extends BaseServiceImpl<Community> implements CommunityService {

    @Autowired
    private CommunityDao communityDao;

    @Autowired
    private DictDao dictDao;

    @Override
    protected BaseDao<Community> getEntityDao() {
        return communityDao;
    }

    @Override
    public PageInfo<Community> findPage(Map<String, Object> filters) {
        Object pageNumObj = filters.get("pageNum");
        Object pageSizeObj = filters.get("pageSize");

        int pageNum = CastUtil.castInt(pageNumObj, 1);
        int pageSize = CastUtil.castInt(pageSizeObj, 10);


        //开启mybatis分页功能
        PageHelper.startPage(pageNum,pageSize);

        Page<Community> page=  getEntityDao().findPage(filters);

        for (Community community : page) {

            String areaName = dictDao.getNameById(community.getAreaId());
            String plateName = dictDao.getNameById(community.getPlateId());
            community.setAreaName(areaName);
            community.setPlateName(plateName);
        }

        return new PageInfo<Community>(page,5);
    }

    @Override
    public Community getById(Serializable id) {

        Community community = communityDao.getById(id);

        community.setAreaName(dictDao.getNameById(community.getAreaId()));
        community.setPlateName(dictDao.getNameById(community.getPlateId()));
        return community;
    }

    @Override
    public List<Community> findAll() {
        return communityDao.findAll();
    }
}
