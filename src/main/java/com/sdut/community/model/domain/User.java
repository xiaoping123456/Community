package com.sdut.community.model.domain;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    private Integer id;
    private String username;
    private String email;
    private String phone;
    private String password;
    private String sex;
    private String info;
    private String pic;

    private String likeBlogs;   //用户喜欢的blog的bid集合 ","分割

    //用户权限
    private String role;
}
