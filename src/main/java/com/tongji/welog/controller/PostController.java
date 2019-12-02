package com.tongji.welog.controller;

import com.tongji.welog.model.Post;
import com.tongji.welog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class PostController {

    @Autowired
    PostService postService;

    @RequestMapping(value = "/put_post" , method = RequestMethod.POST ,produces = "application/json")
    public ResponseEntity putPost(
            @RequestParam(value = "userId", required = true)
                    int userId,
            @RequestParam(value = "content", required = true)
                    String content
    ){
        Post post = new Post();
        post.setContent(content);
        post.setDeleteFlag(0);
        post.setUserId(userId);
        post.setTime(new Date());
        return ResponseEntity.ok(postService.insertPost(post));
    }

    @RequestMapping(value = "/delete_post" , method = RequestMethod.POST ,produces = "application/json")
    public ResponseEntity deletePost(
            @RequestParam(value = "userId", required = true)
                    int postId
    ){
        Post post = new Post();
        post.setPostId(postId);
        return ResponseEntity.ok(postService.deletePost(post));
    }

    @RequestMapping(value = "/show_posts" , method = RequestMethod.POST ,produces = "application/json")
    public ResponseEntity showPosts(
            @RequestParam(value = "postIndex", required = true)
                    int postIndex
    ){
        return ResponseEntity.ok(postService.allPost(postIndex));
    }





}
