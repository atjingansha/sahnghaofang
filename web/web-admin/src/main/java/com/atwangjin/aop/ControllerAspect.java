package com.atwangjin.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author WangJin
 * @create 2022-05-22 15:33
 */


@Aspect
@Component
public class ControllerAspect {


    public static final Logger log=LoggerFactory.getLogger(ControllerAspect.class);


    @Pointcut("execution(* com.atwangjin.controller..*.*(..))")
    public void point(){

    }


    @Around(value = "point()")
    public Object around(ProceedingJoinPoint pjp){


        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

        ServletRequestAttributes servletRequestAttributes= (ServletRequestAttributes) requestAttributes;

        HttpServletRequest request = servletRequestAttributes.getRequest();
        HttpServletResponse response = servletRequestAttributes.getResponse();

        String requestURI = request.getRequestURI().toString();
        String method = request.getMethod();
        String remoteHost = request.getRemoteHost();
        String classMethod = pjp.getSignature().getDeclaringTypeName() + "." + pjp.getSignature().getName();

        String param = "";
        Object[] args = pjp.getArgs();
        for (int i = 0; i < args.length; i++) {
            try {
                param += "param" + (i+1) + ":" + args[i] + ",";
            } catch (Exception e) {
                e.printStackTrace();
            }
        }



        long startTime = System.currentTimeMillis();

        try {
            Object proceed = pjp.proceed();

            return proceed;
        } catch (Throwable e) {
            e.printStackTrace();
        }finally {
            long endTime = System.currentTimeMillis();

            long time = startTime-endTime;
            String msg="requestURI="+requestURI+",method="+method+",remoteHost="+remoteHost+",classMethod="+classMethod+"time="+time;


            log.info(msg);
        }

        return null;
    }
}
