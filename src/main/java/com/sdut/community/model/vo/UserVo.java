package com.sdut.community.model.vo;

import lombok.Data;

@Data
public class UserVo {

    private String username;
    private String email;
    private String password;
    //邮箱验证码
    private String code;

}
