package com.sdut.community.mapper;

import com.sdut.community.model.domain.Blog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BlogMapper {
    /**
     * 添加博客
     */
    public int insertBlog(Blog blog);
    /**
     * 查询博客  根据uid
     */
    public List<Blog> selectBlogsByUid(int uid);
    /**
     * 修改博客内容
     */
    public int updateBlog(Blog blog);
    /**
     * 删除博客 根据博客id
     */
    public int deleteBlog(int id);
    /**
     * 点赞，likenum+1 博客表点赞数+1
     */
    public int updateBlogLikenum(int id);
    /**
     * 点赞 关系表中添加点赞关系
     */
    public int givelike(int uid,int bid);
    /**
     * 查询用户是否已点赞
     */
    public Integer findlike(int uid,int bid);
    /**
     * 取消点赞 likenum-1 博客表点赞数-1
     */
    public int reduceLikenum(int id);
    /**
     * 取消点赞，删除givelike表中的点赞关系
     */
    public int deleteGiveLike(int uid,int bid);

}
