package com.sdut.community.model.domain;

import lombok.Data;

import java.util.Date;

@Data
public class Blog {

    private Integer id;

    private String blogName;        //标题
    private String content;         //内容
    private Date pubdate;           //发布时间
    private String pic;             //图片
    private Integer likenum;        //点赞数
    private Integer visits;         //访问量

    private Integer uid;            //外键，用户id

}
