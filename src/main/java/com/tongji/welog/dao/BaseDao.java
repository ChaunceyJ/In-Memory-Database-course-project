package com.tongji.welog.dao;

import java.sql.*;

public class BaseDao{
    static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
    static final String DB_URL = "jdbc:oracle:thin:@100.24.61.19:1521:orcl";
    static final String USER = "jiang";
    static final String PASS = "1234";

    Connection getConn() throws ClassNotFoundException, SQLException {
        Connection conn = null;
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL, USER, PASS);
        System.out.println("Connected to the database.");
        return conn;
    }


    public static void main(String[] args) {

        try {
            Class.forName(JDBC_DRIVER);
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement sql = conn.createStatement();
            long start1 =  System.currentTimeMillis();
            sql.execute("INSERT INTO POST(POST_ID, POST.CONTENT, POST.USER_ID, POST.TIME, POST.DELETE_FLAG) VALUES (999,'dfqrqr',1,0)");
            long end1 =  System.currentTimeMillis();
            double used1 = (end1-start1)/1000;
            PreparedStatement psql = conn.prepareStatement("INSERT INTO POST(POST_ID, POST.CONTENT, POST.USER_ID, POST.TIME, POST.DELETE_FLAG) VALUES (?,?,?,?)");
            psql.setInt(1, 1000);
            psql.setInt(2, 1);
            psql.setString(3, "wqreqwwad");
            psql.setInt(4,0);
            long start2 =  System.currentTimeMillis();
            psql.execute();
            long end2 =  System.currentTimeMillis();
            double used2 = (end2-start2)/1000;
            System.out.println(used1);
            System.out.println(used2);
            psql.close();
            sql.close();
            conn.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
