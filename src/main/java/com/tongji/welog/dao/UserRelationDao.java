package com.tongji.welog.dao;

import oracle.jdbc.OracleTypes;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class UserRelationDao extends BaseDao{
    public void follow(int userId, int objectId) throws SQLException, ClassNotFoundException {
        Connection conn = getConn();
        String sql = "{call follow(?,?)}";
        CallableStatement cs = conn.prepareCall(sql);

        cs.setObject(1, userId);
        cs.setObject(2, objectId);
        cs.execute();
        cs.close();
        conn.close();
    }

    public void unfollow(int userId, int objectId) throws SQLException, ClassNotFoundException {
        Connection conn = getConn();
        String sql = "{call unfollow(?,?)}";
        CallableStatement cs = conn.prepareCall(sql);

        cs.setObject(1, userId);
        cs.setObject(2, objectId);
        cs.execute();
        cs.close();
        conn.close();
    }
    public ArrayList<HashMap<String,Object>> relationList(
            int userId, int startFrom, int limitation, String type) throws SQLException, ClassNotFoundException {
        Connection conn = getConn();
        String sql = "{?=call relationlist(?,?,?,?)}";
        CallableStatement cs = conn.prepareCall(sql);

        //
        cs.registerOutParameter(1,oracle.jdbc.OracleTypes.CURSOR);
        cs.setObject(2, userId);
        cs.setObject(3, startFrom);
        cs.setObject(4, limitation);
        cs.setObject(5, type);
        cs.execute();

        ResultSet rs = (ResultSet)cs.getObject(1);
        ArrayList<HashMap<String,Object>> temp = new ArrayList<>();
        while(rs.next()){
            HashMap<String,Object> r = new HashMap<>();
            r.put("user_id", rs.getObject("user_id"));
            r.put("nickName", rs.getObject("name"));
            r.put("avatarUrl", rs.getObject("portrait"));
            temp.add(r);
        }
        cs.close();
        conn.close();
        return temp;
    }

    public Boolean ifFollow(int actor_id, int object_id) throws SQLException, ClassNotFoundException {
        Connection conn = getConn();
        String sql = "{call if_follow(?,?,?)}";
        CallableStatement cs = conn.prepareCall(sql);

        cs.registerOutParameter(3,Types.INTEGER);
        cs.setObject(1, actor_id);
        cs.setObject(2, object_id);
        cs.execute();

        return cs.getObject(3).equals(1);
    }
}
