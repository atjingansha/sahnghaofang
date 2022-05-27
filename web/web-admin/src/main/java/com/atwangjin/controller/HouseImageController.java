package com.atwangjin.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atwangjin.base.BaseController;
import com.atwangjin.entity.HouseImage;
import com.atwangjin.result.Result;
import com.atwangjin.service.HouseImageService;
import com.atwangjin.service.HouseUserService;
import com.atwangjin.util.QiniuUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

/**
 * @author WangJin
 * @create 2022-05-21 16:54
 */
@Controller
@RequestMapping("/houseImage")
@CrossOrigin
public class HouseImageController extends BaseController {


    @Reference
    HouseImageService houseImageService;


    public static final String PAGE_UPLOED_SHOW="house/upload";

    @RequestMapping("/uploadShow/{houseId}/{type}")
    public String toUpload(Map map,
                           @PathVariable("houseId") Long houseId,
                           @PathVariable("type")Integer type){


        map.put("houseId",houseId);
        map.put("type",type);


        return PAGE_UPLOED_SHOW;

    }


    @PostMapping("/upload/{houseId}/{type}")
    @ResponseBody
    public Result upload(@PathVariable Long houseId,
                                 @PathVariable Integer type,
                                 @RequestParam(value = "file") MultipartFile[] files)
            throws Exception {
        if(files.length > 0) {
            for(MultipartFile file : files) {
                String newFileName =  UUID.randomUUID().toString() ;
                // 上传图片
                QiniuUtils.upload2Qiniu(file.getBytes(),newFileName);
                String url= "http://rc7nzkna5.hn-bkt.clouddn.com/"+ newFileName;

                HouseImage houseImage = new HouseImage();
                houseImage.setHouseId(houseId);
                houseImage.setType(type);
                houseImage.setImageName(file.getOriginalFilename());
                houseImage.setImageUrl(url);
                houseImageService.save(houseImage);
            }
        }

        return Result.ok();

    }


}
