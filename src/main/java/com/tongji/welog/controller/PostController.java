package com.tongji.welog.controller;

import com.tongji.welog.model.Post;
import com.tongji.welog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
public class PostController {

    @Autowired
    PostService postService;

    @RequestMapping(value = "/api/Message/send" , method = RequestMethod.POST ,produces = "application/json")
    public ResponseEntity putPost(
            @RequestParam(value = "message_content", required = true)
                    String content,
            @CookieValue(value = "user_id", required = true)
                    int userId
    ){
        Post post = new Post();
        post.setContent(content);
        post.setDeleteFlag(0);
        post.setUserId(userId);
        post.setTime(new Date());
        return ResponseEntity.ok(postService.insertPost(post));
    }

    @RequestMapping(value = "/api/Message/delete" , method = RequestMethod.POST ,produces = "application/json")
    public ResponseEntity deletePost(
            @RequestParam(value = "message_id", required = true)
                    int postId
    ){
        Post post = new Post();
        post.setPostId(postId);
        return ResponseEntity.ok(postService.deletePost(post));
    }

    @RequestMapping(value = "/api/Message/queryMessage" , method = RequestMethod.POST ,produces = "application/json")
    public ResponseEntity showPosts(
            @RequestParam(value = "range", required = true)
                    int postIndex
    ){
        return ResponseEntity.ok(postService.allPost(postIndex));
    }

    @RequestMapping(value = "/api/Search" , method = RequestMethod.POST ,produces = "application/json")
    public ResponseEntity showPosts(
            @RequestParam(value = "searchKey", required = true)
                    String searchKey,
            @RequestParam(value = "range", required = true)
                    int index
            ){
        return ResponseEntity.ok(postService.searchPost(index, searchKey));
    }


}
