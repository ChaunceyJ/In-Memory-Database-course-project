package com.tongji.welog.controller;

import com.tongji.welog.service.UserRelationService;
import com.tongji.welog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    UserRelationService userRelationService;

    //includes unfollow
    @RequestMapping(value = "/follow" , method = RequestMethod.POST ,produces = "application/json")
    public ResponseEntity follow(
            @RequestParam(value = "userId") int userId,
            @RequestParam(value = "objectId") int objectId
    ){
        return ResponseEntity.ok(userRelationService.follow(userId, objectId));
    }

    @RequestMapping(value = "/relationList" , method = RequestMethod.POST ,produces = "application/json")
    public ResponseEntity relationList(
            @RequestParam(value = "userId") int userId,
            @RequestParam(value = "type") int type
    ){
        return ResponseEntity.ok(userRelationService.relationList(userId, type));
    }

    @RequestMapping(value = "/profilePage" , method = RequestMethod.POST ,produces = "application/json")
    public ResponseEntity profilePage(
            @RequestParam(value = "userId") int userId
    ){
        return ResponseEntity.ok(userService.profilePage(userId));
    }

    @RequestMapping(value = "/login" , method = RequestMethod.POST ,produces = "application/json")
    public ResponseEntity login(
            @RequestParam(value = "userId") int userId,
            @RequestParam(value = "password") String password
    ){
        return ResponseEntity.ok(userService.login(userId, password));
    }
}
