package sics.bean;

public class CommonData implements Comparable<CommonData>{
    int data;
    long time;

    public CommonData(int data, long time) {
        this.data = data;
        this.time = time;
    }

    public CommonData() {
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "CommonData{" +
                "data=" + data +
                ", time=" + time +
                '}';
    }

    @Override
    public int compareTo(CommonData commonData) {
        return Integer.compare(data,commonData.getData());
    }
}
