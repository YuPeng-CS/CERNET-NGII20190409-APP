package sics.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import sics.bean.MinuteHeartRate;
import sics.exception.DbException;

public class MinuteHeartRateDb {

    public static int[] insertMinuteHeartRateList(List<MinuteHeartRate> list) throws DbException {
        if(list==null)return new int[]{0};
        String sql="insert ignore into minute_heart_rate(user_id,index_vector,minute_heart_rate,update_time)" +
                "values (?,?,?,?)";
        try (
                Connection connection=MysqlConnect.getBatchConn();
                PreparedStatement ps = connection.prepareStatement(sql)
        ){
            connection.setAutoCommit(false);
            for(MinuteHeartRate mhr:list){
                ps.setLong(1,mhr.getUserId());
                ps.setString(2,mhr.getIndexVector());
                ps.setString(3,mhr.getMinuteHeartRate());
                ps.setTimestamp(4,mhr.getUpdateTime());
                ps.addBatch();
            }
            int[] result=ps.executeBatch();
            connection.commit();
            return result;
        } catch (SQLException e) {
            throw new DbException(0,"服务器连接失败 error code:4008");
        } catch (ClassNotFoundException e) {
            throw new DbException(1,"服务器连接失败 error code:4009");
        }
    }


    public static List<MinuteHeartRate> selectHeartRate(Long userId,Timestamp beginTime,Timestamp endTime) throws DbException {
        String sql = "select * from minute_heart_rate where user_id=? and update_time >= ? and update_time <= ? order by update_time";
        try (
                Connection connection=MysqlConnect.getConn();
                PreparedStatement ps = connection.prepareStatement(sql)
        ){
            ps.setLong(1,userId);
            ps.setTimestamp(2,beginTime);
            ps.setTimestamp(3,endTime);
            ResultSet rs = ps.executeQuery();
            List<MinuteHeartRate> list=new ArrayList<>();
            while (rs.next()) {
                MinuteHeartRate m = new MinuteHeartRate();
                m.setId(rs.getLong(1));
                m.setUserId(rs.getLong(2));
                m.setIndexVector(rs.getString(3));
                m.setMinuteHeartRate(rs.getString(4));
                m.setUpdateTime(rs.getTimestamp(5));
                list.add(m);
            }
            return list;
        }catch (SQLException e) {
            throw new DbException(0,"服务器连接失败 error code:4010");
        } catch (ClassNotFoundException e) {
            throw new DbException(1,"服务器连接失败 error code:4011");
        }
    }
}
