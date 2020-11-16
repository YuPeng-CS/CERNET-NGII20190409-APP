package sics.tool;


import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;

public class MyObjectInputStream extends ObjectInputStream {

    public MyObjectInputStream(InputStream arg0) throws IOException {
        super(arg0);
    }

    protected Class<?> resolveClass(ObjectStreamClass desc) throws IOException, ClassNotFoundException {
        String name = desc.getName();
        try {
            if (name.startsWith("com.scis.web.secureiotcloudservicebackend.alo.encrypty.SecretKey"))
                name = name.replace("com.scis.web.secureiotcloudservicebackend.alo.encrypty", "com.encrypty");
            return Class.forName(name);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return super.resolveClass(desc);
    }
}
