package dao;

import java.sql.*;

public class DBConnection {
    private Connection conn = null;

    public DBConnection() {
        getDBConnection();
    }

    private void getDBConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("驱动程序已加载...");
        } catch(Exception e) {
            System.out.println("驱动程序加载失败..." + e);
        }

        try {
            String url = "jdbc:mysql://localhost:3306/lhy2019011378?user=root&password=pea123&zeroDateTimeBehavior=convertToNull";
            conn = DriverManager.getConnection(url);
            System.out.println("数据库连接已创建...");
        } catch(Exception e) {
            System.out.println("数据库连接创建失败..." + e);
        }
    }

    public Connection getConn() {
        return conn;
    }

    public void checkNotClosed() {
        try {
            if(conn.isClosed()) {
                try {
                    String url = "jdbc:mysql://localhost:3306/lhy2019011378?user=root&password=pea123&zeroDateTimeBehavior=convertToNull";
                    conn = DriverManager.getConnection(url);
                    System.out.println("数据库连接已创建...");
                } catch(Exception e) {
                    System.out.println("数据库连接创建失败..." + e);
                }
            }
        } catch(Exception e) {
            System.out.println("数据库连接关闭状态查询失败..." + e);
        }
    }

    public void close() {
        try {
            conn.close();
        } catch(Exception e) {
            System.out.println("数据库连接关闭失败..." + e);
        }
    }
}
