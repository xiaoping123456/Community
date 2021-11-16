package com.sdut.community.service;

import com.sdut.community.model.domain.Blog;

import java.util.List;

public interface BlogService {

    /**
     * 发布博客
     * @param blog
     * @return
     */
    public boolean publishBlog(Blog blog);

    /**
     * 查询一个用户的所有blog
     * @param uid
     * @return
     */
    public List<Blog> selectBlogsByUid(int uid);

    /**
     * 删除blog，根据id
     * @param id
     * @return
     */
    public boolean deleteBlog(int id);

    /**
     * 点赞
     * @param uid
     * @param bid
     * @return
     */
    public boolean givelike(int uid,int bid);

    /**
     * 取消点赞 博客
     * @param uid
     * @param bid
     * @return
     */
    public boolean cancleLike(int uid,int bid);

}
