package sics.observable;

import android.content.SharedPreferences;

import org.mindrot.jbcrypt.BCrypt;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import sics.bean.UserInfo;
import sics.mysql.ConfigDb;
import sics.mysql.RoleUserDb;
import sics.mysql.UserInfoDb;
import sics.tool.ObjStrTool;

public class UserInfoObservable {
    public static Observable<UserInfo> selectUserInfoFromSp(SharedPreferences sp){
        return Observable.create((ObservableEmitter<UserInfo> emitter) -> {
            UserInfo userInfo = (UserInfo) ObjStrTool.uncompress(sp.getString("userInfo", ""));
            emitter.onNext(userInfo);
            emitter.onComplete();
        }).observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<UserInfo> selectUserInfoFromDb(String email){
        return Observable.create((ObservableEmitter<UserInfo> emitter) -> {
            UserInfo input=new UserInfo();
            input.setEmail(email);
            UserInfo userInfo = UserInfoDb.selectUserInfo(input);
            emitter.onNext(userInfo);
            emitter.onComplete();
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<Integer> insertUserInfoInDb(String email,String password){
        return Observable.create((ObservableEmitter<Integer> emitter) -> {
            UserInfo userInfo = new UserInfo();
            userInfo.setEmail(email);
            userInfo.setUsername(email);
            userInfo.setNickname(email);
            userInfo.setGender(0);
            userInfo.setAge(0);
            userInfo.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
            int arg1=UserInfoDb.insertUserInfo(userInfo);
            UserInfo newUserInfo=UserInfoDb.selectUserInfo(userInfo);
            int arg2= RoleUserDb.insertRoleUser(newUserInfo.getUid(),1);
            //int arg3= ConfigDb.insertConfigValue(1);
            //int arg4= ConfigDb.insertConfigValue(2);
            emitter.onNext(arg1+arg2);
            emitter.onComplete();
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<Integer> updateUserInfoInDb(UserInfo userInfo){
        return Observable.create((ObservableEmitter<Integer> emitter) -> {
            emitter.onNext(UserInfoDb.updateUserInfo(userInfo));
            emitter.onComplete();
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
