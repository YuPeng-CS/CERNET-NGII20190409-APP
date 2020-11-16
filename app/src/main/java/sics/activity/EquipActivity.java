package sics.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hicling.clingsdk.ClingSdk;
import com.hicling.clingsdk.bleservice.BluetoothDeviceInfo;
import com.hicling.clingsdk.listener.OnBleListener;
import com.hicling.clingsdk.listener.OnSdkReadyListener;
import com.hicling.clingsdk.model.DayTotalDataModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import sics.bean.DayData;
import sics.bean.MinuteData;
import sics.cling.R;
import sics.exception.DbException;
import sics.observable.DataObservable;
import sics.react.VariableSubject;

public class EquipActivity extends BaseActivity {

    private final static int REGISTER_CODE = 5958117;
    private final static String APP_KEY="HC8bd36e0730e58916";
    private final static String APP_SECRET="c34ac9bd998c5f9e9a26ad03cb04664f";

    public boolean mbClingSdkReady = false;
    private boolean mbRegisterDevice = false;
    //0:未开始连接 1：连接中 2：已连接
    private VariableSubject miDeviceConnect = new VariableSubject(0);
    //0:未开始同步 1：同步中 2：同步结束 3：同步失败
    private VariableSubject miDataSynState = new VariableSubject(0);
    //0:未开始发送 1：发送中 2: 发送结束 3:发送失败
    private VariableSubject miSendState = new VariableSubject(0);

