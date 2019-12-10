package com.tongji.welog.service;

import com.tongji.welog.dao.LikesDao;
import com.tongji.welog.model.Likes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikesService {

    @Autowired
    LikesDao likesDao;

    public boolean delete(Likes likes){
        return likesDao.delete(likes);
    }

    public boolean insert(Likes likes){
        return likesDao.insert(likes);
    }

    public boolean exists(Likes likes){
        return likesDao.exists(likes);
    }
}

