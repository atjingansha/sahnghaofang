package com.atwangjin.base;

import com.atwangjin.util.CastUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Map;

/**
 * @author WangJin
 * @create 2022-05-16 10:31
 */
@Transactional
public abstract class BaseServiceImpl<T> implements BaseService<T> {

    protected abstract BaseDao<T> getEntityDao();


    @Override
    public Integer save(T t) {
        return getEntityDao().save(t);
    }

    @Override
    public T getById(Serializable id) {
        return  getEntityDao().getById(id);
    }

    @Override
    public Integer update(T t) {
        return  getEntityDao().update(t);
    }

    @Override
    public Integer delete(Serializable id) {
        return  getEntityDao().delete(id);
    }

    @Override
    public PageInfo<T> findPage(Map<String, Object> filters) {

        Object pageNumObj = filters.get("pageNum");
        Object pageSizeObj = filters.get("pageSize");

        int pageNum = CastUtil.castInt(pageNumObj, 1);
        int pageSize = CastUtil.castInt(pageSizeObj, 10);


        //开启mybatis分页功能
        PageHelper.startPage(pageNum,pageSize);

        Page<T> page=  getEntityDao().findPage(filters);

        return new PageInfo<T>(page,5);


    }
}
