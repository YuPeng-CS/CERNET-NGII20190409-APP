// Generated code from Butter Knife. Do not modify!
package sics.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import sics.cling.R;

public class EquipFragment_ViewBinding implements Unbinder {
  private EquipFragment target;

  private View view2131230783;

  @UiThread
  public EquipFragment_ViewBinding(final EquipFragment target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.equip_add_watch, "method 'navToEquip'");
    view2131230783 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.navToEquip();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    target = null;


    view2131230783.setOnClickListener(null);
    view2131230783 = null;
  }
}
