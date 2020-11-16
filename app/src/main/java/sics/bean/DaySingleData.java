package sics.bean;

import java.sql.Timestamp;

public class DaySingleData extends AbstractTimeData{

    private Long id;
    private Long userId;
    private String indexVector;
    private String kind;
    private String data;
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

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String getIndex() {
        return indexVector;
    }

    @Override
    public Timestamp getTime() {
        return updateTime;
    }

    @Override
    public String toString() {
        return "DaySingleData{" +
                "id=" + id +
                ", userId=" + userId +
                ", kind='" + kind + '\'' +
                ", data='" + data + '\'' +
                ", updateTime=" + updateTime +
                '}';
    }
}
