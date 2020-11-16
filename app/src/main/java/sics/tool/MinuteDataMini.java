package sics.tool;

import com.Index.Index;
import com.encrypty.Query;
import com.encrypty.SecretKey;
import com.encrypty.splitedMatrix;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import sics.bean.AbstractTimeData;
import sics.bean.CommonData;
import sics.bean.PairData;

public class MinuteDataMini {

    /*
        检查数据是否在区间之中
     */
    public static void check(List<? extends AbstractTimeData> list, double begin, double end, SecretKey sk){
        splitedMatrix sb = Index.getSecureQueryIndex(begin, sk);
        splitedMatrix se = Index.getSecureQueryIndex(end, sk);
        list.removeIf((Predicate<AbstractTimeData>) abstractTimeData -> {
            splitedMatrix si= (splitedMatrix) ObjStrTool.uncompress(abstractTimeData.getIndex());
            return Index.isIn(si,sb,se)!= Query.IN;
        });
    }

    /*
        将AES加密的数据进行解密
     */
    public static void decode(List<? extends AbstractTimeData> list, String aesKey){
        for(AbstractTimeData m:list){
            try {
                m.setData(AesTool.decrypt(aesKey,m.getData()));
            } catch (Exception e) {
                m.setData(0+"");
            }
        }
    }

    /*
        interval:ms
        数据分割的时间间隔
     */
    public static List<CommonData> getSumList(List<? extends AbstractTimeData> list, long interval, Boolean isAverage){
        List<CommonData> result=new ArrayList<>();
        if(list==null||list.size()==0)return result;

        long timeBegin=TimeTool.getTimeBegin(list.get(0).getTime().getTime());
        long timeEnd=TimeTool.getTimeEnd(list.get(list.size()-1).getTime().getTime());
        int iData=0;
        for(long t=timeBegin;t<timeEnd;t+=interval){
            CommonData commonData=new CommonData();
            int num=0;
            int sum=0;
            A:while(true){
                if(iData>=list.size())break;
                AbstractTimeData abM=list.get(iData);
                long dTime= abM.getTime().getTime();
                switch(TimeTool.timeIn(t,interval,dTime)){
                    case TimeTool.DOWN:
                        iData++;
                        break;
                    case TimeTool.IN:
                        num++;
                        sum+=Integer.parseInt(abM.getData());
                        iData++;
                        break;
                    case TimeTool.UP:
                        break A;
                }
            }

            if(num!=0){
                commonData.setTime(t);
                if(isAverage)commonData.setData(sum/num);
                else commonData.setData(sum);
                result.add(commonData);
            }
        }
        return result;
    }

    /*
        统计各个段数据量的多少
     */
    public static List<PairData> getNumList(List<CommonData> list,int interval){
        List<PairData> result=new ArrayList<>();
        if(list==null||list.size()==0)return result;
        Collections.sort(list);
        int begin=(list.get(0).getData()/interval-1)*interval;
        int end=(list.get(list.size()-1).getData()/interval+1)*interval;
        int index=0;
        System.out.println(begin+" "+end);
        for(int i=begin;i<end;i+=interval){
            PairData pairData=new PairData();

            int num=0;
            int sum=0;
            while (true){
                if(index>=list.size())break;
                int data=list.get(index).getData();
                if(data<i){
                    index++;
                    break;
                }else if(data<(i+interval)){
                    index++;
                    num++;
                    sum+=data;
                }else {
                    break;
                }
            }

            if(num!=0){
                pairData.setX(i);
                pairData.setY(num);
                result.add(pairData);
            }
        }
        return result;
    }
}
