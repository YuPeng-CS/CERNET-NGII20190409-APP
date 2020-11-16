package sics.bean;

import java.sql.Timestamp;

public class MinuteHeartRate extends AbstractTimeData {
    private Long id;
    private Long userId;
    private String minuteHeartRate;
    private Timestamp updateTime;
    private String indexVector;

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

    public String getMinuteHeartRate() {
        return minuteHeartRate;
    }

    public void setMinuteHeartRate(String minuteHeartRate) {
        this.minuteHeartRate = minuteHeartRate;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public String getIndexVector() {
        return indexVector;
    }

    public void setIndexVector(String indexVector) {
        this.indexVector = indexVector;
    }

    @Override
    public String toString() {
        return "MinuteHeartRate{" +
                "id=" + id +
                ", userId=" + userId +
                ", minuteHeartRate='" + minuteHeartRate + '\'' +
                ", updateTime=" + updateTime +
                ", indexVector='" + indexVector + '\'' +
                '}';
    }

    @Override
    public String getIndex() {
        return indexVector;
    }

    @Override
    public String getData() {
        return minuteHeartRate;
    }

    @Override
    public Timestamp getTime() {
        return updateTime;
    }

    @Override
    public void setData(String data) {
        minuteHeartRate=data;
    }
}