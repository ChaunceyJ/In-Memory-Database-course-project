package com.tongji.welog.dao;

import com.tongji.welog.model.Likes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Map;

@Repository
public class LikesDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean insert(Likes likes){
        return 1 == jdbcTemplate.update("INSERT INTO LIKES(LIKES.USER_ID, LIKES.POST_ID) VALUES (?,?)", likes.getUserId(), likes.getPostId());
    }

    public boolean delete(Likes likes){
        return 1 == jdbcTemplate.update("DELETE FROM LIKES WHERE LIKES.POST_ID = (?) AND LIKES.USER_ID = (?)", likes.getPostId(), likes.getUserId());
    }

    public boolean exists(Likes likes){
        jdbcTemplate.execute("select count(*) from LIKES where LIKES.USER_ID = "+likes.getUserId()+" and LIKES.POST_ID = "+likes.getPostId()+" and rownum<=1");
        Map result =  jdbcTemplate.queryForMap("select count(*) NUM from LIKES where LIKES.USER_ID = (?) and LIKES.POST_ID = (?) and rownum<=1", likes.getUserId(), likes.getPostId());
        if (((BigDecimal)result.get("NUM")).intValue() == 1){
            return true;
        }
        else {
            return false;
        }
    }

}
