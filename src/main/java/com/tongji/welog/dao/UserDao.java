package com.tongji.welog.dao;

import org.springframework.stereotype.Repository;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

@Repository
public class UserDao extends BaseDao {

    public HashMap<String, Integer> login(Object userId, Object password) throws SQLException, ClassNotFoundException {
        Connection conn = getConn();
        String sql = "{call login(?,?,?,?)}";
        CallableStatement cs = conn.prepareCall(sql);

        cs.setObject(1, userId);
        cs.setObject(2, password);
        cs.registerOutParameter(3, Types.INTEGER);
        cs.registerOutParameter(4, Types.INTEGER);
        cs.execute();
        int result = (int)cs.getObject(3);
        int id = (int)cs.getObject(4);
        cs.close();
        conn.close();
        HashMap<String, Integer> temp = new HashMap<>();
        temp.put("result",result);
        temp.put("user_id",id);
        return temp;
    }


    public HashMap<String,Object> getUserPublicInfo(int userId) throws SQLException, ClassNotFoundException {
        Connection conn = getConn();
        String sql = "{call user_profile(?,?,?,?)}";
        CallableStatement cs = conn.prepareCall(sql);

        cs.setObject(1, userId);
        cs.registerOutParameter(2, Types.REF_CURSOR);
        cs.registerOutParameter(3, Types.INTEGER);//follower
        cs.registerOutParameter(4, Types.INTEGER);//following
        cs.execute();
        ResultSet rs = (ResultSet)cs.getObject(2);
        HashMap<String,Object> returns = new HashMap<>();
        if(rs.next()){
            returns.put("user_id",rs.getObject("user_id"));
            returns.put("nickname", rs.getObject("name"));
            returns.put("avatar_url",rs.getObject("portrait"));
        }
        returns.put("followers_num",cs.getObject(3));
        returns.put("follows_num",cs.getObject(4));
        cs.close();
        conn.close();
        return returns;
    }


    public String signUp(String name, String password) throws SQLException, ClassNotFoundException {
        Connection conn = getConn();
        String sql = "{call signUp(?,?,?)}";
        CallableStatement cs = conn.prepareCall(sql);

        cs.setObject(1, name);
        cs.setObject(2, password);
        cs.registerOutParameter(3, Types.VARCHAR);//chongfu?
        cs.execute();
        String result = (String)cs.getObject(3);
        cs.close();
        conn.close();
        return result;
    }
}
