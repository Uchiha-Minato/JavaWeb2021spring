package util;

import dao.DBConnection;
import domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserFactory {
    private final DBConnection dbConnection;

    public UserFactory() {
        dbConnection = new DBConnection();
    }

    public void checkNotClosed() {
        dbConnection.checkNotClosed();
    }

    public Connection getConnection() {
        return dbConnection.getConn();
    }

    public ResultSet executeQuery(String userName) {
        ResultSet rs = null;
        checkNotClosed();
        try {
            PreparedStatement pstmt = getConnection().prepareStatement("SELECT * FROM user WHERE userName = ?");
            pstmt.setString(1, userName);
            rs = pstmt.executeQuery();
        } catch (Exception e) {
            System.out.println("数据查询失败..." + e);
        }
        return rs;
    }

    public User getUser(String userName) {
        User user = new User();
        try {
            ResultSet rs = executeQuery(userName);
            if(rs.next()) {
                user.setUserName(rs.getString("userName"));
                user.setPassword(rs.getString("password"));
                user.setTel(rs.getString("tel"));
            }
        } catch(Exception e) {
            System.out.println("数据查询失败..." + e);
        }
        return user;
    }

    public boolean existed(String userName) {
        try {
            ResultSet rs = executeQuery(userName);
            if(rs.next())
                return true;
        } catch(Exception e) {
            System.out.println("数据查询失败..." + e);
        }
        return false;
    }

    public String getPassword(String userName) {
        String password = null;
        try {
            if(existed(userName)) {
                ResultSet rs = executeQuery(userName);
                if(rs.next()) {
                    password = rs.getString("password");
                }
            }
        } catch(Exception e) {
            System.out.println("数据查询失败..." + e);
        }
        return password;
    }

    public boolean matched(String userName, String password) {
        try {
            if(password.equals(getPassword(userName)))
                return true;
        } catch(Exception e) {
            System.out.println("数据查询失败..." + e);
        }
        return false;
    }

    public boolean register(User user) {
        checkNotClosed();
        try {
            PreparedStatement pstmt = getConnection().prepareStatement("INSERT INTO user VALUES(?,?,?)");
            pstmt.setString(1, user.getUserName());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getTel());
            int row = pstmt.executeUpdate();
            if(row > 0)
                return true;
        } catch(Exception e) {
            System.out.println("数据更新失败..." + e);
        }
        return false;
    }

    public boolean deleteUser(String userName) {
        checkNotClosed();
        try {
            PreparedStatement pstmt = getConnection().prepareStatement("DELETE FROM user WHERE userName = ?");
            pstmt.setString(1, userName);
            int row = pstmt.executeUpdate();
            if(row > 0)
                return true;
        } catch(Exception e) {
            System.out.println("数据更新失败..." + e);
        }
        return false;
    }

    public boolean changePsw(String userName, String password) {
        checkNotClosed();
        try {
            PreparedStatement pstmt = getConnection().prepareStatement("UPDATE user SET password = ? WHERE userName = ?");
            pstmt.setString(1, password);
            pstmt.setString(2, userName);
            int row = pstmt.executeUpdate();
            if(row > 0)
                return true;
        } catch(Exception e) {
            System.out.println("数据更新失败..." + e);
        }
        return false;
    }
}
