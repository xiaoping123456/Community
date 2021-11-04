package com.sdut.community.controller;

import com.sdut.community.annotation.UserLoginToken;
import com.sdut.community.model.vo.UserVo;
import com.sdut.community.service.MailService;
import com.sdut.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping("/userLogin")
    @ResponseBody
    public Object login(@RequestParam("email")String text,
                        @RequestParam("password")String password){
        System.out.println(text);
        return mailService.loginIn(text, password);
    }

    @UserLoginToken
    @GetMapping("/getMessage")
    @ResponseBody
    public String getMessage() {
        return "你已通过验证";
    }

}
