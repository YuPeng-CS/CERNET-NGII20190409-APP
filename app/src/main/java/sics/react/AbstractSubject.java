package sics.react;

import java.util.Vector;

public abstract  class AbstractSubject implements Subject{
    private Vector<UpdateListener> vector = new Vector<UpdateListener>();

    @Override
    public void addUpdateListener(UpdateListener updateListener) {
        vector.add(updateListener);
    }

    @Override
    public void delUpdateListener(UpdateListener updateListener) {
        vector.remove(updateListener);
    }

    @Override
    public void notifyAllUpdateListener(int data) {
        for (UpdateListener listener : vector) {
            listener.update(data);
        }
    }
}
