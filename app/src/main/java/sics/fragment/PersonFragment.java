package sics.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import sics.activity.MainActivity;
import sics.bean.UserInfo;
import sics.cling.R;
import sics.observable.UserInfoObservable;
import sics.tool.ObjStrTool;

public class PersonFragment extends Fragment {

    @BindView(R.id.person_sex)
    RadioGroup rg;
    @BindView(R.id.person_img)
    ImageView iv;
    @BindView(R.id.person_name)
    TextView username;
    @BindView(R.id.person_email)
    TextView email;
    @BindView(R.id.person_age)
    EditText age;
    @BindView(R.id.person_nickname)
    EditText nickname;
    private boolean isMan;
    private SharedPreferences sp;
    private Unbinder unbinder;
    private CompositeDisposable compositeDisposable;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_person, container, false);
        compositeDisposable = new CompositeDisposable();
        unbinder = ButterKnife.bind(this, view);
        rg.check(R.id.person_man);
        sp = Objects.requireNonNull(getActivity()).getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        initEditText();
        return view;
    }

    private void initEditText() {
        UserInfo userInfoInit = (UserInfo) ObjStrTool.uncompress(sp.getString("userInfo", ""));
        if (userInfoInit == null)return;
        String strAge = userInfoInit.getAge() + "";
        String strUserName = userInfoInit.getUsername() + "";
        String strNickName = userInfoInit.getNickname() + "";
        String strEmail = userInfoInit.getEmail() + "";
        int intGender = userInfoInit.getGender();
        age.setText(strAge);
        email.setText(strEmail);
        username.setText(strUserName);
        nickname.setText(strNickName);
        rg.check(intGender == 1 ? R.id.person_man : R.id.person_woman);
    }

    @OnClick(R.id.person_save)
    void initSave() {
        UserInfo userInfo = (UserInfo) ObjStrTool.uncompress(sp.getString("userInfo", ""));
        if (userInfo != null) {
            String strAge = age.getText().toString().trim();
            if (strAge.length() > 0) userInfo.setAge(Integer.parseInt(strAge));
            userInfo.setNickname(nickname.getText().toString().trim());
            userInfo.setGender(isMan ? 1 : 0);
        }
        Disposable dis = UserInfoObservable.updateUserInfoInDb(userInfo).subscribe(integer -> {
            if (integer == 1) {
                MainActivity.instance.showToast("保存成功");
            } else {
                MainActivity.instance.showToast("保存失败");
            }
        },MainActivity.instance::onError,MainActivity.instance::onComplete,MainActivity.instance::onSubscribe);
        compositeDisposable.add(dis);
    }

    @OnCheckedChanged({R.id.person_man, R.id.person_woman})
    void initRg(CompoundButton view, boolean isChecked) {
        if (!isChecked) return;
        switch (view.getId()) {
            case R.id.person_man:
                isMan = true;
                iv.post(() -> iv.setImageDrawable(getResources().getDrawable(R.drawable.ic_man)));
                break;
            case R.id.person_woman:
                isMan = false;
                iv.post(() -> iv.setImageDrawable(getResources().getDrawable(R.drawable.ic_woman)));
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        compositeDisposable.clear();
        unbinder.unbind();
    }
}
