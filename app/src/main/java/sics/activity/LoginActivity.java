package sics.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import org.mindrot.jbcrypt.BCrypt;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;
import sics.cling.R;
import sics.observable.UserInfoObservable;
import sics.tool.SharedPreferencesTool;


public class LoginActivity extends BaseActivity {

    @BindView(R.id.login_edittext_username)
    EditText email;
    @BindView(R.id.login_edittext_password)
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initEditText();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_login;
    }

    private void initEditText() {
        Disposable dis = UserInfoObservable.selectUserInfoFromSp(sp).subscribe(userInfo -> {
            email.setText(userInfo.getEmail() == null ? "" : userInfo.getEmail());
            password.setText(userInfo.getPassword() == null ? "" : userInfo.getPassword());
        }, this::onError);
        compositeDisposable.add(dis);
    }

    @OnClick(R.id.login_button_login)
    public void login(View view) {
        final String strEmail = email.getText().toString().trim();
        final String strPassword = password.getText().toString().trim();
        if (strEmail.length() <= 0) {
            email.setError("请输入邮箱");
            return;
        }
        if (strPassword.length() <= 0) {
            password.setError("请输入密码");
            return;
        }

        Disposable dis = UserInfoObservable.selectUserInfoFromDb(strEmail).subscribe(userInfo -> {
            if (!strEmail.equals(userInfo.getEmail())) {
                email.setError("用户未注册");
                return;
            }
            if (!BCrypt.checkpw(strPassword, userInfo.getPassword())) {
                password.setError("密码错误");
                return;
            } else {
                userInfo.setPassword(strPassword);
            }
            SharedPreferencesTool.insertObjectInSp(sp, "userInfo", userInfo);
            SharedPreferencesTool.insertLongInSp(sp, "userId", userInfo.getUid());
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }, this::onError, this::onComplete, this::onSubscribe);
        compositeDisposable.add(dis);
    }


    @OnClick(R.id.login_button_register)
    public void navToRegister() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}