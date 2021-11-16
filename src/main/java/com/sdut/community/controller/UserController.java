package com.sdut.community.controller;

import com.alibaba.fastjson.JSONObject;
import com.sdut.community.annotation.UserLoginToken;
import com.sdut.community.model.vo.UserVo;
import com.sdut.community.service.MailService;
import com.sdut.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    /**
     * 发送邮件验证码
     * @param email
     * @param httpSession
     * @return
     */
    @PostMapping("/sendEmail")
    @ResponseBody
    public String sendEmail(String email, HttpSession httpSession){
        mailService.sendMimeMail(email, httpSession);
        return "sucess";
    }

    /**
     * 注册
     * @param userVo
     * @param httpSession
     * @return
     */
    @RequestMapping("/register")
    @ResponseBody
    public boolean register(UserVo userVo,HttpSession httpSession){
        if(mailService.registered(userVo,httpSession)){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 登录
     * @param text
     * @param password
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/userLogin")
    @ResponseBody
    public Object login(@RequestParam("text")String text,
                        @RequestParam("password")String password,
                        HttpServletRequest request,
                        HttpServletResponse response){
        JSONObject jsonObject = (JSONObject) mailService.loginIn(text, password);
        String token = jsonObject.getString("token");
        if (token!=null){
            Cookie cookie = new Cookie("token",token);
            cookie.setPath("/");
            cookie.setMaxAge(3600*24*7);
            cookie.setDomain("localhost");
            response.addCookie(cookie);
            request.getSession().setAttribute("token",token);
        }
        return jsonObject;
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request,HttpServletResponse response){
        //移除session
        request.getSession().removeAttribute("user");
        //移除cookie
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie:cookies){
            if(cookie.getName().equals("token")){
                cookie.setPath("/");
                cookie.setMaxAge(0);
                cookie.setDomain("localhost");
                response.addCookie(cookie);
                break;
            }
        }
        return "index";
    }



//    @UserLoginToken
//    @GetMapping("/getMessage")
//    @ResponseBody
//    public String getMessage() {
//        return "你已通过验证";
//    }

}
