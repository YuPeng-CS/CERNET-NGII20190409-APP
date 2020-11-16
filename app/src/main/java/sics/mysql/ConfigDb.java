package sics.mysql;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import sics.exception.DbException;

public class ConfigDb {
    public static String selectConfigValue(int id) throws DbException {
        String sql = "select value from sys_config_value where id=?";
        try (Connection connection=MysqlConnect.getConn();
             PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getString(1);
        }catch (SQLException e) {
            throw new DbException(0,"服务器连接失败");
        } catch (ClassNotFoundException e) {
            throw new DbException(1,"服务器连接失败");
        }
    }
}
