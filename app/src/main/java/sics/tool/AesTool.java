package sics.tool;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AesTool {

    public static final String VIPARA = "0123456789abcdef";
    public static final String bm = "UTF-8";

    public static String encrypt(String dataPassword, String cleartext)
            throws Exception {
        IvParameterSpec zeroIv = new IvParameterSpec(VIPARA.getBytes());
        SecretKeySpec key = new SecretKeySpec(dataPassword.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
        byte[] encryptedData = cipher.doFinal(cleartext.getBytes(bm));
        return Base64Tool.encode(encryptedData);
    }

    public static String decrypt(String dataPassword, String encrypted)
            throws Exception {
        byte[] byteMi = Base64Tool.decode(encrypted);
        IvParameterSpec zeroIv = new IvParameterSpec(VIPARA.getBytes());
        SecretKeySpec key = new SecretKeySpec(dataPassword.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
        byte[] decryptedData = cipher.doFinal(byteMi);
        return new String(decryptedData, bm);
    }

    public static Integer decryptToInteger(String dataPassword, String encrypted)
            throws Exception {
        byte[] byteMi = Base64Tool.decode(encrypted);
        IvParameterSpec zeroIv = new IvParameterSpec(VIPARA.getBytes());
        SecretKeySpec key = new SecretKeySpec(dataPassword.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
        byte[] decryptedData = cipher.doFinal(byteMi);
        return Integer.parseInt(new String(decryptedData));
    }
}
