package sics.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;
import sics.cling.R;
import sics.observable.UserInfoObservable;


public class RegisterActivity extends BaseActivity {


    @BindView(R.id.register_edittext_username)
    EditText email;
    @BindView(R.id.register_edittext_password)
    EditText password;
    @BindView(R.id.register_edittext_confirm_password)
    EditText confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_register;
    }

    @OnClick(R.id.register_button_register)
    public void register() {
        final String strEmail = email.getText().toString().trim();
        final String strPassword = password.getText().toString().trim();
        String strConfirmPassword = confirmPassword.getText().toString().trim();
        if (strEmail.length() <= 0) {
            email.setError("请输入邮箱");
            return;
        }
        if (strPassword.length() <= 0) {
            password.setError("请输入密码");
            return;
        }
        if (strConfirmPassword.length() <= 0) {
            confirmPassword.setError("请输入确认密码");
            return;
        }
        if (!strPassword.equals(strConfirmPassword)) {
            confirmPassword.setError("确认密码不一致");
            return;
        }
        Disposable dis = UserInfoObservable.insertUserInfoInDb(strEmail, strPassword).subscribe(integer -> {
            if (integer == 0) {
                email.setError("用户名已存在");
                return;
            }
            Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
        }, this::onError, this::onComplete, this::onSubscribe);
        compositeDisposable.add(dis);
    }
}