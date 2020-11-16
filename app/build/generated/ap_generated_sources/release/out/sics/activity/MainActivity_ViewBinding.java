// Generated code from Butter Knife. Do not modify!
package sics.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.CompoundButton;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import sics.cling.R;

public class MainActivity_ViewBinding implements Unbinder {
  private MainActivity target;

  private View view2131230843;

  private View view2131230841;

  private View view2131230842;

  private View view2131230844;

  @UiThread
  public MainActivity_ViewBinding(MainActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public MainActivity_ViewBinding(final MainActivity target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.main_rb_home, "method 'initRg'");
    view2131230843 = view;
    ((CompoundButton) view).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton p0, boolean p1) {
        target.initRg(p0, p1);
      }
    });
    view = Utils.findRequiredView(source, R.id.main_rb_equip, "method 'initRg'");
    view2131230841 = view;
    ((CompoundButton) view).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton p0, boolean p1) {
        target.initRg(p0, p1);
      }
    });
    view = Utils.findRequiredView(source, R.id.main_rb_find, "method 'initRg'");
    view2131230842 = view;
    ((CompoundButton) view).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton p0, boolean p1) {
        target.initRg(p0, p1);
      }
    });
    view = Utils.findRequiredView(source, R.id.main_rb_person, "method 'initRg'");
    view2131230844 = view;
    ((CompoundButton) view).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton p0, boolean p1) {
        target.initRg(p0, p1);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    target = null;


    ((CompoundButton) view2131230843).setOnCheckedChangeListener(null);
    view2131230843 = null;
    ((CompoundButton) view2131230841).setOnCheckedChangeListener(null);
    view2131230841 = null;
    ((CompoundButton) view2131230842).setOnCheckedChangeListener(null);
    view2131230842 = null;
    ((CompoundButton) view2131230844).setOnCheckedChangeListener(null);
    view2131230844 = null;
  }
}
