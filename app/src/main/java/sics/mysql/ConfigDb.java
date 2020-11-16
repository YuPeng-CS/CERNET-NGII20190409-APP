package sics.mysql;


import com.encrypty.Gen;
import com.encrypty.SecretKey;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import sics.exception.DbException;
import sics.tool.ObjStrTool;

public class ConfigDb {
    public static String selectConfigValue(int id) throws DbException {
        String sql = "select value from sys_config_value where id=?";
        try (Connection connection = MysqlConnect.getConn();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getString(1);
        } catch (SQLException e) {
            throw new DbException(0, "服务器连接失败 error code:4000");
        } catch (ClassNotFoundException e) {
            throw new DbException(1, "服务器连接失败 error code:4001");
        }
    }

    public static int insertConfigValue(int id) throws DbException {
        String sql = "insert into sys_config_value(name,value) values(?,?)";
        try (Connection connection = MysqlConnect.getConn();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            if (id == 1) {
                ps.setString(1, "AES");
                ps.setString(2, "aassddffgghhjjkk");
            } else {
                SecretKey sk = Gen.GenKey(128);
                ps.setString(1, "INDEX");
                ps.setString(2, ObjStrTool.compress(sk));
            }

            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(0, "服务器连接失败 error code:4002");
        } catch (ClassNotFoundException e) {
            throw new DbException(1, "服务器连接失败 error code:4003");
        }
    }
}
