package sics.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import sics.bean.MinuteSleepData;
import sics.exception.DbException;

public class MinuteSleepDb {
    
    public static int[] insertMinuteSleepList(List<MinuteSleepData> list) throws DbException {
        if(list==null)return new int[]{0};
        String sql="insert ignore into minute_sleep_data(user_id,index_vector,sleep_second,sleep_state,update_time)" +
                "values (?,?,?,?,?)";
        try (
                Connection connection=MysqlConnect.getBatchConn();
                PreparedStatement ps = connection.prepareStatement(sql)
        ){

            connection.setAutoCommit(false);
            for(MinuteSleepData m:list){
                ps.setLong(1,m.getUserId());
                ps.setString(2,m.getIndexVector());
                ps.setString(3,m.getSleepSecond());
                ps.setString(4,m.getSleepState());
                ps.setTimestamp(5,m.getUpdateTime());
                ps.addBatch();
            }
            int[] result=ps.executeBatch();
            connection.commit();
            return result;
        }catch (SQLException e) {
            throw new DbException(0,"服务器连接失败");
        } catch (ClassNotFoundException e) {
            throw new DbException(1,"服务器连接失败");
        }
    }

    public static List<MinuteSleepData> selectSleep(Long userId,Timestamp beginTime,Timestamp endTime) throws DbException {
        String sql = "select * from minute_sleep_data where user_id=? and update_time >= ? and update_time <= ? order by update_time";
        try (
                Connection connection=MysqlConnect.getConn();
                PreparedStatement ps = connection.prepareStatement(sql)
        ){
            ps.setLong(1,userId);
            ps.setTimestamp(2,beginTime);
            ps.setTimestamp(3,endTime);
            ResultSet rs=ps.executeQuery();
            List<MinuteSleepData> list=new ArrayList<>();
            while (rs.next()){
                MinuteSleepData m=new MinuteSleepData();
                m.setId(rs.getLong(1));
                m.setUserId(rs.getLong(2));
                m.setIndexVector(rs.getString(3));
                m.setSleepSecond(rs.getString(4));
                m.setSleepState(rs.getString(5));
                m.setUpdateTime(rs.getTimestamp(6));
                list.add(m);
            }
            return list;
        }catch (SQLException e) {
            throw new DbException(0,"服务器连接失败");
        } catch (ClassNotFoundException e) {
            throw new DbException(1,"服务器连接失败");
        }
    }
}
