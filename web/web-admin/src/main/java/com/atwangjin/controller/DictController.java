package com.atwangjin.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atwangjin.entity.Dict;
import com.atwangjin.result.Result;
import com.atwangjin.service.DictService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author WangJin
 * @create 2022-05-18 13:39
 */

@Controller
@RequestMapping("/dict")
public class DictController {


    @Reference
   private DictService dictService;



    /**
     * [{ id:'0331',name:'n3.3.n1',isParent:true}]
     * @param id
     * @return
     */
    @GetMapping(value = "/findZnodes")
    @ResponseBody
    public Result findByParentId(@RequestParam(value = "id", defaultValue = "0") Long id) {
        List<Map<String,Object>> zNodes = dictService.getDict(id);
        return Result.ok(zNodes);
    }


    @RequestMapping("/findListByParentId/{id}")
    @ResponseBody
    public Result findListByParentId(@PathVariable("id")Long id){
      List<Dict> child= dictService.findListByParentId(id);

      return Result.ok(child);
    }

    @RequestMapping("/findListByDictCode/{dictCode}")
    @ResponseBody
    public Result findListByDictCode(@PathVariable("dictCode")String dictCode){
        List<Dict> child= dictService.findListByDictCode(dictCode);

        return Result.ok(child);
    }
}
