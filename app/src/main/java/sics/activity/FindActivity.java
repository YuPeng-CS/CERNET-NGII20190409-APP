package sics.activity;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;
import sics.bean.CommonData;
import sics.bean.DayData;
import sics.bean.PairData;
import sics.cling.R;
import sics.observable.DataObservable;
import sics.tool.MinuteDataMini;
import sics.tool.TimeTool;

public class FindActivity extends BaseActivity {

    @BindView(R.id.find_line_chart)
    LineChart lineChart;
    @BindView(R.id.find_bar_chart)
    BarChart barChart;
    @BindView(R.id.find_pie_chart)
    PieChart pieChart;

    @BindView(R.id.find_begin_time)
    TextView beginTime;
    @BindView(R.id.find_end_time)
    TextView endTime;
    @BindView(R.id.find_section)
    TextView sectionFind;

    private Calendar beginDateTime = Calendar.getInstance();
    private Calendar endDateTime = Calendar.getInstance();
    private int[] sections = new int[2];
    //0：心率 1：步数 3：睡眠
    private int kind = 0;
    private String[] describe = new String[]{"心率", "温度", "步数"};
    private String[] unit = new String[]{"/分钟", "摄氏度", "千步/天"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        kind = getIntent().getIntExtra("kind", 0);
        initData();
        initChart();
    }

    private void initData() {
        beginDateTime.setTimeInMillis(TimeTool.getTimeBegin(System.currentTimeMillis()));
        endDateTime.setTimeInMillis(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        beginTime.setText(sdf.format(beginDateTime.getTime()));
        endTime.setText(sdf.format(endDateTime.getTime()));
        Arrays.fill(sections, 0);
    }

    private void initChart() {
        List<CommonData> list = new ArrayList<>();
        list.add(new CommonData(0, 0));
        setDataToLineChart(lineChart, list);
        setDataToBarChart(barChart, list);
        setDataToPieChart(pieChart, list, 10);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_find;
    }


    @OnClick({R.id.find_begin_time, R.id.find_end_time})
    public void chooseTime(View textView) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(getBaseContext()).inflate(R.layout.dialog_time, null, false);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        NumberPicker year = view.findViewById(R.id.time_year);
        builderNum(year, 1870, calendar.get(Calendar.YEAR), "年", calendar.get(Calendar.YEAR));
        NumberPicker month = view.findViewById(R.id.time_month);
        builderNum(month, 1, 12, "月", calendar.get(Calendar.MONTH) + 1);
        NumberPicker day = view.findViewById(R.id.time_day);
        builderNum(day, 1, 31, "日", calendar.get(Calendar.DAY_OF_MONTH));
        NumberPicker hour = view.findViewById(R.id.time_hour);
        builderNum(hour, 0, 23, "时", calendar.get(Calendar.HOUR_OF_DAY));
        NumberPicker minute = view.findViewById(R.id.time_minute);
        builderNum(minute, 0, 59, "分", calendar.get(Calendar.MINUTE));
        builder.setView(view);
        builder.setPositiveButton("确定", (dialog, which) -> {
            Calendar cal = Calendar.getInstance();
            cal.set(year.getValue(), month.getValue() - 1, day.getValue(), hour.getValue(), minute.getValue());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
            String str = sdf.format(cal.getTime());
            switch (textView.getId()) {
                case R.id.find_begin_time:
                    beginDateTime.set(year.getValue(), month.getValue() - 1, day.getValue(), hour.getValue(), minute.getValue());
                    beginTime.post(() -> beginTime.setText(str));
                    break;
                case R.id.find_end_time:
                    endDateTime.set(year.getValue(), month.getValue() - 1, day.getValue(), hour.getValue(), minute.getValue());
                    endTime.post(() -> endTime.setText(str));
                    break;
            }
        }).setNegativeButton("取消", (dialog, which) -> {
        });
        builder.create().show();
    }

    private void builderNum(NumberPicker picker, int min, int max, String after, int value) {
        String[] strings = new String[max - min + 1];
        for (int i = 0; i < strings.length; i++) strings[i] = min + i + after;
        picker.setMinValue(min);
        picker.setMaxValue(max);
        picker.setDisplayedValues(strings);
        picker.setValue(value);
    }

    @OnClick(R.id.find_section)
    void chooseSection() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(getBaseContext()).inflate(R.layout.dialog_section, null, false);
        builder.setView(view);
        NumberPicker begin = view.findViewById(R.id.section_begin);
        NumberPicker end = view.findViewById(R.id.section_end);
        switch (kind) {
            case 0:
                builderNum(begin, 0, 300, "/分钟", 0);
                builderNum(end, 0, 300, "/分钟", 120);
                break;
            case 1:
                builderNum(begin, 14, 46, "摄氏度", 30);
                builderNum(end, 14, 46, "摄氏度", 33);
                break;
            case 2:
                builderNum(begin, 0, 100, "千步/天", 0);
                builderNum(end, 0, 100, "千步／天", 3);
                break;
        }

