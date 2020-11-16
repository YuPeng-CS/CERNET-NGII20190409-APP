package sics.tool;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ObjStrTool {

    public static String compress(Object object) {
        try (
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                ObjectOutputStream obj=new ObjectOutputStream(out)
        ){
            obj.writeObject(object);
            return Base64Tool.encode(out.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static Object uncompress(String compressedStr) {
        if (compressedStr == null || compressedStr.length()==0) return null;
        byte[] compressed = Base64Tool.decode(compressedStr);
        try (
                ByteArrayInputStream in = new ByteArrayInputStream(compressed);
                ObjectInputStream obj = new ObjectInputStream(in)
        ) {
            return  obj.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object uncompressSk(String compressedStr) {
        if (compressedStr == null) return null;
        byte[] compressed = Base64Tool.decode(compressedStr);
        try (
                ByteArrayInputStream in = new ByteArrayInputStream(compressed);
                MyObjectInputStream obj = new MyObjectInputStream(in)
        ) {
            return  obj.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}