    @BindView(R.id.equip_state_watch)
    TextView watch;
    @BindView(R.id.equip_start_scan)
    Button startScan;
    @BindView(R.id.equip_tip)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.equip_data_heart)
    TextView heart;
    @BindView(R.id.equip_data_temp)
    TextView temp;
    @BindView(R.id.equip_data_step)
    TextView step;
    @BindView(R.id.equip_data_cal)
    TextView cal;
    @BindView(R.id.equip_data_bp)
    TextView bp;
    @BindView(R.id.equip_data_hp)
    TextView hp;
    @BindView(R.id.equip_progress)
    ProgressBar progressBar;
    @BindView(R.id.equip_progress_text)
    TextView proText;

    public static BluetoothDeviceInfo bluetoothDeviceInfo;
    public static List<MinuteData> dataList = new CopyOnWriteArrayList<>();
    public static List<Long> timeList=new CopyOnWriteArrayList<>();
    public static DayData dayData;


    private OnSdkReadyListener onSdkReadyListener = new OnSdkReadyListener() {
        @Override
        public void onClingSdkReady() {
            mbClingSdkReady = true;
        }

        @Override
        public void onFailed(String s) {
            mbClingSdkReady = false;
        }
    };

    private OnBleListener.OnRegisterDeviceListener onRegisterDeviceListener = new OnBleListener.OnRegisterDeviceListener() {
        @Override
        public void onRegisterDeviceSucceed() {
            mbRegisterDevice = true;
        }

        @Override
        public void onRegisterDeviceFailed(int i, String s) {
            mbRegisterDevice = false;
        }
    };

    private OnBleListener.OnBleDataListener onBleDataListener = new OnBleListener.OnBleDataListener() {
        @Override
        public void onGotSosMessage() {}

        @Override
        public void onDataSyncingProgress(Object o) {}

        @Override
        public void onDataSyncedFromDevice() {
            miDataSynState.setData(2);
        }

        @Override
        public void onDataSyncingMinuteData(Object o) {
            miDataSynState.setData(1);
            MinuteData minuteData=new MinuteData((com.hicling.clingsdk.model.MinuteData) o);
            if(!timeList.contains(minuteData.getMinuteTimeStamp())){
                dataList.add(minuteData);
                timeList.add(minuteData.getMinuteTimeStamp());
            }
        }

        @Override
        public void onGetDayTotalData(DayTotalDataModel dayTotalDataModel) {
            dayData=new DayData(dayTotalDataModel);
            Disposable dis = Observable.just(dayTotalDataModel).observeOn(AndroidSchedulers.mainThread()).subscribe(dataList -> {
                heart.setText(String.format("%s:%s", "心率", dataList.mHeartRate));
                temp.setText(String.format("%s:%s", "温度", (int)dataList.mSkinTemperature/60));
                cal.setText(String.format("%s:%s", "卡路里", dataList.mCaloriesTotal));
                step.setText(String.format("%s:%s", "步数", dataList.mStepTotal));
                hp.setText(String.format("%s:%s", "血压高压", dataList.mnBPhp));
                bp.setText(String.format("%s:%s", "血压低压", dataList.mnBPlp));
            });
            compositeDisposable.add(dis);
        }
    };

    private OnBleListener.OnDeviceConnectedListener onDeviceConnectedListener = new OnBleListener.OnDeviceConnectedListener() {
        @Override
        public void onDeviceConnected() {
            BluetoothDeviceInfo info = bluetoothDeviceInfo;
            BluetoothDevice bluetoothDevice = info.getmBleDevice();
            ClingSdk.connectDevice(bluetoothDevice);
            ClingSdk.loadDeviceData();
            miDeviceConnect.setData(2);
        }

        @Override
        public void onDeviceDisconnected() {
            miDeviceConnect.setData(0);
        }

        @Override
        public void onDeviceInfoReceived(Object o) { }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ClingSdk.init(this, APP_KEY,APP_SECRET, onSdkReadyListener);
        //在cling sdk中调用此代码以提高性能
        ClingSdk.setBleDataListener(onBleDataListener);
        ClingSdk.setDeviceConnectListener(onDeviceConnectedListener);
        ClingSdk.enableDebugMode(true);
        ClingSdk.start(this);
        progressBar.setVisibility(View.INVISIBLE);
        //添加对状态的监听与提示
        initMiConnect();
        initMiSync();
        initMiSend();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_equip;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ClingSdk.stop(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ClingSdk.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        ClingSdk.onPause(this);
    }

    private void initMiSend() {
        miSendState.addUpdateListener(dataList -> {
            switch (dataList) {
                case 0:
                    break;
                case 1:
                    changeProgressbar(true);
                    changeText(proText, "数据上传中");
                    break;
                case 2:
                    changeProgressbar(false);
                    changeText(watch,"已同步");
                    changeText(proText, "");
                    Disposable dis = Observable.just("数据上传成功").observeOn(AndroidSchedulers.mainThread()).subscribe(this::showTip);
                    compositeDisposable.add(dis);
                    break;
                case 3:
                    changeText(watch,"已同步:"+timeList.size());
                    showTip("数据上传失败");
                    break;
            }
        });
    }

    private void initMiSync() {
        miDataSynState.addUpdateListener(i -> {
            switch (i) {
                case 0:
                    break;
                case 1:
                    changeProgressbar(true);
                    changeText(proText, "同步中");
                    break;
                case 2:
                    changeText(watch, "已同步:" + dataList.size());
                    changeProgressbar(false);
                    changeText(proText, "");
                    break;
            }
        });
    }

    private void initMiConnect() {
        miDeviceConnect.addUpdateListener(dataList -> {
            switch (dataList) {
                case 0:
                    changeText(watch, "未连接");
                    break;
                case 1:
                    changeProgressbar(true);
                    changeText(proText, "连接中");
                    break;
                case 2:
                    changeText(watch, "已连接");
                    changeProgressbar(false);
                    changeText(proText, "");
                    break;
            }
        });
    }

    //扫描设备,显示设备选择对话框，获取设备信息
    @OnClick(R.id.equip_start_scan)
    void initButtonStartScan() {
        if (miDeviceConnect.getData() == 2) {
            showTip("设备已连接");
            return;
        }
        if (bluetoothDeviceInfo != null) {
            showTip("设备信息已保存");
            ClingSdk.registerDevice(bluetoothDeviceInfo.getmBleDevice(), onRegisterDeviceListener);
            return;
        }
        requestPermission();
        if (!openBluetooth()) return;
        if (!mbClingSdkReady) return;
        if (!ClingSdk.isAccountBondWithCling())
            ClingSdk.setClingDeviceType(ClingSdk.CLING_DEVICE_TYPE_ALL);
        progressBar.setVisibility(View.VISIBLE);
        changeText(proText, "扫描中");
        ClingSdk.startScan(10 * 60 * 1000, o -> {
            if (o == null) return;
            ClingSdk.stopScan();
            progressBar.setVisibility(View.INVISIBLE);
            changeText(proText, "");
            showChooseDialog(o);
        });
    }

    @OnClick(R.id.equip_send_data)
    void initButtonSendData() {
        //同步结束
        if (miDataSynState.getData() != 2) {
            showTip("数据未同步");
            ClingSdk.loadDeviceData();
            return;
        }
        //未开始发送数据
        if (miSendState.getData() == 1) {
            showTip("数据正在发送中");
            return;
        }
        long userId=sp.getLong("userId",0);
        DataObservable.insertDataListInDb(userId, dataList,dayData).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                miSendState.setData(1);
            }

            @Override
            public void onNext(Integer integer) {
                dataList.clear();
                timeList.clear();
                dayData=null;
            }

            @Override
            public void onError(Throwable e) {
                if(e instanceof DbException){
                    showTip(((DbException) e).getWhat());
                }
                miSendState.setData(3);
            }

            @Override
            public void onComplete() {
                miSendState.setData(2);
            }
        });
    }

    //请求定位权限
    private void requestPermission() {
        int p = ContextCompat.checkSelfPermission(this, "Manifest.ACCESS_COARSE_LOCATION");
        if (p != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }
    }

    //请求开启蓝牙
    private boolean openBluetooth() {
        BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        if (bluetoothManager == null) return false;
        BluetoothAdapter mBluetoothAdapter = bluetoothManager.getAdapter();
        if (mBluetoothAdapter == null) return false;
        if (mBluetoothAdapter.isEnabled()) {
            return true;
        } else {
            return mBluetoothAdapter.enable();
        }
    }

    private void showChooseDialog(Object o) {
        List<BluetoothDeviceInfo> listInfo = castObj2List(o);
        String[] items = castList2Array(listInfo);
        AlertDialog.Builder builder = new AlertDialog.Builder(EquipActivity.this);
        builder.setTitle("选择设备");
        builder.setItems(items, (dialog, which) -> {
            bluetoothDeviceInfo = listInfo.get(which);
            BluetoothDevice bleDevice = bluetoothDeviceInfo.getmBleDevice();
            ClingSdk.registerDevice(REGISTER_CODE, bleDevice, onRegisterDeviceListener);
            dialog.dismiss();
            miDeviceConnect.setData(1);
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    //进行类型检查，返回需要的类型
    private List<BluetoothDeviceInfo> castObj2List(Object object) {
        List<BluetoothDeviceInfo> listInfo = new ArrayList<>();
        if (object instanceof List<?>) {
            for (Object obj : (List<?>) object) {
                if (obj instanceof BluetoothDeviceInfo) {
                    listInfo.add((BluetoothDeviceInfo) obj);
                }
            }
        }
        return listInfo;
    }

    //提取list中的元素，转化成需要在dialog中显示的string
    private String[] castList2Array(List<BluetoothDeviceInfo> list) {
        String[] items = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            BluetoothDeviceInfo bInfo = list.get(i);
            items[i] = bInfo.getmBleDevice().getName() + ":" + bInfo.getmRssi() + "db";
        }
        return items;
    }

    private void changeProgressbar(boolean b) {
        Disposable dis = Observable.just(b)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ib -> {
                    if (ib) progressBar.setVisibility(View.VISIBLE);
                    else progressBar.setVisibility(View.INVISIBLE);
                });
        compositeDisposable.add(dis);
    }

    private void changeText(TextView textView, String string) {
        Disposable dis = Observable.just(string)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(textView::setText);
        compositeDisposable.add(dis);
    }

    private void showTip(String str) {
        Snackbar bar = Snackbar.make(coordinatorLayout, str, Snackbar.LENGTH_SHORT);
        bar.show();
    }
}
