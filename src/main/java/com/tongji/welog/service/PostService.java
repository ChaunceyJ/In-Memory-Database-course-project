package com.tongji.welog.service;

import com.alibaba.fastjson.JSONObject;
import com.tongji.welog.dao.PostDao;
import com.tongji.welog.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    @Autowired
    PostDao postDao;

    public boolean insertPost(Post post){return postDao.insert(post);}

    public boolean deletePost(Post post){return postDao.delete(post);}

    public List<JSONObject> allPost(int index){return postDao.getPosts(index);}

    public List<JSONObject> searchPost(int index, String keyWord){return postDao.searchPosts(index, keyWord);}

}
