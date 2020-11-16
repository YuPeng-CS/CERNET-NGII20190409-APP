package sics.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import sics.bean.DayData;
import sics.bean.DaySingleData;
import sics.exception.DbException;

public class DayDataDb {
    /*
        kind:  0:平均心率 1:总步数 2:平均体表温度 3:总卡路里 4:血压低压 5:血压高压
     */
    public static int[] insertDayData(DayData dayData, long userId) throws DbException {
        String sql="insert ignore into day_data(user_id,index_vector,kind,data,update_time)" +
                "values (?,?,?,?,?)";
        try (
                Connection connection=MysqlConnect.getBatchConn();
                PreparedStatement ps = connection.prepareStatement(sql)
        ){

            connection.setAutoCommit(false);
            for(int i=0;i<6;i++){
                ps.setLong(1,userId);
                ps.setString(2,dayData.getIndexVector()[i]);
                ps.setString(3,dayData.getKind()[i]);
                ps.setString(4,dayData.getAesData()[i]);
                ps.setTimestamp(5,new Timestamp(dayData.getDayBeginTime()));
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

    public static List<DaySingleData> selectDayDataList(Long userId, String kind, Timestamp beginTime, Timestamp endTime) throws DbException {
        String sql = "select * from day_data where id in (select max(id) from day_data where kind=? group by user_id,update_time) and user_id=? and kind=? and update_time >= ? and update_time <= ? order by update_time";
        try (
                Connection connection=MysqlConnect.getConn();
                PreparedStatement ps = connection.prepareStatement(sql)
        ){
            ps.setString(1,kind);
            ps.setLong(2,userId);
            ps.setString(3,kind);
            ps.setTimestamp(4,beginTime);
            ps.setTimestamp(5,endTime);
            ResultSet rs=ps.executeQuery();
            List<DaySingleData> list=new ArrayList<>();
            while (rs.next()){
                DaySingleData m=new DaySingleData();
                m.setId(rs.getLong(1));
                m.setUserId(rs.getLong(2));
                m.setIndexVector(rs.getString(3));
                m.setKind(rs.getString(4));
                m.setData(rs.getString(5));
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
