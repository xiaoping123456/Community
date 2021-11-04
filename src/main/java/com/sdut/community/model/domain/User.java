package com.sdut.community.model.domain;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String username;
    private String email;
    private String phone;
    private String password;
    private String sex;
    //用户权限
    private String role;
}
