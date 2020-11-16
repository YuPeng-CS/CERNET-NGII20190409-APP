package sics.tool;

import android.content.SharedPreferences;

public class SharedPreferencesTool {

    public static void insertObjectInSp(SharedPreferences sp, String key,Object obj){
        String strOfObj=ObjStrTool.compress(obj);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key,strOfObj);
        editor.apply();
    }

    public static Object selectObjectFromSp(SharedPreferences sp, String key){
        String strOfObj=sp.getString(key,"");
        if(strOfObj==null||strOfObj.length()==0)return null;
        return ObjStrTool.uncompress(strOfObj);
    }

    public static void insertLongInSp(SharedPreferences sp, String key, Long data) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong(key,data);
        editor.apply();
    }
}
