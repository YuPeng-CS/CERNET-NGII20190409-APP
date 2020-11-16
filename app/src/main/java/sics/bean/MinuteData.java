package sics.bean;

import com.Index.Index;
import com.encrypty.SecretKey;
import com.encrypty.splitedMatrix;

import sics.tool.AesTool;
import sics.tool.ObjStrTool;

public class MinuteData {

    private long minuteTimeStamp;
    private int wSteps;
    private int rSteps;
    private int distance;
    private int sleepSecond;
    private int sleepState;
    private int calories;
    private double skinTemperature;
    private int heartRate;
    private int bplp;
    private int bphp;
    private int steps;

    public long getMinuteTimeStamp() {
        return minuteTimeStamp;
    }

    public void setMinuteTimeStamp(long minuteTimeStamp) {
        this.minuteTimeStamp = minuteTimeStamp;
    }

    public int getwSteps() {
        return wSteps;
    }

    public void setwSteps(int wSteps) {
        this.wSteps = wSteps;
    }

    public int getrSteps() {
        return rSteps;
    }

    public void setrSteps(int rSteps) {
        this.rSteps = rSteps;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getSleepSecond() {
        return sleepSecond;
    }

    public void setSleepSecond(int sleepSecond) {
        this.sleepSecond = sleepSecond;
    }

    public int getSleepState() {
        return sleepState;
    }

    public void setSleepState(int sleepState) {
        this.sleepState = sleepState;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public double getSkinTemperature() {
        return skinTemperature;
    }

    public void setSkinTemperature(double skinTemperature) {
        this.skinTemperature = skinTemperature;
    }

    public int getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(int heartRate) {
        this.heartRate = heartRate;
    }

    public int getBplp() {
        return bplp;
    }

    public void setBplp(int bplp) {
        this.bplp = bplp;
    }

    public int getBphp() {
        return bphp;
    }

    public void setBphp(int bphp) {
        this.bphp = bphp;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public String getIndexHeartRate() {
        return indexHeartRate;
    }

    public void setIndexHeartRate(String indexHeartRate) {
        this.indexHeartRate = indexHeartRate;
    }

    public String getIndexSleepState() {
        return indexSleepState;
    }

    public void setIndexSleepState(String indexSleepState) {
        this.indexSleepState = indexSleepState;
    }

    public String getIndexSteps() {
        return indexSteps;
    }

    public void setIndexSteps(String indexSteps) {
        this.indexSteps = indexSteps;
    }

    public splitedMatrix getSpHeartRate() {
        return spHeartRate;
    }

    public void setSpHeartRate(splitedMatrix spHeartRate) {
        this.spHeartRate = spHeartRate;
    }

    public splitedMatrix getSpSleepState() {
        return spSleepState;
    }

    public void setSpSleepState(splitedMatrix spSleepState) {
        this.spSleepState = spSleepState;
    }

    public splitedMatrix getSpSteps() {
        return spSteps;
    }

    public void setSpSteps(splitedMatrix spSteps) {
        this.spSteps = spSteps;
    }

    public String getAesHeartRate() {
        return aesHeartRate;
    }

    public void setAesHeartRate(String aesHeartRate) {
        this.aesHeartRate = aesHeartRate;
    }

    public String getAesSleepState() {
        return aesSleepState;
    }

    public void setAesSleepState(String aesSleepState) {
        this.aesSleepState = aesSleepState;
    }

    public String getAesSteps() {
        return aesSteps;
    }

    public void setAesSteps(String aesSteps) {
        this.aesSteps = aesSteps;
    }

    //加密索引
    private String indexHeartRate;
    private String indexSleepState;
    private String indexSteps;

    //加密索引向量
    private splitedMatrix spHeartRate;
    private splitedMatrix spSleepState;
    private splitedMatrix spSteps;

    //aes加密数据
    private String aesHeartRate;
    private String aesSleepState;
    private String aesSteps;

    public void aesEncode(String aesKey){
        try {
            aesHeartRate=AesTool.encrypt(aesKey,heartRate+"");
        } catch (Exception e) {
            aesHeartRate="";
        }
        try {
            aesSleepState=AesTool.encrypt(aesKey,sleepState+"");
        } catch (Exception e) {
            aesSleepState="";
        }
        try {
            aesSteps=AesTool.encrypt(aesKey,steps+"");
        } catch (Exception e) {
            aesSteps="";
        }
    }

    public void aesDecode(String aesKey){
        try {
            heartRate=AesTool.decryptToInteger(aesKey,aesHeartRate);
        } catch (Exception e) {
            heartRate=0;
        }
        try {
            sleepState=AesTool.decryptToInteger(aesKey,aesSleepState);
        } catch (Exception e) {
            sleepState=0;
        }
        try {
            steps=AesTool.decryptToInteger(aesKey,aesSteps);
        } catch (Exception e) {
            steps=0;
        }
    }

    public void indexEncode(SecretKey sk){
        indexHeartRate= ObjStrTool.compress(Index.getSecureDataIndex(heartRate,sk));
        indexSleepState=ObjStrTool.compress(Index.getSecureDataIndex(sleepState,sk));
        indexSteps=ObjStrTool.compress(Index.getSecureDataIndex(steps,sk));
    }

    public void indexDecode(SecretKey sk){
        spHeartRate= (splitedMatrix) ObjStrTool.uncompress(indexHeartRate);
        spSleepState= (splitedMatrix) ObjStrTool.uncompress(indexSleepState);
        spSteps= (splitedMatrix) ObjStrTool.uncompress(indexSteps);
    }

    public MinuteData(com.hicling.clingsdk.model.MinuteData minuteData){
        this.minuteTimeStamp=minuteData.minuteTimeStamp*1000;
        this.wSteps=minuteData.wSteps;
        this.rSteps=minuteData.rSteps;
        this.steps=this.wSteps+this.rSteps;
        this.distance=minuteData.distance;
        this.sleepSecond=minuteData.sleepSecond;
        this.sleepState=minuteData.sleepState;
        this.calories=minuteData.calories;
        this.skinTemperature=minuteData.skinTemperature;
        this.heartRate=minuteData.heartRate;
        this.bplp=minuteData.bplp;
        this.bphp=minuteData.bphp;
    }
}
