package com.Index;

import com.encrypty.IndexEncrypty;
import com.encrypty.Query;
import com.encrypty.QueryEncrypty;
import com.encrypty.SecretKey;
import com.encrypty.splitedMatrix;

public class Index {

    public static splitedMatrix getSecureDataIndex(double data, SecretKey sk) {
        IndexEncrypty indexEncrypty = new IndexEncrypty();
        double[] index = indexEncrypty.BuildIndexVector(data, sk);
        splitedMatrix splitedMatrix = indexEncrypty.splitIndexVector(index, sk);
        return indexEncrypty.EncIndexVector(splitedMatrix, sk);
    }

    public static splitedMatrix getSecureQueryIndex(double data, SecretKey sk) {
        QueryEncrypty queryEncrypty = new QueryEncrypty();
        double[] index = queryEncrypty.BuildQueryVector(data, sk);
        splitedMatrix splitedMatrix = queryEncrypty.splitQueryVector(index, sk);
        return queryEncrypty.EncQueryVector(splitedMatrix, sk);
    }

    public static int isIn(splitedMatrix dataIndex1,splitedMatrix queryIndexLeft,splitedMatrix queryIndexRight){
        return new Query().Search(queryIndexLeft, queryIndexRight, dataIndex1);
    }
}
