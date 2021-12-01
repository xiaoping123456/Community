package com.sdut.community.controller;

import com.auth0.jwt.JWT;
import com.sdut.community.annotation.UserLoginToken;
import com.sdut.community.mapper.BlogMapper;
import com.sdut.community.model.domain.Blog;
import com.sdut.community.model.domain.Comment;
import com.sdut.community.model.vo.PageMessage;
import com.sdut.community.service.BlogService;
import com.sdut.community.service.CommentService;
import com.sdut.community.utils.FromTokenGet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private CommentService commentService;
    @Autowired
    private BlogMapper blogMapper;

    /**
     * 发布博客
     * @param blog
     * @return
     * @throws ParseException
     */
    @UserLoginToken
    @RequestMapping(value = "/publish",method = RequestMethod.POST)
    @ResponseBody
    public boolean publishBlog(Blog blog, HttpServletRequest request) {
        System.out.println(blog);
        //解析token，获取出id
        int uid = FromTokenGet.getUidFromCookie(request);
        blog.setUid(uid);

        return blogService.publishBlog(blog);
    }

    /**
     * 展示一个用户的所有blog
     * @param request
     * @return
     */
    @RequestMapping("/userBlogs")
    @ResponseBody
    public List showUserBlogs(HttpServletRequest request){
        //解析token，获取出id
        int userId = FromTokenGet.getUidFromCookie(request);
        return blogService.selectBlogsByUid(userId);
    }

    /**
     * 删除blog，根据id
     * @param id
     * @param request
     * @return
     */
    @RequestMapping("/deleteBlog")
    @ResponseBody
    public boolean deleteBlogById(@RequestParam("id")int id,
                                  HttpServletRequest request){
        int uid = FromTokenGet.getUidFromCookie(request);
        return blogService.deleteBlog(id);
    }

    /**
     * 点赞功能
     * 如果已点赞，返回false 未点赞，返回true
     * @param bid
     * @param request
     * @return
     */
    @UserLoginToken
    @RequestMapping("/like")
    @ResponseBody
    public boolean like(@RequestParam("bid")int bid,
                        HttpServletRequest request){
        int uid = FromTokenGet.getUidFromCookie(request);
        return blogService.givelike(uid,bid);
    }

    /**
     * 判断用户是否已点赞
     * true 已点赞  false 未点赞
     * @param bid
     * @param request
     * @return
     */
    @RequestMapping("/judgeUserLiked")
    @ResponseBody
    public boolean judgeUserLiked(@RequestParam("bid")int bid,
                                  HttpServletRequest request){
        int uid = FromTokenGet.getUidFromCookie(request);
        if (blogMapper.findlike(uid,bid)==null){
            return false;
        }else{
            return true;
        }
    }

    /**
     * 取消点赞
     * @param bid
     * @param request
     * @return
     */
    @RequestMapping("/cancleLike")
    @ResponseBody
    public boolean cancleLike(@RequestParam("bid")int bid,
                              HttpServletRequest request){
        int uid = FromTokenGet.getUidFromCookie(request);
        return blogService.cancleLike(uid,bid);
    }


    /**
     * 发布评论
     * @param content
     * @param request
     * @return
     */
    @UserLoginToken
    @RequestMapping("/comment")
    @ResponseBody
    public boolean comment(@RequestParam("content")String content,
                           @RequestParam("bid")Integer bid,
                           HttpServletRequest request){
        int uid = FromTokenGet.getUidFromCookie(request);

        return commentService.comment(content,bid,uid);
    }

    /**
     * 展示某个blog的所有评论 按点赞数降序
     * @param bid
     * @return
     */
    @RequestMapping("/showComments")
    @ResponseBody
    public List showComments(@RequestParam("bid")int bid){

        return commentService.selectCommentShow(bid);
    }

    /**
     * 用户点赞评论
     * @param cid
     * @param request
     * @return
     */
    @RequestMapping("/likeComment")
    @ResponseBody
    public boolean likeComment(@RequestParam("cid")int cid,
                               HttpServletRequest request){

        int uid = FromTokenGet.getUidFromCookie(request);
        return commentService.likeComment(uid,cid);
    }

    /**
     * 取消对评论的点赞
     * @param cid
     * @param request
     * @return
     */
    @RequestMapping("/cancleCommentLike")
    @ResponseBody
    public boolean cancleCommentLike(@RequestParam("cid")int cid,
                                     HttpServletRequest request){
        int uid = FromTokenGet.getUidFromCookie(request);
        return commentService.cancleCommentLike(uid,cid);
    }

    /**
     * 获取用户博客的页码信息
     * @param request
     * @return
     */
    @RequestMapping("/getUserBlogsPageMessage")
    @ResponseBody
    public PageMessage getUserBlogsPageMessage(HttpServletRequest request){
        int uid = FromTokenGet.getUidFromCookie(request);
        //获取总数
        int totalCount = blogMapper.countUserBlogs(uid);
        //每页有几个blog
        int currentCount=8;
        PageMessage pageMessage = new PageMessage();
        pageMessage.setCurrentCount(currentCount);
        pageMessage.setTotalCount(totalCount);
        pageMessage.setTotalPage((int) Math.ceil(1.0*totalCount/currentCount));
        return pageMessage;
    }

    /**
     * 获取 blog页 的 页码信息
     * @return
     */
    @RequestMapping("/getAllUserBlogsPageMessage")
    @ResponseBody
    public PageMessage getAllUserBlogsPageMessage(){
        //获取总数
        int totalCount = blogMapper.countAllBlogs();
        //每页有几个blog
        int currentCount = 10;
        PageMessage pageMessage = new PageMessage();
        pageMessage.setCurrentCount(currentCount);
        pageMessage.setTotalCount(totalCount);
        pageMessage.setTotalPage((int) Math.ceil(1.0*totalCount/currentCount));
        return pageMessage;
    }

    /**
     * 获取 用户点赞过的blogs 的 页码信息
     * @return
     */
    @RequestMapping("/getLikedBlogsPageMessage")
    @ResponseBody
    public PageMessage getAllUserBlogsPageMessage(HttpServletRequest request){
        //获取总数
        int uid = FromTokenGet.getUidFromCookie(request);
        int totalCount = blogMapper.countLikedFromUser(uid);
        //每页有几个blog
        int currentCount = 6;
        PageMessage pageMessage = new PageMessage();
        pageMessage.setCurrentCount(currentCount);
        pageMessage.setTotalCount(totalCount);
        pageMessage.setTotalPage((int) Math.ceil(1.0*totalCount/currentCount));
        return pageMessage;
    }

    /**
     * 展示当前页的所有blog 用户页
     * 每页8个  在方法参数中
     * @param currentPage
     * @param request
     * @return
     */
    @RequestMapping("/showUserBlogsCurrentPage")
    @ResponseBody
    public List<Blog> showUserBlogsCurrentPage(@RequestParam("currentPage")Integer currentPage,
                                               HttpServletRequest request){
        int uid = FromTokenGet.getUidFromCookie(request);
        List<Blog> blogs = blogService.selectCurrentPageBlogs(uid,currentPage,8);
        return blogs;
    }

    /**
     * 展示用户点赞的blog 当前页
     * 每页6个  在方法参数中
     * @param currentPage
     * @return
     */
    @RequestMapping("/showLikedBlogsCurrentPage")
    @ResponseBody
    public List showLikedBlogsCurrentPage(@RequestParam("currentPage")Integer currentPage,
                                          HttpServletRequest request){
        int uid = FromTokenGet.getUidFromCookie(request);
        return blogService.getUserLikedBlogs(uid,currentPage,6);
    }


    /**
     * 展示当前页的所有blog 总的blog
     * 每页10个  在方法参数中
     * @param currentPage
     * @return
     */
    @RequestMapping("/showAllBlogsCurrentPage")
    @ResponseBody
    public List<Blog> showBlogsCurrentPage(@RequestParam("currentPage")Integer currentPage){
        List<Blog> blogs = blogService.selectAllBlogsInCurrentPage(currentPage,10);
        return blogs;
    }


    @RequestMapping("/hotBlogs")
    @ResponseBody
    public List showHotBlogs(){
        List blogs = blogService.showHotBlogs();
        return blogs;
    }



}
