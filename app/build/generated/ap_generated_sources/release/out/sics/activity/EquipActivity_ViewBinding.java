// Generated code from Butter Knife. Do not modify!
package sics.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import sics.cling.R;

public class EquipActivity_ViewBinding implements Unbinder {
  private EquipActivity target;

  private View view2131230793;

  private View view2131230792;

  @UiThread
  public EquipActivity_ViewBinding(EquipActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public EquipActivity_ViewBinding(final EquipActivity target, View source) {
    this.target = target;

    View view;
    target.watch = Utils.findRequiredViewAsType(source, R.id.equip_state_watch, "field 'watch'", TextView.class);
    view = Utils.findRequiredView(source, R.id.equip_start_scan, "field 'startScan' and method 'initButtonStartScan'");
    target.startScan = Utils.castView(view, R.id.equip_start_scan, "field 'startScan'", Button.class);
    view2131230793 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.initButtonStartScan();
      }
    });
    target.coordinatorLayout = Utils.findRequiredViewAsType(source, R.id.equip_tip, "field 'coordinatorLayout'", CoordinatorLayout.class);
    target.heart = Utils.findRequiredViewAsType(source, R.id.equip_data_heart, "field 'heart'", TextView.class);
    target.temp = Utils.findRequiredViewAsType(source, R.id.equip_data_temp, "field 'temp'", TextView.class);
    target.step = Utils.findRequiredViewAsType(source, R.id.equip_data_step, "field 'step'", TextView.class);
    target.cal = Utils.findRequiredViewAsType(source, R.id.equip_data_cal, "field 'cal'", TextView.class);
    target.bp = Utils.findRequiredViewAsType(source, R.id.equip_data_bp, "field 'bp'", TextView.class);
    target.hp = Utils.findRequiredViewAsType(source, R.id.equip_data_hp, "field 'hp'", TextView.class);
    target.progressBar = Utils.findRequiredViewAsType(source, R.id.equip_progress, "field 'progressBar'", ProgressBar.class);
    target.proText = Utils.findRequiredViewAsType(source, R.id.equip_progress_text, "field 'proText'", TextView.class);
    view = Utils.findRequiredView(source, R.id.equip_send_data, "method 'initButtonSendData'");
    view2131230792 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.initButtonSendData();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    EquipActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.watch = null;
    target.startScan = null;
    target.coordinatorLayout = null;
    target.heart = null;
    target.temp = null;
    target.step = null;
    target.cal = null;
    target.bp = null;
    target.hp = null;
    target.progressBar = null;
    target.proText = null;

    view2131230793.setOnClickListener(null);
    view2131230793 = null;
    view2131230792.setOnClickListener(null);
    view2131230792 = null;
  }
}
