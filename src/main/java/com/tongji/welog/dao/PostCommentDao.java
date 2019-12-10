package com.tongji.welog.dao;

import com.fasterxml.jackson.databind.ser.Serializers;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class PostCommentDao extends BaseDao {
    public ArrayList<HashMap<String,Object>> viewComment(int postId, int startFrom, int limitation)
            throws SQLException, ClassNotFoundException {
        Connection conn = getConn();
        String sql = "{?=call viewComment(?,?,?)}";
        CallableStatement cs = conn.prepareCall(sql);

        cs.registerOutParameter(1,oracle.jdbc.OracleTypes.CURSOR);
        cs.setObject(2, postId);
        cs.setObject(3, startFrom);
        cs.setObject(4, limitation);
        cs.execute();
        ResultSet rs = (ResultSet)cs.getObject(1);
        ArrayList<HashMap<String,Object>> temp = new ArrayList<>();
        while(rs.next()){
            HashMap<String,Object> r = new HashMap<>();
            r.put("comment_id",rs.getObject("comment_id"));
            r.put("comment_content", rs.getObject("content"));
            r.put("comment_sender_id", rs.getObject("actor_id"));
            r.put("comment_message_id",rs.getObject("post_id"));
            r.put("comment_create_time",rs.getObject("time"));
            r.put("user_id",rs.getObject("user_id"));
            r.put("nickname",rs.getObject("name"));
            r.put("avatar_url",rs.getObject("portrait"));
            temp.add(r);
        }
        cs.close();
        conn.close();
        return temp;
    }
    public void sendComment(int userId, int postId, String content) throws SQLException, ClassNotFoundException {
        Connection conn = getConn();
        String sql = "{call sendComment(?,?,?)}";
        CallableStatement cs = conn.prepareCall(sql);

        cs.setObject(1, userId);
        cs.setObject(2, postId);
        cs.setObject(3, content);
        cs.execute();
        cs.close();
        conn.close();
    }
}
