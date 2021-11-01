package com.sdut.community.controller;

import com.sdut.community.model.vo.UserVo;
import com.sdut.community.service.MailService;
import com.sdut.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    @PostMapping("/sendEmail")
    @ResponseBody
    public String sendEmail(String email, HttpSession httpSession){
        mailService.sendMimeMail(email, httpSession);
        return "sucess";
    }

    @RequestMapping("/register")
    @ResponseBody
    public boolean register(UserVo userVo,HttpSession httpSession){
        if(mailService.registered(userVo,httpSession)){
            return true;
        }else{
            return false;
        }
    }

    @RequestMapping("/login")
    @ResponseBody
    public String login(@RequestParam("email")String email,
                        @RequestParam("password")String password){
        if (mailService.loginIn(email,password)){
            return "登录成功";
        }else{
            return "邮箱或密码错误";
        }
    }

}
