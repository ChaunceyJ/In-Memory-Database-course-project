package com.tongji.welog.dao;

import com.alibaba.fastjson.JSONObject;
import com.tongji.welog.model.Post;
import com.tongji.welog.model.User;
import oracle.jdbc.OracleTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedCaseInsensitiveMap;

import java.sql.ResultSet;
import java.util.*;

@Repository
public class PostDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean insert(Post post){
//        SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
//                .withProcedureName("PUT_A_POST")
//                . declareParameters(
//                        new SqlParameter("CONTEXT", OracleTypes.VARCHAR),
//                        new SqlParameter("USER_ID", OracleTypes.NUMBER),
//                        new SqlParameter("TIME", OracleTypes.DATE),
//                        new SqlParameter("DELETE_FLAG", OracleTypes.NUMBER),
//                        new SqlOutParameter("POSR_ID", OracleTypes.NUMBER));
//        Map<String, Object> execute = call.execute(
//                new MapSqlParameterSource()
//                        .addValue("CONTEXT", post.getContent())
//                        .addValue("USER_ID", post.getUserId())
//                        .addValue("TIME", post.getTime())
//                        .addValue("DELETE_FLAG", post.getDeleteFlag())
//        );
//        return (int)execute.get("POST_ID");
        return 1 == jdbcTemplate.update("INSERT INTO POST(POST.CONTENT, POST.USER_ID, POST.TIME, POST.DELETE_FLAG) VALUES (?,?,?,?)", post.getContent(), post.getUserId(), post.getTime(), post.getDeleteFlag());
    }

    public boolean delete(Post post){
        post.setDeleteFlag(1);
        return 1 == jdbcTemplate.update("update POST set DELETE_FLAG = "+post.getDeleteFlag()+" where POST_ID = "+post.getPostId());
    }

    public List<JSONObject> searchPosts(int index, String keyWord){
        List<JSONObject> records = new ArrayList<>();
        //index 帖子的游标，包含帖子信息+用户+点赞数
        SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("SEARCH_POST")
                . declareParameters(
                        new SqlParameter("POSTINDEX", OracleTypes.INTEGER),
                        new SqlParameter("WORD", OracleTypes.VARCHAR),
                        new SqlOutParameter("POSTS", OracleTypes.CURSOR));
        Map<String, Object> execute = call.execute(
                new MapSqlParameterSource()
                        .addValue("POSTINDEX", index)
                        .addValue("WORD", keyWord)
        );
        ResultSet rs = (ResultSet) execute.get("POSTS");
        try {
            while (rs.next()){
                Map<String, Object> post = new HashMap<>();
                post.put("postId", rs.getInt("postId"));
                post.put("userId", rs.getString("userId"));
                post.put("time", rs.getDate("time"));
                post.put("content", rs.getString("content"));
                post.put("likeNum", rs.getInt("likeNum"));
                post.put("userName", rs.getString("userName"));
                records.add((JSONObject)JSONObject.toJSON(post));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return records;
    }

    public List<JSONObject> getPosts(int index){
        List<JSONObject> records = new ArrayList<>();
        //index 帖子的游标，包含帖子信息+用户+点赞数
        SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("PROCEDURE1")
                . declareParameters(
                        new SqlParameter("BEGINNUM", OracleTypes.INTEGER),
                        new SqlOutParameter("POSTS", OracleTypes.CURSOR));
        Map<String, Object> execute = call.execute(
                new MapSqlParameterSource()
                        .addValue("BEGINNUM", index)
        );
        ArrayList arrayList = (ArrayList)execute.get("POSTS");
        for (Object i:arrayList
             ) {
            Map<String, Object> post = new HashMap<>();
            Set<Map.Entry> set = ((LinkedCaseInsensitiveMap)i).entrySet();
            for (Map.Entry e:set
                 ) {
                if (e.getKey().toString().equals("LIKENUM") && e.getValue()==null){
                    post.put(e.getKey().toString(), 0);
                }else {
                    post.put(e.getKey().toString(), e.getValue());
                }
            }
            records.add((JSONObject)JSONObject.toJSON(post));
        }
        return records;
    }

}
