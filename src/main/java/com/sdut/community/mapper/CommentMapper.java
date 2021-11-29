package com.sdut.community.mapper;

import com.sdut.community.model.domain.Comment;
import com.sdut.community.model.vo.CommentShow;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

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

    /**
     * 获取最大的id
     * @return
     */
    @Select("select max(id) from comment")
    public int getMaxId();

    @Insert("insert into comment_user (cid,uid) values (#{cid},#{uid})")
    public int insertCommentLink(Map map);

    @Select("select `comment`.content,comment_user.uid,`user`.pic FROM comment,user,comment_user " +
            "where comment.id=comment_user.cid and comment_user.uid=`user`.id and bid=#{bid} " +
            "order by likenum desc")
    public List<CommentShow> selectCommentShow(int bid);

}
