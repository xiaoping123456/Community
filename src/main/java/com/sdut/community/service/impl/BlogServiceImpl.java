package com.sdut.community.service.impl;

import com.sdut.community.mapper.BlogMapper;
import com.sdut.community.model.domain.Blog;
import com.sdut.community.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogMapper blogMapper;

    @Override
    public boolean publishBlog(Blog blog) {

        //获取当前系统时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String pubdateStr = df.format(new Date());
        Date pubdate = null;
        try {
            pubdate = df.parse(pubdateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        blog.setPubdate(pubdate);
        //初始化 点赞数和访问量
        blog.setLikenum(0);
        blog.setVisits(0);
        if(blogMapper.insertBlog(blog)!=0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public List<Blog> selectBlogsByUid(int uid) {

        return blogMapper.selectBlogsByUid(uid);
    }

    @Override
    public boolean deleteBlog(int id) {
        if(blogMapper.deleteBlog(id)!=0){
            return true;
        }else{
            return false;
        }
    }


    @Override
    public boolean givelike(int uid, int bid) {
        //如果查询结果不为空，说明已点赞 返回
        if(blogMapper.findlike(uid,bid)==null){
            blogMapper.givelike(uid,bid);
            blogMapper.updateBlogLikenum(bid);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean cancleLike(int uid, int bid) {
        //blog表中总的点赞数-1
        blogMapper.reduceLikenum(bid);
        //删除givelike点赞关系表中的 点赞记录
        blogMapper.deleteGiveLike(uid,bid);
        return true;
    }
}
