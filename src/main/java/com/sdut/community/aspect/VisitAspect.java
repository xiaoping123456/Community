package com.sdut.community.aspect;

import com.sdut.community.service.BlogService;
import com.sdut.community.utils.RedisUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;


/**
 * 面向 showBlogInfoByBid 方法  向redis中更改浏览量
 */
@Aspect
@Component
public class VisitAspect {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private BlogService blogService;

    @Pointcut("execution(* com.sdut.community.controller.UrlController.showBlogInfoByBid(..))")
    public void visitCut(){

    }

    @Before(value = "visitCut()")
    public void addVisit(JoinPoint joinPoint){
        System.out.println("-------------前置通知 访问+1--------------------");
        System.out.println(joinPoint.getArgs()[0]);
        System.out.println("----------------------------------------------");
        int bid = (int) joinPoint.getArgs()[0];
        String visits = (String) redisUtils.get(bid+"-visits");
        if (visits==null){
            redisUtils.set(bid+"-visits","1");
        }else{
            redisUtils.incr(bid+"-visits",1);
        }
    }

}
