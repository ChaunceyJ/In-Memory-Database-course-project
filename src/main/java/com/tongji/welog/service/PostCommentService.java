package com.tongji.welog.service;

import com.tongji.welog.dao.PostCommentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class PostCommentService {

    @Autowired
    private PostCommentDao postCommentDao;

    public HashMap<String, String> viewComment(int postId){
        HashMap<String,String> result = new HashMap<>();
        try {
            postCommentDao.viewComment(postId);
        } catch (Exception e) {
            String error = e.getMessage();
            result.put("status", error);
        }
        result.put("status", "success");
        return result;
    }

    public HashMap<String, String> sendComment(int userId, int postId, String content){
        HashMap<String,String> result = new HashMap<>();
        try {
            postCommentDao.sendComment(userId, postId, content);
        } catch (Exception e) {
            String error = e.getMessage();
            result.put("status", error);
        }
        result.put("status", "success");
        return result;
    }
}
