package com.sdut.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UrlController {

    @RequestMapping("/")
    public String toIndex(){
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
}
