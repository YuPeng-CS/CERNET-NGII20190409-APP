package sics.react;

public class VariableSubject extends AbstractSubject{
    private int data;

    public VariableSubject(int i) {
        super();
        data=i;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        if(this.data!=data){
            this.data = data;
            this.notifyAllUpdateListener(data);
        }
    }
}
