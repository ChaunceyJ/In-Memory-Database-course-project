package com.tongji.welog.controller;

import com.tongji.welog.service.PostCommentService;
import com.tongji.welog.util.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RequestMapping(value = "/api/Comment")
@RestController
public class CommentController {
    @Autowired
    private PostCommentService postCommentService;

    @RequestMapping(value = "/add/{message_id}" , method = RequestMethod.POST ,produces = "application/json")
    public ResponseEntity sendComment(
//            @RequestParam(value = "userID")
//                    int userId,
            @PathVariable(value = "message_id") int postId,
            @RequestBody HashMap<String, String> contentAndUserId
    ){
        try {
            int userId = Integer.parseInt(contentAndUserId.get("userID"));
            String content = contentAndUserId.get("comment_content");
            System.out.println(userId);
            System.out.println(content);
            postCommentService.sendComment(userId,
                    postId, content);
            return JSONResult.custom("200","success",null);
        } catch (Exception e) {
            e.printStackTrace();
            return JSONResult.custom("200", "fail",null);
        }
    }

    @RequestMapping(value = "/queryComments/{message_id}" , method = RequestMethod.POST ,produces = "application/json")
    public ResponseEntity viewComment(
            @PathVariable(value = "message_id") int postId,
            @RequestBody HashMap<String, Integer> range
    ){
        try {
            System.out.println("===================");
            return JSONResult.custom("200","success",
                    postCommentService.viewComment(postId,range));
        } catch (Exception e) {
            e.printStackTrace();
            return JSONResult.custom("200", "fail",null);
        }
    }

}
