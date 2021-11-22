package com.sdut.community.controller;

import com.auth0.jwt.JWT;
import com.sdut.community.model.domain.User;
import com.sdut.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class UrlController {

    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String toIndex(HttpServletRequest request){
        //获取浏览器所有cookie,找到token，通过token查找用户信息，并放入session，前端通过session中是否有user，判断是否已登陆
        Cookie[] cookies = request.getCookies();
        if(cookies != null && cookies.length!=0){
            for (Cookie cookie : cookies){
                if(cookie.getName().equals("token")){
                    String token = cookie.getValue();
                    String userId = JWT.decode(token).getAudience().get(0);
                    User user = userService.selectUserById(Integer.parseInt(userId));
                    if (user!=null){
                        request.getSession().setAttribute("user",user);
                    }
                    break;
                }
            }
        }
        return "index";
    }

    @RequestMapping("/tologin")
    public String toLogin(){
        return "/login";
    }

    @RequestMapping("/toregister")
    public String toRegister(){
        return "/register";
    }

    @RequestMapping("/admain")
    public String toDomain(){
        return "/admain";
    }

    @RequestMapping("/issue")
    public String toissue(){
        return "/issue";
    }

    @RequestMapping("/userinfo")
    public String touserinfo(){
        return "/userinfo";
    }

    @RequestMapping("/blog")
    public String toblog(){
        return "/blog";
    }
}
