package com.tongji.welog.controller;

import com.tongji.welog.service.UserRelationService;
import com.tongji.welog.service.UserService;

import com.tongji.welog.util.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Hashtable;

@RequestMapping("/api/User")
@RestController
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    UserRelationService userRelationService;

    @RequestMapping(value = "/getUserPublicInfo/{user_id}" , method = RequestMethod.GET ,produces = "application/json")
    public JSONResult profilePage(
            @PathVariable(value = "user_id") int userId
    ){
        try {
            return JSONResult.custom("200", "success", userService.getUserPublicInfo(userId));
        } catch (Exception e) {
            e.printStackTrace();
            return JSONResult.custom("200", "fail", null);
        }
    }

    @RequestMapping(value = "/signIn" , method = RequestMethod.POST ,produces = "application/json")
    public JSONResult login(
            @RequestBody Hashtable<String,Object> userInfoForSignIn
    ){
        try {
            HashMap<String, Integer> result = userService.login
                    (userInfoForSignIn.get("name"), userInfoForSignIn.get("password"));
            if ( result.get("result")==1) {
                return JSONResult.custom("200", "login success", result);
            }
            else return JSONResult.custom("200","wrong username or password",null);
        } catch (Exception e) {
            return JSONResult.custom("200","error",null);
        }

    }


    @RequestMapping(value = "/signUp" , method = RequestMethod.POST ,produces = "application/json")
    public JSONResult signup(
            @RequestBody Hashtable<String,String> userInfoForSignIn
    ) throws SQLException, ClassNotFoundException {
        return JSONResult.custom("200",userService.signUp
                (userInfoForSignIn.get("name"), userInfoForSignIn.get("password")),null);
    }
}
