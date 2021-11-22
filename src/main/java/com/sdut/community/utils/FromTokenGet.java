package com.sdut.community.utils;

import com.auth0.jwt.JWT;
import com.sdut.community.model.domain.User;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class FromTokenGet {

    /**
     * 从请求头中解析token
     */
    public static Integer getUid(HttpServletRequest request){
        String token = request.getHeader("token");
        String userId = JWT.decode(token).getAudience().get(0);
        return Integer.parseInt(userId);
    }

    /**
     * 从cookie中解析token
     * @param request
     * @return
     */
    public static Integer getUidFromCookie(HttpServletRequest request){
        Integer uid = null;
        Cookie[] cookies = request.getCookies();
        if(cookies != null && cookies.length!=0){
            for (Cookie cookie : cookies){
                if(cookie.getName().equals("token")){
                    String token = cookie.getValue();
                    String userId = JWT.decode(token).getAudience().get(0);
                    uid = Integer.parseInt(userId);
                }
            }
        }
        return uid;
    }

}
