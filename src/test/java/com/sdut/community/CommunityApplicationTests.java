package com.sdut.community;

import com.sdut.community.mapper.CommentMapper;
import com.sdut.community.service.BlogService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CommunityApplicationTests {

	@Autowired
	private CommentMapper commentMapper;

	@Autowired
	private BlogService blogService;

	@Test
	void contextLoads() {
		System.out.println(commentMapper.selectCommentShow(16));
	}

//	@Test
//	void testGetLikedBlogs(){
//		System.out.println(blogService.getUserLikedBlogs(1));
//	}

}
