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

public class FindFragment_ViewBinding implements Unbinder {
  private FindFragment target;

  private View view2131230811;

  private View view2131230812;

  private View view2131230815;

  private View view2131230816;

  private View view2131230813;

  private View view2131230814;

  @UiThread
  public FindFragment_ViewBinding(final FindFragment target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.frag_find_heart, "method 'navToWeb'");
    view2131230811 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.navToWeb(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.frag_find_heart1, "method 'navToWeb'");
    view2131230812 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.navToWeb(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.frag_find_sleep, "method 'navToWeb'");
    view2131230815 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.navToWeb(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.frag_find_sleep1, "method 'navToWeb'");
    view2131230816 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.navToWeb(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.frag_find_run, "method 'navToWeb'");
    view2131230813 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.navToWeb(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.frag_find_run1, "method 'navToWeb'");
    view2131230814 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.navToWeb(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    target = null;


    view2131230811.setOnClickListener(null);
    view2131230811 = null;
    view2131230812.setOnClickListener(null);
    view2131230812 = null;
    view2131230815.setOnClickListener(null);
    view2131230815 = null;
    view2131230816.setOnClickListener(null);
    view2131230816 = null;
    view2131230813.setOnClickListener(null);
    view2131230813 = null;
    view2131230814.setOnClickListener(null);
    view2131230814 = null;
  }
}
