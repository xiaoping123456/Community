package com.sdut.community.utils;

import com.auth0.jwt.JWT;

import javax.servlet.http.HttpServletRequest;

public class FromTokenGet {

    public static Integer getUid(HttpServletRequest request){
        //解析token，获取出id
        String token = request.getHeader("token");
        String userId = JWT.decode(token).getAudience().get(0);
        return Integer.parseInt(userId);
    }

}
