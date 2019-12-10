package com.tongji.welog.service;

import com.tongji.welog.dao.UserRelationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

@Service
public class UserRelationService {
    @Autowired
    private UserRelationDao userRelationDao;

    public String follow(int userId, int objectId){
        try {
            userRelationDao.follow(userId, objectId);
            return "success";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "error";
        }
    }

    public String unfollow(int userId, int objectId){
        try {
            userRelationDao.unfollow(userId, objectId);
            return "success";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "error";
        }
    }

    public ArrayList<HashMap<String,Object>> relationList(int userId, HashMap<String, Integer> range, String type)
            throws SQLException, ClassNotFoundException {
        System.out.println(range.get("startFrom"));
        System.out.println(range.get("limitation"));
        return userRelationDao.relationList(userId, range.get("startFrom"),
                range.get("limitation"), type);
    }

    public Hashtable<String,Boolean> ifFollow(int actor_id, int object_id) throws SQLException, ClassNotFoundException {
        Hashtable<String,Boolean> temp = new Hashtable<>();
        temp.put("if_following",userRelationDao.ifFollow(actor_id, object_id));
        return temp;
    }

}
