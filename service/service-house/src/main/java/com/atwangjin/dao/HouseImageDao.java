package com.atwangjin.dao;

import com.atwangjin.base.BaseDao;
import com.atwangjin.entity.HouseImage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author WangJin
 * @create 2022-05-20 16:22
 */
public interface HouseImageDao extends BaseDao<HouseImage> {


    List<HouseImage> findList(@Param("houseId")Long houseId,@Param("type")Integer type);
}
