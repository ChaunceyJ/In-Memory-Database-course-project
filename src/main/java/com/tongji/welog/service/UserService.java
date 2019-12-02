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

    }

    public HashMap<String, String> login(int userId, String password){

    }
}
