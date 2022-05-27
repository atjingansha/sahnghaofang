package com.atwangjin.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.atwangjin.dao.DictDao;
import com.atwangjin.entity.Dict;
import com.atwangjin.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author WangJin
 * @create 2022-05-18 13:55
 */

@Service(interfaceClass = DictService.class)
public class DictServiceImpl implements DictService {


    @Autowired
   private DictDao dictDao;

    @Autowired
    JedisPool jedisPool;

    @Override
    public List<Map<String, Object>> getDict(Long id) {


        //获取子节点
        // [{ id:'0331',name:'n3.3.n1',isParent:true}]
      List<Dict> childList=  dictDao.getDict(id);

      List<Map<String,Object>> nodes=new ArrayList<>();


        for (Dict dict : childList) {

            Integer parentCount = dictDao.isParentCount(dict.getId());

            Map<String,Object> map=new HashMap<>();

            map.put("id",dict.getId());
            map.put("name",dict.getName());
            map.put("isParent",parentCount>0?true : false);

            nodes.add(map);

        }

        return nodes;
    }

    @Override
    public List<Dict> findListByParentId(Long id) {


        Jedis jedis=jedisPool.getResource();

        List<Dict>  dictList= null;
        try {

            String value = jedis.get("shf:dict:parentId:" + id);

            if (!StringUtils.isEmpty(value)){
                dictList =  JSON.parseObject(value,List.class);

                return dictList;
            }


            dictList = dictDao.getDict(id);
            if (!CollectionUtils.isEmpty(dictList)) {
                jedis.set("zfw:dict:parentId:" + id, JSON.toJSONString(dictList));
                return dictList;
            }



        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (jedis!=null){
                jedis.close();

                if (jedis.isConnected()) {
                    try {
                        jedis.disconnect();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return dictList;
    }

    @Override
    public List<Dict> findListByDictCode(String dictCode) {
        Dict  dict=   dictDao.findListByDictCode(dictCode);

        if (dict==null){
            return null;
        }

        return    dictDao.getDict(dict.getId());

    }

    @Override
    public String getNameById(Long id) {
        return dictDao.getNameById(id);
    }

}
