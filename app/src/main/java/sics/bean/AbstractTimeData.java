package sics.bean;
import java.sql.Timestamp;

public abstract class AbstractTimeData {
    abstract public String getIndex();
    abstract public String getData();
    abstract public Timestamp getTime();
    abstract public void setData(String data);
}
