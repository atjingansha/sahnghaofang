package com.atwangjin.base;

import com.github.pagehelper.Page;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author WangJin
 * @create 2022-05-16 10:09
 */
public interface BaseDao<T> {


    Integer save(T t);

    T getById(Serializable id);

    Integer update(T t);

    Integer delete(Serializable id);


    Page<T> findPage(Map<String, Object> filters);

    void insertBatch(List<T> list);
}
