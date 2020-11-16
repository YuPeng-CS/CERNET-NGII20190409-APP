package sics.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.CompoundButton;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.OnCheckedChanged;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import sics.cling.R;
import sics.fragment.EquipFragment;
import sics.fragment.FindFragment;
import sics.fragment.HomeFragment;
import sics.fragment.PersonFragment;

public class MainActivity extends BaseActivity {

    public static MainActivity instance;
    private ArrayList<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        initFragment();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    public void showToast(String text) {
        Disposable dis = Observable.just(text).observeOn(AndroidSchedulers.mainThread()).subscribe(str -> {
            Toast.makeText(MainActivity.this, str, Toast.LENGTH_SHORT).show();
        });
        compositeDisposable.add(dis);
    }

    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new EquipFragment());
        fragments.add(new FindFragment());
        fragments.add(new PersonFragment());
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        for (Fragment f : fragments) {
            ft.add(R.id.main_fragment, f);
            ft.hide(f);
        }
        ft.show(fragments.get(0));
        ft.commit();
    }

    private FragmentTransaction hindAllFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        for (Fragment f : fragments) {
            ft.hide(f);
        }
        return ft;
    }

    @OnCheckedChanged({R.id.main_rb_home, R.id.main_rb_equip, R.id.main_rb_find, R.id.main_rb_person})
    void initRg(CompoundButton view, boolean isChecked) {
        if (!isChecked) return;
        FragmentTransaction ft = hindAllFragment();
        switch (view.getId()) {
            case R.id.main_rb_home:
                ft.show(fragments.get(0));
                break;
            case R.id.main_rb_equip:
                ft.show(fragments.get(1));
                break;
            case R.id.main_rb_find:
                ft.show(fragments.get(2));
                break;
            case R.id.main_rb_person:
                ft.show(fragments.get(3));
                break;
        }
        ft.commit();
    }
}