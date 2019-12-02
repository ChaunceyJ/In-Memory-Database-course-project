package com.tongji.welog.controller;

import com.tongji.welog.service.PostCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentController {
    @Autowired
    private PostCommentService postCommentService;

    @RequestMapping(value = "/sendComment" , method = RequestMethod.POST ,produces = "application/json")
    public ResponseEntity sendComment(
            @RequestParam(value = "userId") int userId,
            @RequestParam(value = "postId") int postId,
            @RequestParam(value = "content") int content
    ){
        return ResponseEntity.ok(postCommentService.sendComment(userId, postId, content));
    }

    @RequestMapping(value = "/viewComment" , method = RequestMethod.POST ,produces = "application/json")
    public ResponseEntity viewComment(
            @RequestParam(value = "postId") int postId
    ){
        return ResponseEntity.ok(postCommentService.viewComment(postId));
    }

}
