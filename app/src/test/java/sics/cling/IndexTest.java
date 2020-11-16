package sics.cling;

import com.Index.Index;
import com.encrypty.Gen;
import com.encrypty.SecretKey;
import com.encrypty.splitedMatrix;

import org.junit.Test;

import sics.tool.ObjStrTool;

import static org.junit.Assert.assertEquals;

public class IndexTest {
    @Test
    public void indexTest(){
        SecretKey sk= Gen.GenKey(128);
        splitedMatrix index = Index.getSecureDataIndex(14.5, sk);
        String encIndex=ObjStrTool.compress(index);
        splitedMatrix decIndex= (splitedMatrix) ObjStrTool.uncompress(encIndex);
        System.out.println(index.toString().length()+" "+index.toString());
        System.out.println(encIndex.length()+" "+encIndex);
        System.out.println(decIndex.toString().length()+" "+decIndex.toString());
        assertEquals(index.toString(),decIndex.toString());
    }

    @Test
    public void testQuery(){
        SecretKey sk= Gen.GenKey(128);
        splitedMatrix si = Index.getSecureDataIndex(11, sk);
        splitedMatrix si1=Index.getSecureDataIndex(13,sk);
        splitedMatrix si2=Index.getSecureDataIndex(16,sk);
        splitedMatrix sb = Index.getSecureQueryIndex(12, sk);
        splitedMatrix se = Index.getSecureQueryIndex(15, sk);
        assertEquals(Index.isIn(si, sb, se), -1);
        assertEquals(Index.isIn(si1, sb, se), 0);
        assertEquals(Index.isIn(si2, sb, se), 1);
    }
}
