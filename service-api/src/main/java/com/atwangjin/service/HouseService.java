package com.atwangjin.service;

import com.atwangjin.base.BaseService;
import com.atwangjin.entity.House;
import com.atwangjin.vo.HouseQueryVo;
import com.atwangjin.vo.HouseVo;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author WangJin
 * @create 2022-05-20 10:08
 */
@Mapper
public interface HouseService extends BaseService<House> {
    void publish(Long id, Integer status);

    PageInfo<HouseVo> findPage(Integer pageNum, Integer pageSize, HouseQueryVo houseQueryVo);
}
