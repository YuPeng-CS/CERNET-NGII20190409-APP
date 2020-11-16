package sics.tool;

import com.Index.Index;
import com.encrypty.SecretKey;
import com.encrypty.splitedMatrix;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import sics.bean.MinuteData;
import sics.bean.MinuteHeartRate;
import sics.bean.MinuteSleepData;
import sics.bean.MinuteSteps;

public class MinuteData2List {

    private SecretKey secretKey;
    private List<MinuteData> list;
    private Long userId;
    private String aeskey;

    public MinuteData2List(List<MinuteData> list, Long userId,SecretKey secretKey,String aesKey){
        this.list=list;
        this.userId=userId;
        this.secretKey=secretKey;
        this.aeskey=aesKey;
    }


    public List<MinuteSteps> getMinuteStepList() {
        List<MinuteSteps> result=new ArrayList<>();
        for(MinuteData minuteData:list){
            MinuteSteps item=new MinuteSteps();
            item.setUserId(userId);
            item.setUpdateTime(new Timestamp(minuteData.getMinuteTimeStamp()));
            int steps=minuteData.getSteps();
            String strStep= minuteData.getrSteps()+minuteData.getwSteps()+"";
            try {
                strStep= AesTool.encrypt(aeskey,strStep);
            } catch (Exception e) {
                e.printStackTrace();
            }
            item.setMinuteSteps(strStep);
            if(secretKey!=null){
                splitedMatrix sm= Index.getSecureDataIndex(steps,secretKey);
                item.setIndexVector(ObjStrTool.compress(sm));
            }
            result.add(item);
        }
        return result;
    }

    public List<MinuteSleepData> getMinuteSleepDataList() {
        List<MinuteSleepData> result=new ArrayList<>();
        for(MinuteData minuteData:list){
            MinuteSleepData item=new MinuteSleepData();
            item.setUserId(userId);
            item.setUpdateTime(new Timestamp(minuteData.getMinuteTimeStamp()));
            String sleepSecond= minuteData.getSleepSecond()+"";
            String sleepState=minuteData.getSleepState()+"";
            try {
                sleepState= AesTool.encrypt(aeskey,sleepState);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                sleepSecond=AesTool.encrypt(aeskey,sleepSecond);
            } catch (Exception e) {
                e.printStackTrace();
            }
            item.setSleepSecond(sleepSecond);
            item.setSleepState(sleepState);
            if(secretKey!=null){
                splitedMatrix sm= Index.getSecureDataIndex(minuteData.getSleepSecond(),secretKey);
                item.setIndexVector(ObjStrTool.compress(sm));
            }
            result.add(item);
        }
        return result;
    }

    public List<MinuteHeartRate> getMinuteHeartRateList() {
        List<MinuteHeartRate> result=new ArrayList<>();
        for(MinuteData minuteData:list){
            MinuteHeartRate item=new MinuteHeartRate();
            item.setUserId(userId);
            item.setUpdateTime(new Timestamp(minuteData.getMinuteTimeStamp()));
            String heartRate= minuteData.getHeartRate()+"";
            try {
                heartRate = AesTool.encrypt(aeskey,heartRate);
            } catch (Exception e) {
                e.printStackTrace();
            }
            item.setMinuteHeartRate(heartRate);
            if(secretKey!=null){
                splitedMatrix sm= Index.getSecureDataIndex(minuteData.getHeartRate(),secretKey);
                item.setIndexVector(ObjStrTool.compress(sm));
            }
            result.add(item);
        }
        return result;
    }
}
