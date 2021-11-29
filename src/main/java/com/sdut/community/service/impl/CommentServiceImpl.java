package com.sdut.community.service.impl;

import com.sdut.community.mapper.CommentMapper;
import com.sdut.community.model.domain.Comment;
import com.sdut.community.model.vo.CommentShow;
import com.sdut.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public boolean comment(String content,int bid,int uid) {
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setBid(bid);
        //获取当前系统时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String pubdateStr = df.format(new Date());
        Date pubdate = null;
        try {
            pubdate = df.parse(pubdateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        comment.setPubdate(pubdate);
        //初始化点赞数
        comment.setLikenum(0);
        //插入评论
        if(commentMapper.insertComment(comment)!=0){
            int cid = commentMapper.getMaxId();
            Map<String,Object> map = new HashMap<>();
            map.put("cid",cid);
            map.put("uid",uid);
            if (commentMapper.insertCommentLink(map)!=0){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    @Override
    public List<Comment> findCommentsByBid(int bid) {

        return commentMapper.selectCommentsByBid(bid);
    }

    @Override
    public boolean likeComment(int uid, int cid) {

        //点赞数+1
        commentMapper.addCommentLikeNum(cid);
        //评论点赞表中添加信息
        commentMapper.addCommentLike(uid,cid);
        return true;
    }

    @Override
    public boolean cancleCommentLike(int uid, int cid) {
        //点赞数-1
        commentMapper.reduceCommentLikeNum(cid);
        //评论点赞表中删除信息
        commentMapper.deleteCommentLike(uid,cid);
        return true;
    }

    @Override
    public List<CommentShow> selectCommentShow(int bid) {
        return commentMapper.selectCommentShow(bid);
    }


}
