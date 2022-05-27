package com.atwangjin.service;

import com.atwangjin.base.BaseService;
import com.atwangjin.entity.HouseImage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author WangJin
 * @create 2022-05-20 16:19
 */
public interface HouseImageService extends BaseService<HouseImage> {

    List<HouseImage> findList(Long houseId, Integer type);
}
