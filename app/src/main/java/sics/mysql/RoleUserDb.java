package sics.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import sics.exception.DbException;

public class RoleUserDb {

    public static int insertRoleUser(long userId,long roleId) throws DbException {
        String sql = "insert into role_user (user_id,role_id) values (?,?)";
        try(
                Connection connection=MysqlConnect.getConn();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setLong(1,userId);
            ps.setLong(2,roleId);
            return ps.executeUpdate();
        }catch (SQLException e) {
            throw new DbException(0,"服务器连接失败 error code:4018");
        } catch (ClassNotFoundException e) {
            throw new DbException(1,"服务器连接失败 error code:4019");
        }
    }
}
