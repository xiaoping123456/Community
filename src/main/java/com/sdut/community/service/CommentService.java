package com.sdut.community.service;

import com.sdut.community.model.domain.Comment;
import com.sdut.community.model.vo.CommentShow;

import java.util.List;

public interface CommentService {

    /**
     * 发布评论
     */
    public boolean comment(String content,int bid,int uid);
    /**
     * 查询评论 根据bid
     */
    public List<Comment> findCommentsByBid(int bid);
    /**
     * 点赞评论
     */
    public boolean likeComment(int uid,int cid);
    /**
     * 取消点赞评论
     */
    public boolean cancleCommentLike(int uid,int cid);

    public List<CommentShow> selectCommentShow(int bid);
}
