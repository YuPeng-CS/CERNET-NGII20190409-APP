// Generated code from Butter Knife. Do not modify!
package sics.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import java.lang.IllegalStateException;
import java.lang.Override;
import sics.cling.R;

public class FindActivity_ViewBinding implements Unbinder {
  private FindActivity target;

  private View view2131230803;

  private View view2131230804;

  private View view2131230808;

  private View view2131230805;

  @UiThread
  public FindActivity_ViewBinding(FindActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public FindActivity_ViewBinding(final FindActivity target, View source) {
    this.target = target;

    View view;
    target.lineChart = Utils.findRequiredViewAsType(source, R.id.find_line_chart, "field 'lineChart'", LineChart.class);
    target.barChart = Utils.findRequiredViewAsType(source, R.id.find_bar_chart, "field 'barChart'", BarChart.class);
    target.pieChart = Utils.findRequiredViewAsType(source, R.id.find_pie_chart, "field 'pieChart'", PieChart.class);
    view = Utils.findRequiredView(source, R.id.find_begin_time, "field 'beginTime' and method 'chooseTime'");
    target.beginTime = Utils.castView(view, R.id.find_begin_time, "field 'beginTime'", TextView.class);
    view2131230803 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.chooseTime(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.find_end_time, "field 'endTime' and method 'chooseTime'");
    target.endTime = Utils.castView(view, R.id.find_end_time, "field 'endTime'", TextView.class);
    view2131230804 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.chooseTime(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.find_section, "field 'sectionFind' and method 'chooseSection'");
    target.sectionFind = Utils.castView(view, R.id.find_section, "field 'sectionFind'", TextView.class);
    view2131230808 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.chooseSection();
      }
    });
    view = Utils.findRequiredView(source, R.id.find_find, "method 'beginFind'");
    view2131230805 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.beginFind();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    FindActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.lineChart = null;
    target.barChart = null;
    target.pieChart = null;
    target.beginTime = null;
    target.endTime = null;
    target.sectionFind = null;

    view2131230803.setOnClickListener(null);
    view2131230803 = null;
    view2131230804.setOnClickListener(null);
    view2131230804 = null;
    view2131230808.setOnClickListener(null);
    view2131230808 = null;
    view2131230805.setOnClickListener(null);
    view2131230805 = null;
  }
}
