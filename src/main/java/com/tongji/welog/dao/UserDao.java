package com.tongji.welog.dao;

import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

@Repository
public class UserDao extends BaseDao {

    public int login(int userId, String password) throws SQLException {
        Connection conn = getConn();
        String sql = "{call login(?,?,?)}";
        CallableStatement cs = conn.prepareCall(sql);

        cs.setObject(1, userId);
        cs.setObject(2, password);
        cs.registerOutParameter(3, Types.INTEGER);
        cs.execute();
        int result = (int)cs.getObject(3);
        cs.close();
        conn.close();
        return result ;
    }


    public void profilePage(int userId) {

    }


}
