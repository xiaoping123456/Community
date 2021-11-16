package com.sdut.community.controller;

import com.auth0.jwt.JWT;
import com.sdut.community.model.domain.Blog;
import com.sdut.community.model.domain.Comment;
import com.sdut.community.service.BlogService;
import com.sdut.community.service.CommentService;
import com.sdut.community.utils.FromTokenGet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

    /**
     * 发布博客
     * @param blog
     * @return
     * @throws ParseException
     */
    @RequestMapping("/publish")
    @ResponseBody
    public boolean publishBlog(Blog blog, HttpServletRequest request) {
        //解析token，获取出id
        String token = request.getHeader("token");
        String userId = JWT.decode(token).getAudience().get(0);
        blog.setUid(Integer.parseInt(userId));

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
        String token = request.getHeader("token");
        String userId = JWT.decode(token).getAudience().get(0);
        return blogService.selectBlogsByUid(Integer.parseInt(userId));
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
        int uid = FromTokenGet.getUid(request);
        return blogService.deleteBlog(id);
    }

    /**
     * 点赞功能
     * 如果已点赞，返回false 未点赞，返回true
     * @param bid
     * @param request
     * @return
     */
    @RequestMapping("/like")
    @ResponseBody
    public boolean like(@RequestParam("bid")int bid,
                        HttpServletRequest request){
        int uid = FromTokenGet.getUid(request);
        return blogService.givelike(uid,bid);
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
        int uid = FromTokenGet.getUid(request);
        return blogService.cancleLike(uid,bid);
    }

    /**
     * 发布评论
     * @param content
     * @param request
     * @return
     */
    @RequestMapping("/comment")
    @ResponseBody
    public boolean comment(@RequestParam("content")String content,
                           HttpServletRequest request){
        int uid = FromTokenGet.getUid(request);
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setUid(uid);

        return commentService.comment(comment);
    }

    /**
     * 展示某个blog的所有评论 按点赞数降序
     * @param bid
     * @return
     */
    @RequestMapping("/showComments")
    @ResponseBody
    public List showComments(@RequestParam("bid")int bid){

        return commentService.findCommentsByBid(bid);
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

        int uid = FromTokenGet.getUid(request);
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
        int uid = FromTokenGet.getUid(request);
        return commentService.cancleCommentLike(uid,cid);
    }

}
