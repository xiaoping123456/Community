package com.sdut.community.mapper;

import com.sdut.community.model.domain.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {
    /**
     * 添加评论
     */
    public int insertComment(Comment comment);
    /**
     * 查询评论  根据bid
     */
    public List<Comment> selectCommentsByBid(int bid);
    /**
     * 删除评论 根据评论id
     */
    public int deleteComment(int id);
    /**
     * 点赞数+1
     */
    public int addCommentLikeNum(int cid);
    /**
     * 评论点赞表 添加记录
     */
    public int addCommentLike(int uid,int cid);
    /**
     * 点赞数-1
     */
    public int reduceCommentLikeNum(int cid);
    /**
     * 评论点赞表 删除记录
     */
    public int deleteCommentLike(int uid,int cid);

}
