package com.test;

import com.encrypty.Gen;
import com.encrypty.IndexEncrypty;
import com.encrypty.Query;
import com.encrypty.QueryEncrypty;
import com.encrypty.SecretKey;
import com.encrypty.splitedMatrix;

import java.util.Arrays;

public class Demo {

    public static splitedMatrix getSecureDataIndex(double data, SecretKey sk) {
        splitedMatrix secureDataIndex = null;

        IndexEncrypty indexEncrypty = new IndexEncrypty();

        double[] index = indexEncrypty.BuildIndexVector(data, sk);
        System.out.println(Arrays.toString(index));

        splitedMatrix splitedMatrix = indexEncrypty.splitIndexVector(index, sk);
        System.out.println(splitedMatrix);
        secureDataIndex = indexEncrypty.EncIndexVector(splitedMatrix, sk);
        System.out.println(secureDataIndex);
        return secureDataIndex;
    }

    public static splitedMatrix getSecureQueryIndex(double data, SecretKey sk) {
        splitedMatrix secureQueryIndex = null;

        QueryEncrypty queryEncrypty = new QueryEncrypty();
        double[] index = queryEncrypty.BuildQueryVector(data, sk);
        splitedMatrix splitedMatrix = queryEncrypty.splitQueryVector(index, sk);
        secureQueryIndex = queryEncrypty.EncQueryVector(splitedMatrix, sk);
        return secureQueryIndex;
    }


    public static void main(String[] args) {
        SecretKey sk = Gen.GenKey(4);
        double data = 2.0;
        double queryLeft = 2.0;
        double queryRight = 4.0;
        splitedMatrix dataIndex1 = getSecureDataIndex(data, sk);
        splitedMatrix queryIndexLeft = getSecureQueryIndex(queryLeft, sk);
        splitedMatrix queryIndexRight = getSecureQueryIndex(queryRight, sk);
        System.out.println(new Query().Search(queryIndexLeft, queryIndexRight, dataIndex1));
    }
}
