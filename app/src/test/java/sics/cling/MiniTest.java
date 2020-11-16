package sics.cling;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sics.bean.CommonData;
import sics.bean.PairData;
import sics.tool.MinuteDataMini;
import sics.tool.TimeTool;

public class MiniTest {

    @Test
    public void miniTest() throws Exception {
        int[] a=new int[6];
        Arrays.fill(a,0);
        for(int i=0;i<6;i++)System.out.println(a[i]);
    }

    @Test
    public void testTimeIn(){
        long time=System.currentTimeMillis();
        long testTime=time+30;
        System.out.println(TimeTool.timeIn(time,20,testTime));
        System.out.println(TimeTool.timeIn(time,60,testTime));
        System.out.println(TimeTool.timeIn(time,61,testTime));
    }

    @Test
    public void getNumListTest(){
        List<CommonData> list=new ArrayList<>();
        list.add(new CommonData(12,13));
        list.add(new CommonData(21,45));
        list.add(new CommonData(34,12));
        List<PairData> result=MinuteDataMini.getNumList(list,5);
        System.out.println(result);
    }
}