        builder.setPositiveButton("确定", (dialog, which) -> {
            sections[0] = begin.getValue();
            sections[1] = end.getValue();
            String str = String.format(Locale.CHINA, "%d-%d %s", begin.getValue(), end.getValue(), unit[kind]);
            sectionFind.post(() -> {
                sectionFind.setText(str);
            });
            dialog.dismiss();
        });
        builder.setNegativeButton("取消", (dialog, which) -> {
            dialog.dismiss();
        });
        builder.create().show();
    }


    @OnClick(R.id.find_find)
    void beginFind() {
        Timestamp beginTime = new Timestamp(beginDateTime.getTimeInMillis());
        Timestamp endTime = new Timestamp(endDateTime.getTimeInMillis());
        long userId = sp.getLong("userId", 0);
        switch (kind) {
            case 0:
                initHeartRate(userId, beginTime, endTime);
                break;
            case 1:
                initTemp(userId, beginTime, endTime);
                break;
            case 2:
                initSteps(userId, beginTime, endTime);
                break;

        }
    }

    public void initHeartRate(long userId, Timestamp beginTime, Timestamp endTime) {
        Disposable dis = DataObservable
                .selectMinuteHeartListFromDb(userId, beginTime, endTime,sections[0],sections[1])
                .subscribe(result -> {
                    setDataToLineChart(lineChart, result);
                    setDataToBarChart(barChart, result);
                    setDataToPieChart(pieChart, result, 10);
                }, this::onError, this::onComplete, this::onSubscribe);
        compositeDisposable.add(dis);
    }

    public void initTemp(long userId, Timestamp beginTime, Timestamp endTime) {
        Disposable dis = DataObservable
                .selectDaySingleListFromDb(userId, DayData.TEMP, beginTime, endTime,sections[0],sections[1])
                .subscribe(result -> {
                    setDataToLineChart(lineChart, result);
                    setDataToBarChart(barChart, result);
                    setDataToPieChart(pieChart, result, 1);
                }, this::onError, this::onComplete, this::onSubscribe);
        compositeDisposable.add(dis);
    }

    public void initSteps(long userId, Timestamp beginTime, Timestamp endTime) {
        Disposable dis = DataObservable
                .selectDaySingleListFromDb(userId, DayData.STEPS, beginTime, endTime,sections[0]*1000,sections[1]*1000)
                .subscribe(result -> {
                    setDataToLineChart(lineChart, result);
                    setDataToBarChart(barChart, result);
                    setDataToPieChart(pieChart, result, 1000);
                }, this::onError, this::onComplete, this::onSubscribe);
        compositeDisposable.add(dis);
    }

    private void setDataToLineChart(LineChart line, List<CommonData> arrayList) {
        if (arrayList == null || arrayList.size() == 0) {
            return;
        }
        ArrayList<Entry> list = new ArrayList<>();
        for (CommonData commonData : arrayList) {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(commonData.getTime());
            list.add(new Entry(cal.get(Calendar.DAY_OF_MONTH) * 24 + cal.get(Calendar.HOUR_OF_DAY), commonData.getData()));
        }
        LineDataSet lineDataSet = new LineDataSet(list, describe[kind]);
        setLineChart(lineDataSet);
        LineData lineData = new LineData(lineDataSet);
        line.setData(lineData);
        line.getDescription().setEnabled(false);//隐藏右下角英文
        setXAxis(line.getXAxis());
        setYAxis(line.getAxisLeft(), line.getAxisRight());
        line.invalidate();
    }

    private void setLineChart(LineDataSet lineDataSet) {
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineDataSet.setCubicIntensity(0.1f);
    }

    private void setXAxis(XAxis xAxis) {
        xAxis.setEnabled(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter((v, axisBase) -> {
            int data = (int) v;
            return data / 24 + "日" + data % 24 + "时";
        });
    }

    private void setYAxis(YAxis yAxis, YAxis yAxis2) {
        yAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        yAxis.setTextSize(10f);
        yAxis.setAxisMinimum(0);
        yAxis2.setEnabled(false);
    }

    private void setDataToBarChart(BarChart barChart, List<CommonData> arrayList) {
        if (arrayList == null || arrayList.size() == 0) return;
        List<BarEntry> list = new ArrayList<>();
        for (CommonData commonData : arrayList) {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(commonData.getTime());
            list.add(new BarEntry(cal.get(Calendar.DAY_OF_MONTH) * 24 + cal.get(Calendar.HOUR_OF_DAY), commonData.getData()));
        }

        BarDataSet barDataSet = new BarDataSet(list, describe[kind]);
        barDataSet.setDrawValues(true);
        barDataSet.setColors(Color.RED, Color.BLUE, Color.YELLOW, Color.CYAN);//设置各个数据的颜色
        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);

        barChart.getDescription().setEnabled(false);
        barChart.getXAxis().setGranularity(1f);
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.getXAxis().setValueFormatter((v, axisBase) -> {
            int data = (int) v;
            return data / 24 + "日" + data % 24 + "时";
        });
        barChart.getAxisRight().setEnabled(false);
        barChart.invalidate();
    }

    private void setDataToPieChart(PieChart pieChart, List<CommonData> arrayList, int interval) {
        if (arrayList == null || arrayList.size() == 0) {
            return;
        }
        List<PairData> list = MinuteDataMini.getNumList(arrayList, interval);
        List<PieEntry> pieEntryList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            int x = list.get(i).getX();
            String str = String.format(Locale.CHINA, "%d-%d", x, x + interval);
            pieEntryList.add(new PieEntry(list.get(i).getY(), str));
        }
        PieDataSet pieDataSet = new PieDataSet(pieEntryList, describe[kind]);
        pieDataSet.setColors(getLotOfColor());
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.invalidate();
    }

    private List<Integer> getLotOfColor() {
        ArrayList<Integer> colors = new ArrayList<>();
        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);
        colors.add(ColorTemplate.getHoloBlue());
        return colors;
    }
}