package com.tongji.welog.dao;

import com.alibaba.fastjson.JSONObject;
import com.tongji.welog.model.Post;
import oracle.jdbc.OracleTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedCaseInsensitiveMap;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Repository
public class PostDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean insert(Post post){
        return 1 == jdbcTemplate.update("INSERT INTO POST(POST.CONTENT, POST.USER_ID, POST.TIME, POST.DELETE_FLAG) VALUES (?,?,?,?)", post.getContent(), post.getUserId(), post.getTime(), post.getDeleteFlag());
    }

    public boolean delete(Post post){
        post.setDeleteFlag(1);
        return 1 == jdbcTemplate.update("update POST set DELETE_FLAG = "+post.getDeleteFlag()+" where POST_ID = "+post.getPostId());
    }

    public List<JSONObject> searchPosts(int start, int index, String keyWord){
        //index 帖子的游标，包含帖子信息+用户+点赞数
        SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("PROCEDURE_SEARCH")
                .declareParameters(
                        new SqlParameter("BEGINNUM", OracleTypes.INTEGER),
                        new SqlParameter("RANGES", OracleTypes.INTEGER),
                        new SqlParameter("WORD", OracleTypes.VARCHAR),
                        new SqlOutParameter("POSTS", OracleTypes.CURSOR));
        Map<String, Object> execute = call.execute(
                new MapSqlParameterSource()
                        .addValue("BEGINNUM", start)
                        .addValue("RANGES", index)
                        .addValue("WORD", keyWord)
        );
        ArrayList arrayList = (ArrayList)execute.get("POSTS");
        return getPostsFromArray(arrayList);
    }

    public List<JSONObject> getPosts(int start, int index){
        //index 帖子的游标，包含帖子信息+用户+点赞数
        SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("PROCEDURE_POST")
                .declareParameters(
                        new SqlParameter("BEGINNUM", OracleTypes.INTEGER),
                        new SqlParameter("RANGES", OracleTypes.INTEGER),
                        new SqlOutParameter("POSTS", OracleTypes.CURSOR));
        Map<String, Object> execute = call.execute(
                new MapSqlParameterSource()
                        .addValue("BEGINNUM", start)
                        .addValue("RANGES", index)
        );
        ArrayList arrayList = (ArrayList)execute.get("POSTS");
        return getPostsFromArray(arrayList);
    }

    public List<JSONObject> getUserPosts(int userId, int start, int index){
        SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("PROCEDURE_POST_OF")
                .declareParameters(
                        new SqlParameter("USERID", OracleTypes.INTEGER),
                        new SqlParameter("BEGINNUM", OracleTypes.INTEGER),
                        new SqlParameter("RANGES", OracleTypes.INTEGER),
                        new SqlOutParameter("POSTS", OracleTypes.CURSOR));
        Map<String, Object> execute = call.execute(
                new MapSqlParameterSource()
                        .addValue("USERID", userId)
                        .addValue("BEGINNUM", start)
                        .addValue("RANGES", index)
        );
        ArrayList arrayList = (ArrayList)execute.get("POSTS");
        return getPostsFromArray(arrayList);
    }

    public List<JSONObject> getFollowPosts(int userId, int start, int index){
        //index 帖子的游标，包含帖子信息+用户+点赞数
        SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("PROCEDURE_POST_FOLLOW")
                .declareParameters(
                        new SqlParameter("USERID", OracleTypes.INTEGER),
                        new SqlParameter("BEGINNUM", OracleTypes.INTEGER),
                        new SqlParameter("RANGES", OracleTypes.INTEGER),
                        new SqlOutParameter("POSTS", OracleTypes.CURSOR));
        Map<String, Object> execute = call.execute(
                new MapSqlParameterSource()
                        .addValue("USERID", userId)
                        .addValue("BEGINNUM", start)
                        .addValue("RANGES", index)
        );
        ArrayList arrayList = (ArrayList)execute.get("POSTS");
        return getPostsFromArray(arrayList);
    }

    private List<JSONObject> getPostsFromArray(ArrayList arrayList){
        List<JSONObject> records = new ArrayList<>();
        for (Object i:arrayList
        ) {
            Map<String, Object> post = new HashMap<>();
//            Set<Map.Entry> set = ((LinkedCaseInsensitiveMap)i).entrySet();
//            for (Map.Entry e:set
//            ) {
//                if (e.getKey().toString().equals("LIKENUM") && e.getValue()==null){
//                    post.put(e.getKey().toString(), 0);
//                }else {
//                    post.put(e.getKey().toString(), e.getValue());
//                }
//            }
            LinkedCaseInsensitiveMap map = (LinkedCaseInsensitiveMap)i;
            post.put("message_id", map.get("POST_ID"));
            post.put("message_content", map.get("CONTENT"));
            SimpleDateFormat SFDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = SFDate.format((Date)map.get("TIME"));
            post.put("message_create_time", time);
            post.put("message_sender_user_id", map.get("USER_ID"));
            int likes = 0;
            if (map.get("LIKENUM") != null){
                likes = ((BigDecimal)map.get("LIKENUM")).intValue();
            }
            post.put("message_like_num", likes);
            int comment = 0;
            if (map.get("COM") != null){
                comment = ((BigDecimal)map.get("COM")).intValue();
            }
            post.put("message_comment_num", comment);
            records.add((JSONObject)JSONObject.toJSON(post));
        }
        return records;
    }


}
