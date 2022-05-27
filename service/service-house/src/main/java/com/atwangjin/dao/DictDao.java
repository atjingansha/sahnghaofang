package com.atwangjin.dao;

import com.atwangjin.base.BaseDao;
import com.atwangjin.entity.Admin;
import com.atwangjin.entity.Dict;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author WangJin
 * @create 2022-05-16 14:44
 */
@Mapper
public interface DictDao{

    List<Dict> getDict(Long id);

    Integer isParentCount(Long id);

    Dict findListByDictCode(String dictCode);

    String getNameById(Long id);
}

