package com.tongji.welog.controller;

import com.alibaba.fastjson.JSONObject;
import com.tongji.welog.model.Likes;
import com.tongji.welog.service.LikesService;
import com.tongji.welog.util.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
public class LikesController {

    @Autowired
    LikesService likesService;

    @RequestMapping(value = "/api/Like/like/{message_id}" , method = RequestMethod.POST ,produces = "application/json")
    public JSONResult like(
            @PathVariable(name = "message_id")
                    int postId,
            @RequestBody HashMap<String, Integer> range
    ){
        Likes likes = new Likes();
        likes.setPostId(postId);
        likes.setUserId(range.get("userID"));
        return JSONResult.success(likesService.insert(likes));
    }

    @RequestMapping(value = "/api/Like/cancel/{message_id}" , method = RequestMethod.POST ,produces = "application/json")
    public JSONResult cancelLike(
            @PathVariable(name = "message_id")
                    int postId,
            @RequestBody HashMap<String, Integer> range
    ){
        Likes likes = new Likes();
        likes.setPostId(postId);
        likes.setUserId(range.get("userID"));
        return JSONResult.success(likesService.delete(likes));
    }

    @RequestMapping(value = "/api/Like/checkUserLikesMessage", method = RequestMethod.POST, produces = "application/json")
    public JSONResult ifLike(
//            @RequestParam(name = "message_id",  required = true)
//                    String message_id,
//            @RequestParam(name = "user_id",  required = true)
//                    String user_id
            @RequestBody HashMap<String, String> range

    ){
        System.out.println(range);
        Likes likes = new Likes();
        likes.setPostId(Integer.parseInt(range.get("message_id")));
        likes.setUserId(Integer.parseInt(range.get("user_id")));
        JSONObject re = new JSONObject();
        re.put("like", likesService.exists(likes));
        return JSONResult.success(re);
    }

}

