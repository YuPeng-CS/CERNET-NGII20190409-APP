package sics.react;

public interface Subject{
    public void addUpdateListener(UpdateListener ul);
    public void delUpdateListener(UpdateListener ul);
    public void notifyAllUpdateListener(int data);
}
