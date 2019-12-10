package com.tongji.welog.controller;

import com.alibaba.fastjson.JSONObject;
import com.tongji.welog.model.Likes;
import com.tongji.welog.service.LikesService;
import com.tongji.welog.utils.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class LikesController {

    @Autowired
    LikesService likesService;

    @RequestMapping(value = "/api/Like/{message_id}" , method = RequestMethod.GET ,produces = "application/json")
    public JSONResult like(
            @PathVariable(name = "message_id")
                    int postId,
            @CookieValue(value = "user_id", required = true)
                    int userId
    ){
        Likes likes = new Likes();
        likes.setPostId(postId);
        likes.setUserId(userId);
        return JSONResult.success(likesService.insert(likes));
    }

    @RequestMapping(value = "/api/Like/cancel/{message_id}" , method = RequestMethod.GET ,produces = "application/json")
    public JSONResult cancelLike(
            @PathVariable(name = "message_id")
                    int postId,
            @CookieValue(value = "user_id", required = true)
                    int userId
    ){
        Likes likes = new Likes();
        likes.setPostId(postId);
        likes.setUserId(userId);
        return JSONResult.success(likesService.delete(likes));
    }

    @RequestMapping(value = "/api/Like/CheckUserLikesMessage" , method = RequestMethod.POST ,produces = "application/json")
    public JSONResult ifLike(
            @RequestParam(name = "message_id",  required = true)
                    int postId,
            @CookieValue(value = "user_id", required = true)
                    int userId
    ){
        Likes likes = new Likes();
        likes.setPostId(postId);
        likes.setUserId(userId);
        JSONObject re = new JSONObject();
        re.put("like", likesService.exists(likes));
        return JSONResult.success(re);
    }
}

