package com.sdut.community.model.vo;

import com.sdut.community.model.domain.Blog;
import lombok.Data;

@Data
public class SmallBlog {

    private Integer id;

    private String blogName;
    private Integer likenum;
    private Integer visits;
    private Integer uid;
    private String head;


}
