package com.sdut.community.utils;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 全局异常处理类
 */
@ControllerAdvice
public class GlobalException {

    /**
     * 描述：捕获 ArithmeticException 异常
     * @param model 将Model对象注入到方法中
     * @param e 将产生异常对象注入到方法中
     * @return 指定错误页面
     */
    @ExceptionHandler(value = {RuntimeException.class})
    public String arithmeticExceptionHandle(Model model, Exception e) {

        model.addAttribute("msg", "您未登录，请先登录");

        return "error";
    }


}
