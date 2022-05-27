package com.atwangjin.base;

import com.github.pagehelper.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author WangJin
 * @create 2022-05-16 12:02
 */

public class BaseController {



    private final static String PAGE_SUCCESS = "common/successPage";
    //提示信息
    public final static String MESSAGE_SUCCESS = "操作成功！";


    protected String successPage(String message, HttpServletRequest request) {
        request.setAttribute("messagePage", StringUtil.isEmpty(message) ? MESSAGE_SUCCESS : message);
        return PAGE_SUCCESS;
    }


    protected Map<String,Object> getFilters(HttpServletRequest request){

        Map<String,Object> filters=new HashMap<>();

        Enumeration<String> parameterNames = request.getParameterNames();

        while (parameterNames.hasMoreElements() && parameterNames !=null){

            String element = parameterNames.nextElement();

            String[] values = request.getParameterValues(element);

            if (values.length>1){
                filters.put(element,values);
            }else {
                filters.put(element,values[0]);
            }

        }


        if (!filters.containsKey("pageNum")){
            filters.put("pageNum",1);
        }
        if (!filters.containsKey("pageSize")){
            filters.put("pageSize",4);
        }

        return filters;
    }
}
