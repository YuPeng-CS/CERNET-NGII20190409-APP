package sics.bean;

import java.sql.Timestamp;

public class MinuteSleepData extends AbstractTimeData {
    private Long id;
    private Long userId;
    private String indexVector;
    private String sleepSecond;
    private String sleepState;
    private Timestamp updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getIndexVector() {
        return indexVector;
    }

    public void setIndexVector(String indexVector) {
        this.indexVector = indexVector;
    }

    public String getSleepState() {
        return sleepState;
    }

    public void setSleepState(String sleepState) {
        this.sleepState = sleepState;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public String getSleepSecond() {
        return sleepSecond;
    }

    public void setSleepSecond(String sleepSecond) {
        this.sleepSecond = sleepSecond;
    }

    @Override
    public String toString() {
        return "MinuteSleepData{" +
                "id=" + id +
                ", userId=" + userId +
                ", indexVector='" + indexVector + '\'' +
                ", sleepSecond='" + sleepSecond + '\'' +
                ", sleepState='" + sleepState + '\'' +
                ", updateTime=" + updateTime +
                '}';
    }

    @Override
    public String getIndex() {
        return indexVector;
    }

    @Override
    public String getData() {
        return sleepState;
    }

    @Override
    public Timestamp getTime() {
        return updateTime;
    }

    @Override
    public void setData(String data) {
        sleepState=data;
    }
}