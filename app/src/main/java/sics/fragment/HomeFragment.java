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
import sics.activity.FindActivity;
import sics.activity.MainActivity;
import sics.cling.R;

public class HomeFragment extends Fragment {
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.home_heart)
    void navToHeart() {
        Intent intent = new Intent(MainActivity.instance, FindActivity.class);
        intent.putExtra("kind",0);
        MainActivity.instance.startActivity(intent);
    }

    @OnClick(R.id.home_temp)
    void navToSleep() {
        Intent intent = new Intent(MainActivity.instance,  FindActivity.class);
        intent.putExtra("kind",1);
        MainActivity.instance.startActivity(intent);
    }

    @OnClick(R.id.home_run)
    void navToRun() {
        Intent intent = new Intent(MainActivity.instance,  FindActivity.class);
        intent.putExtra("kind",2);
        MainActivity.instance.startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
