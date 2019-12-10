package com.tongji.welog.controller;

import com.tongji.welog.model.Post;
import com.tongji.welog.service.PostService;
import com.tongji.welog.utils.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
public class PostController {

    @Autowired
    PostService postService;

    @RequestMapping(value = "/api/Message/send", method = RequestMethod.POST ,produces = "application/json")
    public JSONResult putPost(
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
        return JSONResult.custom("200","success",postService.insertPost(post));
    }

    @RequestMapping(value = "/api/Message/delete" , method = RequestMethod.POST ,produces = "application/json")
    public JSONResult deletePost(
            @RequestParam(value = "message_id", required = true)
                    int postId
    ){
        Post post = new Post();
        post.setPostId(postId);
        return JSONResult.success(postService.deletePost(post));
    }

    @RequestMapping(value = "/api/Message/queryNewestMessage" , method = RequestMethod.POST ,produces = "application/json")
    public JSONResult showPosts(
            @RequestParam(value = "startFrom", required = true)
                    int postIndex,
            @RequestParam(value = "limitation", required = true)
                    int limit

            ){
        return JSONResult.success(postService.allPost(postIndex, limit));
    }
    //个人主页的帖子
    @RequestMapping(value = "/api/Message/queryMessage/{user_id}" , method = RequestMethod.POST ,produces = "application/json")
    public JSONResult showUserPosts(
            @PathVariable(name = "user_id")
                    int userId,
            @RequestParam(value = "startFrom", required = true)
                    int postIndex,
            @RequestParam(value = "limitation", required = true)
                    int limit
    ){

        return JSONResult.success(postService.someonePost(userId, postIndex, limit));
    }
    //关注的帖子
    @RequestMapping(value = "/api/Message/queryFollowMessage" , method = RequestMethod.POST ,produces = "application/json")
    public JSONResult showFollowMessage(
            @CookieValue(value = "user_id", required = true)
                    int userId,
            @RequestParam(value = "startFrom", required = true)
                    int postIndex,
            @RequestParam(value = "limitation", required = true)
                    int limit

    ){
        return JSONResult.success(postService.followPost(userId, postIndex, limit));
    }

    @RequestMapping(value = "/api/Search/{searchKey}" , method = RequestMethod.POST ,produces = "application/json")
    public JSONResult showPosts(
            @PathVariable(name = "searchKey")
                    String searchKey,
            @RequestParam(value = "startFrom", required = true)
                    int postIndex,
            @RequestParam(value = "limitation", required = true)
                    int limit
            ){
        return JSONResult.success(postService.searchPost(postIndex, limit, searchKey));
    }



}
