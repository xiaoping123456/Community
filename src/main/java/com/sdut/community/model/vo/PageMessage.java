package com.sdut.community.model.vo;

import lombok.Data;

@Data
public class PageMessage {

    private Integer currentPage;
    private Integer currentCount;
    private Integer totalCount;
    private Integer totalPage;

}
