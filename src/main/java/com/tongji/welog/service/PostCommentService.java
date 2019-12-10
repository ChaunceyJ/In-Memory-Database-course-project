package com.tongji.welog.service;

import com.tongji.welog.dao.PostCommentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class PostCommentService {

    @Autowired
    private PostCommentDao postCommentDao;

    public ArrayList<HashMap<String,Object>> viewComment(int postId, HashMap<String, Integer> range)
            throws SQLException, ClassNotFoundException {
        return postCommentDao.viewComment(postId,range.get("startFrom"), range.get("limitation"));
    }

    public void sendComment(int userId, int postId, String content) throws SQLException, ClassNotFoundException {
        postCommentDao.sendComment(userId, postId, content);
    }
}
