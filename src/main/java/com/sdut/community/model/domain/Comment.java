package com.sdut.community.model.domain;

import lombok.Data;

import java.util.Date;

@Data
public class Comment {

    private Integer id;

    private String content;         //评论内容
    private Date pubdate;           //发布时间
    private Integer likenum;        //点赞数

    private Integer bid;            //外键，blog id
    private Integer uid;            //外键，user id


}
