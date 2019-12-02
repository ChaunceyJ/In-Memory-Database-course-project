package com.tongji.welog.service;

import com.tongji.welog.dao.UserRelationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class UserRelationService {
    @Autowired
    private UserRelationDao userRelationDao;

    public HashMap<String, String> follow(int userId, int objectId){
        HashMap<String,String> result = new HashMap<>();
        try {
            userRelationDao.follow(userId, objectId);
        } catch (Exception e) {
            String error = e.getMessage();
            result.put("status", error);
        }
        result.put("status", "success");
        return result;
    }

    public HashMap<String, String> relationList(int userId, int type){
        HashMap<String,String> result = new HashMap<>();
        try {
            userRelationDao.relationList(userId, type);
        } catch (Exception e) {
            String error = e.getMessage();
            result.put("status", error);
        }
        result.put("status", "success");
        return result;
    }
}
