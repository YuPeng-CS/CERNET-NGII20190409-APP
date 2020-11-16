package sics.cling;

import org.junit.Test;

import sics.react.UpdateListener;
import sics.react.VariableSubject;

public class reactTest {

    @Test
    public void test1(){
        VariableSubject vs=new VariableSubject(0);
        vs.addUpdateListener(new UpdateListener() {
            @Override
            public void update(int object) {
                System.out.println(object);
            }
        });
        vs.setData(12);
        vs.setData(13);
        vs.setData(15);
    }
}
