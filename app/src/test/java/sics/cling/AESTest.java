package sics.cling;

import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Base64;

import sics.tool.AesTool;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AESTest {

    @Test
    public void testAES() throws Exception {
        String key="aassddffgghhjjkk";
        String text="1932";
        String encText= AesTool.encrypt(key,text);
        String decText= AesTool.decrypt(key,"38QB47fF/bJPeVtwntsDjg==");

        System.out.println("原文："+text);
        System.out.println("加密："+encText);
        System.out.println("解密："+decText);
        assertEquals(text,decText);
    }

    @Test
    public void testBase641(){
        String text="1234567";
        String encText=  Base64.getEncoder().encodeToString(text.getBytes());
        String decText= new String(Base64.getDecoder().decode(encText));
        System.out.println("原文："+text);
        System.out.println("加密："+encText);
        System.out.println("解密："+decText);
        assertEquals(text,decText);
    }

    @Test
    public void testBc(){
        String text="1234567";
        String encText= BCrypt.hashpw(text,BCrypt.gensalt());
        boolean decText= BCrypt.checkpw(text,encText);
        System.out.println("原文："+text);
        System.out.println("加密："+encText);
        System.out.println("解密："+decText);
        assertTrue(decText);
    }
}