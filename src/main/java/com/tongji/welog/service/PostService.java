package com.tongji.welog.service;

import com.alibaba.fastjson.JSONObject;
import com.tongji.welog.dao.PostDao;
import com.tongji.welog.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    @Autowired
    PostDao postDao;

    public boolean insertPost(Post post){return postDao.insert(post);}

    public boolean deletePost(Post post){return postDao.delete(post);}

    public List<JSONObject> allPost(int start, int index){return postDao.getPosts(start, index);}

    public List<JSONObject> searchPost(int start, int index, String keyWord){return postDao.searchPosts(start, index, keyWord);}

    public List<JSONObject> someonePost(int userId, int start, int index){return postDao.getUserPosts(userId, start, index);}

    public List<JSONObject> followPost(int userId, int start, int index){
        return postDao.getFollowPosts(userId, start, index);
    }


}
