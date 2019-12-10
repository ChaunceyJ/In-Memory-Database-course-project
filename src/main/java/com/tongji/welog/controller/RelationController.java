package com.tongji.welog.controller;

import com.tongji.welog.service.UserRelationService;
import com.tongji.welog.service.UserService;
import com.tongji.welog.util.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Hashtable;

@RequestMapping("/api/Relation")
@RestController
public class RelationController {
    @Autowired
    UserService userService;
    @Autowired
    UserRelationService userRelationService;

    @RequestMapping(value = "/follow/{user_id}" , method = RequestMethod.GET ,produces = "application/json")
    public JSONResult follow(
            @CookieValue(value = "user_id") int userId,
            @PathVariable(value = "user_id") int objectId
    ){
        return JSONResult.custom("200",userRelationService.follow(userId, objectId),null);
    }

    @RequestMapping(value = "/cancelFollowingTo/{user_id}" , method = RequestMethod.GET ,produces = "application/json")
    public JSONResult unfollow(
            @CookieValue(value = "user_id") int userId,
            @PathVariable(value = "user_id") int objectId
    ){
        return JSONResult.custom("200",userRelationService.unfollow(userId, objectId),null);
    }

    @RequestMapping(value = "/if_following" , method = RequestMethod.POST ,produces = "application/json")
    public JSONResult iffollow(
            @RequestParam(value = "follow_id") int actor_id,
            @RequestParam(value = "be_followed_id") int object_id
    ){
        try {
            return JSONResult.custom("200","success",userRelationService.ifFollow(actor_id, object_id));
        }
        catch (Exception e) {
            e.printStackTrace();
            return JSONResult.custom("200", "fail", null);
        }
    }

    @RequestMapping(value = "/if_following_by_me/{be_followed_id}" , method = RequestMethod.GET ,produces = "application/json")
    public JSONResult iffollowbyme(
            @CookieValue(value = "user_id") int actor_id,
            @PathVariable(value = "be_followed_id") int object_id
    ){
        try {
            return JSONResult.custom("200","success",userRelationService.ifFollow(actor_id, object_id));
        }
        catch (Exception e) {
            e.printStackTrace();
            return JSONResult.custom("200", "fail", null);
        }
    }


    @RequestMapping(value = "/queryFollowingFor/{user_id}" , method = RequestMethod.POST ,produces = "application/json")
    public JSONResult queryFollowingFor(
            @PathVariable(value = "user_id") int userId,
            @RequestBody HashMap<String, Integer> range
    ){
        try {
            return JSONResult.custom("200","success",
                    userRelationService.relationList(userId, range,"following"));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return JSONResult.custom("200", "fail",null);
        }
    }

    @RequestMapping(value = "/queryFollowersFor/{user_id}" , method = RequestMethod.POST ,produces = "application/json")
    public ResponseEntity queryFollowersFor(
            @PathVariable(value = "user_id") int userId,
            @RequestBody HashMap<String, Integer> range
    ){
        try {
            return JSONResult.custom("200","success",
                    userRelationService.relationList(userId, range,"follower"));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return JSONResult.custom("200", "fail",null);
        }
    }


}
