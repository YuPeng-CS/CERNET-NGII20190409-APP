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

public class HomeFragment_ViewBinding implements Unbinder {
  private HomeFragment target;

  private View view2131230820;

  private View view2131230822;

  private View view2131230821;

  @UiThread
  public HomeFragment_ViewBinding(final HomeFragment target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.home_heart, "method 'navToHeart'");
    view2131230820 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.navToHeart();
      }
    });
    view = Utils.findRequiredView(source, R.id.home_temp, "method 'navToSleep'");
    view2131230822 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.navToSleep();
      }
    });
    view = Utils.findRequiredView(source, R.id.home_run, "method 'navToRun'");
    view2131230821 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.navToRun();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    target = null;


    view2131230820.setOnClickListener(null);
    view2131230820 = null;
    view2131230822.setOnClickListener(null);
    view2131230822 = null;
    view2131230821.setOnClickListener(null);
    view2131230821 = null;
  }
}
