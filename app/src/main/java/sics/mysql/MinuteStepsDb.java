package sics.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import sics.bean.MinuteSteps;
import sics.exception.DbException;

public class MinuteStepsDb {

    public static int[] insertMinuteStepsList(List<MinuteSteps> list) throws DbException {
        if(list==null)return new int[]{0};
        String sql = "insert ignore into minute_steps(user_id,idex_vector,minute_steps,update_time)" +
                "values (?,?,?,?)";
        try (
                Connection connection = MysqlConnect.getBatchConn();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            connection.setAutoCommit(false);
            for (MinuteSteps m : list) {
                ps.setLong(1, m.getUserId());
                ps.setString(2, m.getIndexVector());
                ps.setString(3, m.getMinuteSteps());
                ps.setTimestamp(4, m.getUpdateTime());
                ps.addBatch();
            }
            int[] result = ps.executeBatch();
            connection.commit();
            return result;
        } catch (SQLException e) {
            throw new DbException(0, "服务器连接失败 error code:4016");
        } catch (ClassNotFoundException e) {
            throw new DbException(1, "服务器连接失败 error code:4017");
        }
    }


    public static List<MinuteSteps> selectSteps(Long userId, Timestamp beginTime, Timestamp endTime) throws DbException {
        String sql = "select * from minute_steps where user_id=? and update_time >= ? and update_time <= ? order by update_time";
        try (
                Connection connection = MysqlConnect.getConn();
                PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ps.setLong(1, userId);
            ps.setTimestamp(2, beginTime);
            ps.setTimestamp(3, endTime);
            ResultSet rs = ps.executeQuery();
            List<MinuteSteps> list = new ArrayList<>();
            while (rs.next()) {
                MinuteSteps m = new MinuteSteps();
                m.setId(rs.getLong(1));
                m.setUserId(rs.getLong(2));
                m.setIndexVector(rs.getString(3));
                m.setMinuteSteps(rs.getString(4));
                m.setUpdateTime(rs.getTimestamp(5));
                list.add(m);
            }
            return list;
        } catch (SQLException e) {
            throw new DbException(0, "服务器连接失败18");
        } catch (ClassNotFoundException e) {
            throw new DbException(1, "服务器连接失败19");
        }
    }
}
