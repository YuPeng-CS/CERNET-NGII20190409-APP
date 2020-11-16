package sics.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import sics.cling.R;
import sics.exception.DbException;

public abstract class BaseActivity extends AppCompatActivity {

    protected CompositeDisposable compositeDisposable;
    protected Unbinder unbinder;
    protected SharedPreferences sp;
    protected Dialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        compositeDisposable=new CompositeDisposable();
        unbinder=ButterKnife.bind(this);
        sp=getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(getBaseContext()).inflate(R.layout.dialog_being, null, false);
        builder.setView(dialogView);
        dialog = builder.create();
    }

    protected abstract int getContentViewId();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(compositeDisposable!=null){
            compositeDisposable.clear();
        }
        if(unbinder!=null){
            unbinder.unbind();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        updateDialog(false);
    }

    public void onError(Throwable e){
        updateDialog(false);
        if(e instanceof DbException){
            Toast.makeText(this,((DbException) e).getWhat(),Toast.LENGTH_SHORT).show();
        }
    }

    public void onComplete(){
        updateDialog(false);
    }

    public void onSubscribe(Disposable d){
        compositeDisposable.add(d);
        updateDialog(true);
    }

    public void updateDialog(boolean bool){
        Disposable dis = Observable.just(bool).observeOn(AndroidSchedulers.mainThread()).subscribe(aBoolean -> {
            if(dialog==null)return;
            if(aBoolean&&!dialog.isShowing()){
                dialog.show();
                dialog.setCanceledOnTouchOutside(true);
            }else {
                if(dialog.isShowing())dialog.dismiss();
            }
        });
        compositeDisposable.add(dis);
    }
}
