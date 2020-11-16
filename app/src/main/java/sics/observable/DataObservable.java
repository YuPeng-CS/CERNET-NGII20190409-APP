package sics.observable;

import android.util.Log;

import com.encrypty.SecretKey;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import sics.bean.CommonData;
import sics.bean.DayData;
import sics.bean.DaySingleData;
import sics.bean.MinuteData;
import sics.bean.MinuteHeartRate;
import sics.mysql.ConfigDb;
import sics.mysql.DayDataDb;
import sics.mysql.MinuteHeartRateDb;
import sics.mysql.MinuteSleepDb;
import sics.mysql.MinuteStepsDb;
import sics.tool.AesTool;
import sics.tool.MinuteData2List;
import sics.tool.MinuteDataMini;
import sics.tool.ObjStrTool;

public class DataObservable {

    private final static int AES_KEY_ID=1;
    private final static int SECRET_KEY_ID=2;

    public static Observable<Integer> insertDataListInDb(long userId, List<MinuteData> minuteDataList, DayData dayData){
        return Observable.create((ObservableOnSubscribe<Integer>)emitter -> {
            String aesKey = ConfigDb.selectConfigValue(AES_KEY_ID);
            SecretKey secretKey = (SecretKey) ObjStrTool.uncompressSk(ConfigDb.selectConfigValue(SECRET_KEY_ID));
            if (secretKey == null || aesKey==null){
                emitter.onNext(0);
                emitter.onComplete();
            }
            int result=0;
            if(minuteDataList!=null && minuteDataList.size()!=0){
                MinuteData2List minuteData2List = new MinuteData2List(minuteDataList,userId,secretKey,aesKey);
                int[] rs1=MinuteHeartRateDb.insertMinuteHeartRateList(minuteData2List.getMinuteHeartRateList());
                int[] rs2=MinuteSleepDb.insertMinuteSleepList(minuteData2List.getMinuteSleepDataList());
                int[] rs3=MinuteStepsDb.insertMinuteStepsList(minuteData2List.getMinuteStepList());
                for(int i:rs1)result+=i;
                for(int i:rs2)result+=i;
                for(int i:rs3)result+=i;
            }
            if(dayData!=null){
                dayData.encode(aesKey,secretKey);
                int[] rs4= DayDataDb.insertDayData(dayData,userId);
                for(int i:rs4)result+=i;
            }
            emitter.onNext(result);
            emitter.onComplete();
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<List<CommonData>> selectMinuteHeartListFromDb(long userId,Timestamp beginTime, Timestamp endTime,double begin,double end){
        return Observable.create((ObservableOnSubscribe<List<CommonData>>) emitter -> {
            //获取密钥
            List<CommonData> result=new ArrayList<>();
            String aesKey = ConfigDb.selectConfigValue(AES_KEY_ID);
            SecretKey secretKey = (SecretKey) ObjStrTool.uncompressSk(ConfigDb.selectConfigValue(SECRET_KEY_ID));
            if (secretKey == null || aesKey==null){
                emitter.onNext(result);
                emitter.onComplete();
            }
            List<MinuteHeartRate> list=MinuteHeartRateDb.selectHeartRate(userId, beginTime, endTime);
            MinuteDataMini.check(list, begin, end, secretKey);
            MinuteDataMini.decode(list, aesKey);
            result.addAll(MinuteDataMini.getSumList(list, 60 * 60 * 1000, true));
            emitter.onNext(result);
            emitter.onComplete();
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<List<CommonData>> selectDaySingleListFromDb(long userId,int iKind, Timestamp beginTime, Timestamp endTime,double begin,double end){
        return Observable.create((ObservableOnSubscribe<List<CommonData>>) emitter -> {
            List<CommonData> result=new ArrayList<>();
            String aesKey = ConfigDb.selectConfigValue(AES_KEY_ID);
            SecretKey secretKey = (SecretKey) ObjStrTool.uncompressSk(ConfigDb.selectConfigValue(SECRET_KEY_ID));
            if (secretKey == null || aesKey==null){
                emitter.onNext(result);
                emitter.onComplete();
            }
            String kind= AesTool.encrypt(Objects.requireNonNull(aesKey),iKind+"");
            List<DaySingleData> list=DayDataDb.selectDayDataList(userId,kind,beginTime,endTime);
            MinuteDataMini.check(list, begin, end, secretKey);
            MinuteDataMini.decode(list, aesKey);
            result.addAll(MinuteDataMini.getSumList(list, 24 * 60 * 60 * 1000, true));
            emitter.onNext(result);
            emitter.onComplete();
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
