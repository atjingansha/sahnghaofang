package com.atwangjin.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atwangjin.entity.Dict;
import com.atwangjin.result.Result;
import com.atwangjin.service.DictService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author WangJin
 * @create 2022-05-18 13:39
 */

@RestController
@CrossOrigin   //允许跨域请求
@RequestMapping("/dict")
public class DictController {


    @Reference
   private DictService dictService;



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
