package com.sdut.community.mapper;

import com.sdut.community.model.domain.Blog;
import com.sdut.community.model.vo.SmallBlog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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

    /**
     * 获取用户发布的博客的总数
     * @param uid
     * @return
     */
    @Select("select count(*) from blog where uid = #{uid}")
    public int countUserBlogs(int uid);

    /**
     * 获取全部博客的总数
     * @return
     */
    @Select("select count(*) from blog")
    public int countAllBlogs();

    /**
     * 查询当前页面的blogs  用户的
     * @param uid
     * @param start
     * @param pageSize
     * @return
     */
    @Select("select * from blog where uid=#{uid} limit ${start},${pageSize}")
    public List<Blog> selectCurrentPageBlogs(int uid,int start,int pageSize);

    /**
     * 查询当前页面的blogs 总的 按时间先后顺序
     * @param start
     * @param pageSize
     * @return
     */
    @Select("select id,blog_name,likenum,visits,uid from blog order by id desc limit ${start},${pageSize}")
    public List<SmallBlog> selectAllBlogsInCurrentPage(int start, int pageSize);


}
