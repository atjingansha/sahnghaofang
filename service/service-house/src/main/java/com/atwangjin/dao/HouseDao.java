package com.atwangjin.dao;

import com.atwangjin.base.BaseDao;
import com.atwangjin.entity.House;
import com.atwangjin.vo.HouseQueryVo;
import com.atwangjin.vo.HouseVo;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

/**
 * @author WangJin
 * @create 2022-05-20 10:09
 */
public interface HouseDao extends BaseDao<House> {


    Page<HouseVo> findListPage(@Param("vo") HouseQueryVo houseQueryVo);
}
