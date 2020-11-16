package sics.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import sics.bean.UserInfo;
import sics.exception.DbException;

public class UserInfoDb {

    public static int insertUserInfo(UserInfo userInfo) throws DbException {
        String sql = "insert into user_info (username,nickname,email,password,gender,age,create_time) values" +
                "(?,?,?,?,?,?,?)";
        try(
                Connection connection=MysqlConnect.getConn();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setString(1,userInfo.getUsername());
            ps.setString(2,userInfo.getNickname());
            ps.setString(3,userInfo.getEmail());
            ps.setString(4,userInfo.getPassword());
            ps.setInt(5,userInfo.getGender());
            ps.setInt(6,userInfo.getAge());
            ps.setTimestamp(7,new Timestamp(System.currentTimeMillis()));
            return ps.executeUpdate();
        }catch (SQLException e) {
            throw new DbException(0,"服务器连接失败");
        } catch (ClassNotFoundException e) {
            throw new DbException(1,"服务器连接失败");
        }
    }

    public static UserInfo selectUserInfo(UserInfo user) throws DbException {
        String sql = "select * from user_info where email=? limit 1";
        try (
                Connection connection=MysqlConnect.getConn();
                PreparedStatement ps = connection.prepareStatement(sql)
        ){
            ps.setString(1,user.getEmail());
            ResultSet rs=ps.executeQuery();
            UserInfo userInfo=new UserInfo();
            while (rs.next()){
                userInfo.setUid(rs.getLong(1));
                userInfo.setUsername(rs.getString(2));
                userInfo.setNickname(rs.getString(3));
                userInfo.setEmail(rs.getString(4));
                userInfo.setPassword(rs.getString(5));
                userInfo.setGender(rs.getInt(6));
                userInfo.setAge(rs.getInt(7));
                userInfo.setCreateTime(rs.getTimestamp(8));
            }
            return userInfo;
        }catch (SQLException e) {
            throw new DbException(0,"服务器连接失败");
        } catch (ClassNotFoundException e) {
            throw new DbException(1,"服务器连接失败");
        }
    }

    public static int updateUserInfo(UserInfo userInfo) throws DbException {
        String sql = "update user_info set nickname=?,gender=?,age=? where uid =?";
        try (
                Connection connection=MysqlConnect.getConn();
                PreparedStatement ps = connection.prepareStatement(sql)
        ){
            ps.setString(1,userInfo.getNickname());
            ps.setInt(2,userInfo.getGender());
            ps.setInt(3,userInfo.getAge());
            ps.setLong(4,userInfo.getUid());
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(0,"服务器连接失败");
        } catch (ClassNotFoundException e) {
            throw new DbException(1,"服务器连接失败");
        }
    }
}
