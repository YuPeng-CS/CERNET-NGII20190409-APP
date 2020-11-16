package sics.bean;

import java.sql.Timestamp;

public class MinuteSteps extends AbstractTimeData {
    private Long id;
    private Long userId;
    private String minuteSteps;
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

    public String getMinuteSteps() {
        return minuteSteps;
    }

    public void setMinuteSteps(String minuteSteps) {
        this.minuteSteps = minuteSteps;
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
        return "MinuteSteps{" +
                "id=" + id +
                ", userId=" + userId +
                ", minuteSteps='" + minuteSteps + '\'' +
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
        return minuteSteps;
    }

    @Override
    public Timestamp getTime() {
        return updateTime;
    }

    @Override
    public void setData(String data) {
        minuteSteps=data;
    }
}