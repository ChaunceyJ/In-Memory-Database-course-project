package com.tongji.welog.service;

import com.tongji.welog.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public HashMap<String, String> profilePage(int userId){
        HashMap<String,String> result = new HashMap<>();
        try {
            userDao.profilePage(userId);
        } catch (Exception e) {
            String error = e.getMessage();
            result.put("status", error);
        }
        result.put("status", "success");
        return result;
    }

    public HashMap<String, String> login(int userId, String password){
        HashMap<String,String> result = new HashMap<>();
        try {
            userDao.login(userId, password);
        } catch (Exception e) {
            String error = e.getMessage();
            result.put("status", error);
        }
        result.put("status", "success");
        return result;
    }
}
