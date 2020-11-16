package sics.tool;

import java.util.Calendar;

public class TimeTool {
    public final static int DOWN=-1;
    public final static int IN=0;
    public final static int UP=1;


    public static long getTimeBegin(long time){
        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(time/1000*1000);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.SECOND,0);
        return calendar.getTimeInMillis();
    }

    public static long getTimeEnd(long time){
        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(time/1000*1000);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.SECOND,0);
        return calendar.getTimeInMillis()+24*60*60*1000;
    }

    public static int timeIn(long time,long interval,long testTime){
        long tu=time +interval;
        if(testTime< time)return DOWN;
        else if(testTime < tu)return IN;
        else return UP;
    }
}
