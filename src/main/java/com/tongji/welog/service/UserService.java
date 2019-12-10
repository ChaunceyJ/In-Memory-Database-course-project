package com.tongji.welog.service;

import com.tongji.welog.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.HashMap;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public HashMap<String,Object> getUserPublicInfo(int userId) throws SQLException, ClassNotFoundException {
        return userDao.getUserPublicInfo(userId);
    }

    public HashMap<String, Integer> login(Object userId, Object password)
            throws SQLException, ClassNotFoundException {
        return userDao.login(userId, password);
    }


    public String signUp(String name, String password) throws SQLException, ClassNotFoundException {
        return userDao.signUp(name, password);
    }
}
