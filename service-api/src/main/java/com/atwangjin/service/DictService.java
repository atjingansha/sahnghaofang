package com.atwangjin.service;

import com.atwangjin.entity.Dict;

import java.util.List;
import java.util.Map;

/**
 * @author WangJin
 * @create 2022-05-18 11:47
 */
public interface DictService {
    List<Map<String, Object>> getDict(Long id);

    List<Dict> findListByParentId(Long id);

    List<Dict> findListByDictCode(String dictCode);

    String getNameById(Long id);
}
