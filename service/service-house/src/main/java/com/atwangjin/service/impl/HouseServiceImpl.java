package com.atwangjin.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atwangjin.base.BaseDao;
import com.atwangjin.base.BaseServiceImpl;
import com.atwangjin.dao.DictDao;
import com.atwangjin.dao.HouseDao;
import com.atwangjin.entity.House;
import com.atwangjin.service.HouseService;
import com.atwangjin.vo.HouseQueryVo;
import com.atwangjin.vo.HouseVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

/**
 * @author WangJin
 * @create 2022-05-20 10:10
 */
@Service(interfaceClass = HouseService.class )
public class HouseServiceImpl extends BaseServiceImpl<House> implements HouseService {

    @Autowired
    private HouseDao houseDao;

    @Autowired
    private DictDao dictDao;

    @Override
    protected BaseDao<House> getEntityDao() {
        return houseDao;
    }

    @Override
    public House getById(Serializable id) {

        House house = houseDao.getById(id);
        house.setHouseTypeName( dictDao.getNameById(house.getHouseTypeId()));
        house.setFloorName(dictDao.getNameById(house.getFloorId()));
        house.setBuildStructureName(dictDao.getNameById(house.getBuildStructureId()));
        house.setDecorationName(dictDao.getNameById(house.getDecorationId()));
        house.setDirectionName(dictDao.getNameById(house.getDirectionId()));
        house.setHouseUseName(dictDao.getNameById(house.getHouseUseId()));
        return house;
    }

    @Override
    public void publish(Long id, Integer status) {
        House house=new House();

        house.setId(id);
        house.setStatus(status);

        houseDao.update(house);
    }

    @Override
    public PageInfo<HouseVo> findPage(Integer pageNum, Integer pageSize, HouseQueryVo houseQueryVo) {


        PageHelper.startPage(pageNum,pageSize);

       Page<HouseVo> page= houseDao.findListPage(houseQueryVo);


        for (HouseVo houseVo : page) {
            houseVo.setFloorName(dictDao.getNameById(houseVo.getFloorId()));
            houseVo.setDirectionName(dictDao.getNameById(houseVo.getDirectionId()));
            houseVo.setHouseTypeName(dictDao.getNameById(houseVo.getHouseTypeId()));
        }


        return new PageInfo<>(page,5);
    }
}
