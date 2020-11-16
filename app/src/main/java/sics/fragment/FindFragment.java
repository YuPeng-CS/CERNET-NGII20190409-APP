package sics.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import sics.activity.MainActivity;
import sics.activity.WebActivity;
import sics.cling.R;

public class FindFragment extends Fragment {

    private CompositeDisposable compositeDisposable;
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find, container, false);
        unbinder = ButterKnife.bind(this, view);
        compositeDisposable = new CompositeDisposable();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(unbinder!=null)unbinder.unbind();
        if(compositeDisposable!=null)compositeDisposable.clear();
    }

    @OnClick({R.id.frag_find_heart,R.id.frag_find_heart1,R.id.frag_find_sleep,R.id.frag_find_sleep1,R.id.frag_find_run,R.id.frag_find_run1})
    public void navToWeb(View view){
        Intent intent = new Intent(MainActivity.instance, WebActivity.class);
        switch (view.getId()) {
            case R.id.frag_find_heart:
                intent.putExtra("url", R.string.url_heart);
                break;
            case R.id.frag_find_heart1:
                intent.putExtra("url", R.string.url_heart1);
                break;
            case R.id.frag_find_sleep:
                intent.putExtra("url", R.string.url_sleep);
                break;
            case R.id.frag_find_sleep1:
                intent.putExtra("url", R.string.url_sleep1);
                break;
            case R.id.frag_find_run:
                intent.putExtra("url", R.string.url_run);
                break;
            case R.id.frag_find_run1:
                intent.putExtra("url", R.string.url_run1);
                break;
        }
        startActivity(intent);
    }
}
