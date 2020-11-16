package sics.bean;

import com.Index.Index;
import com.encrypty.SecretKey;
import com.hicling.clingsdk.model.DayTotalDataModel;

import java.util.Arrays;

import sics.tool.AesTool;
import sics.tool.ObjStrTool;

public class DayData {
    /*
        kind:  0:平均心率 1:总步数 2:平均体表温度 3:总卡路里 4:血压低压 5:血压高压
     */
    public final static int HEART_RATE=0;
    public final static int STEPS=1;
    public final static int TEMP=2;
    public final static int CALORIES=3;
    public final static int BP_LP=4;
    public final static int BP_HP=5;

    private long dayBeginTime = 0L;
    private int[] data=new int[6];
    private String[] aesData=new String[6];
    private String[] kind=new String[6];
    private String[] indexVector=new String[6];

    public DayData(DayTotalDataModel dtd) {
        dayBeginTime=dtd.mDayBeginTime*1000;
        data[HEART_RATE]=dtd.mHeartRate;
        data[STEPS]=dtd.mStepTotal;
        data[TEMP]= (int) dtd.mSkinTemperature;
        data[CALORIES]= (int) dtd.mCaloriesTotal;
        data[BP_LP]=dtd.mnBPlp;
        data[BP_HP]=dtd.mnBPhp;
    }

    public void encode(String aesKey, SecretKey secretKey){
        for(int i=0;i<6;i++) {
            try {
                kind[i]= AesTool.encrypt(aesKey,i+"");
                aesData[i]=AesTool.encrypt(aesKey,data[i]+"");
            } catch (Exception e) {
                kind[i]="";
            }
            indexVector[i]= ObjStrTool.compress(Index.getSecureDataIndex(data[i],secretKey));
        }
    }

    public long getDayBeginTime() {
        return dayBeginTime;
    }

    public void setDayBeginTime(long dayBeginTime) {
        this.dayBeginTime = dayBeginTime;
    }

    public int[] getData() {
        return data;
    }

    public void setData(int[] data) {
        this.data = data;
    }

    public String[] getAesData() {
        return aesData;
    }

    public void setAesData(String[] aesData) {
        this.aesData = aesData;
    }

    public String[] getKind() {
        return kind;
    }

    public void setKind(String[] kind) {
        this.kind = kind;
    }

    public String[] getIndexVector() {
        return indexVector;
    }

    public void setIndexVector(String[] indexVector) {
        this.indexVector = indexVector;
    }

    @Override
    public String toString() {
        return "DayData{" +
                "dayBeginTime=" + dayBeginTime +
                ", data=" + Arrays.toString(data) +
                ", aesData=" + Arrays.toString(aesData) +
                ", kind=" + Arrays.toString(kind) +
                ", indexVector=" + Arrays.toString(indexVector) +
                '}';
    }
}
