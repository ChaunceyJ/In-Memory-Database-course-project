package com.tongji.welog.controller;

import com.tongji.welog.model.Post;
import com.tongji.welog.service.PostService;
import com.tongji.welog.util.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;

@RestController
public class PostController {

    @Autowired
    PostService postService;

    /*
    @RequestMapping(value = "/api/Message/send", method = RequestMethod.POST ,produces = "application/json")
    public JSONResult putPost(
            @RequestParam
                    HashMap<String, String> range
    ){
        Post post = new Post();
        post.setContent(range.get("message_content"));
        post.setDeleteFlag(0);
        post.setUserId(Integer.parseInt(range.get("userID")));
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
            @RequestParam HashMap<String, Integer> range

            ){
        return JSONResult.success(postService.allPost(range.get("startFrom"), range.get("limitation")));
    }
    //个人主页的帖子
    @RequestMapping(value = "/api/Message/queryMessage/{user_id}" , method = RequestMethod.POST ,produces = "application/json")
    public JSONResult showUserPosts(
            @PathVariable(name = "user_id")
                    int userId,
            @RequestParam HashMap<String, Integer> range
    ){

        return JSONResult.success(postService.someonePost(userId,range.get("startFrom"), range.get("limitation")));
    }
    //关注的帖子
    @RequestMapping(value = "/api/Message/queryFollowMessage" , method = RequestMethod.POST ,produces = "application/json")
    public JSONResult showFollowMessage(
            @RequestParam HashMap<String, Integer> range
    ){
        return JSONResult.success(postService.followPost(range.get("userID"),
                range.get("startFrom"), range.get("limitation")));
    }

    @RequestMapping(value = "/api/Search/{searchKey}" , method = RequestMethod.POST ,produces = "application/json")
    public JSONResult showPosts(
            @PathVariable(name = "searchKey")
                    String searchKey,
            @RequestParam HashMap<String, Integer> range
            ){
        return JSONResult.success(postService.searchPost(range.get("startFrom"), range.get("limitation"), searchKey));
    }


    */
    @RequestMapping(value = "/api/Message/send", method = RequestMethod.POST ,produces = "application/json")
    public JSONResult putPost(
            @RequestParam(value = "message_content", required = true)
                    String content,
            @RequestParam(value = "userID", required = true)
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
//            @RequestParam(value = "startFrom", required = true)
//                    int postIndex,
//            @RequestParam(value = "limitation", required = true)
//                    int limit
            @RequestBody HashMap<String, Object> range

    ){
        int postIndex = (int)range.get("startFrom");
        int limit = (int)range.get("limitation");
        return JSONResult.success(postService.allPost(postIndex, limit));
    }
    //个人主页的帖子
    @RequestMapping(value = "/api/Message/queryMessage/{user_id}" , method = RequestMethod.POST ,produces = "application/json")
    public JSONResult showUserPosts(
            @PathVariable(name = "user_id")
                    int userId,
//            @RequestParam(value = "startFrom", required = true)
//                    int postIndex,
//            @RequestParam(value = "limitation", required = true)
//                    int limit
            @RequestBody HashMap<String, Object> range

    ){
        System.out.println(userId);
        int postIndex = (int)range.get("startFrom");
        int limit = (int)range.get("limitation");
        return JSONResult.success(postService.someonePost(userId, postIndex, limit));
    }
    //关注的帖子
    @RequestMapping(value = "/api/Message/queryFollowMessage" , method = RequestMethod.POST ,produces = "application/json")
    public JSONResult showFollowMessage(
//            @RequestParam(value = "userID", required = true)
//                    int userId,
//            @RequestParam(value = "startFrom", required = true)
//                    int postIndex,
//            @RequestParam(value = "limitation", required = true)
//                    int limit
            @RequestBody HashMap<String, Object> range

            ){
        int userId = Integer.parseInt((String)range.get("userID"));
        int postIndex = (int)range.get("startFrom");
        int limit = (int)range.get("limitation");
        return JSONResult.success(postService.followPost(userId, postIndex, limit));
    }

    @RequestMapping(value = "/api/Search/{searchKey}" , method = RequestMethod.POST ,produces = "application/json")
    public JSONResult showPosts(
            @PathVariable(name = "searchKey")
                    String searchKey,
//            @RequestParam(value = "startFrom", required = true)
//                    int postIndex,
//            @RequestParam(value = "limitation", required = true)
//                    int limit
            @RequestBody HashMap<String, Object> range

    ){
        int postIndex = (int)range.get("startFrom");
        int limit = (int)range.get("limitation");
        return JSONResult.success(postService.searchPost(postIndex, limit, searchKey));
    }


}
