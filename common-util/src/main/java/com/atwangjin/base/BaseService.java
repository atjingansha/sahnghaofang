package com.atwangjin.base;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

import java.io.Serializable;
import java.util.Map;

/**
 * @author WangJin
 * @create 2022-05-16 10:28
 */
public interface BaseService<T> {

    Integer save(T t);

    T getById(Serializable id);

    Integer update(T t);

    Integer delete(Serializable id);


    PageInfo<T> findPage(Map<String, Object> filters);
}
