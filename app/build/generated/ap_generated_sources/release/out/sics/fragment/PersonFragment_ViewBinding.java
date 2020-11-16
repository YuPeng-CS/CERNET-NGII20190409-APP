// Generated code from Butter Knife. Do not modify!
package sics.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import sics.cling.R;

public class PersonFragment_ViewBinding implements Unbinder {
  private PersonFragment target;

  private View view2131230864;

  private View view2131230869;

  private View view2131230867;

  @UiThread
  public PersonFragment_ViewBinding(final PersonFragment target, View source) {
    this.target = target;

    View view;
    target.rg = Utils.findRequiredViewAsType(source, R.id.person_sex, "field 'rg'", RadioGroup.class);
    target.iv = Utils.findRequiredViewAsType(source, R.id.person_img, "field 'iv'", ImageView.class);
    target.username = Utils.findRequiredViewAsType(source, R.id.person_name, "field 'username'", TextView.class);
    target.email = Utils.findRequiredViewAsType(source, R.id.person_email, "field 'email'", TextView.class);
    target.age = Utils.findRequiredViewAsType(source, R.id.person_age, "field 'age'", EditText.class);
    target.nickname = Utils.findRequiredViewAsType(source, R.id.person_nickname, "field 'nickname'", EditText.class);
    view = Utils.findRequiredView(source, R.id.person_man, "method 'initRg'");
    view2131230864 = view;
    ((CompoundButton) view).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton p0, boolean p1) {
        target.initRg(p0, p1);
      }
    });
    view = Utils.findRequiredView(source, R.id.person_woman, "method 'initRg'");
    view2131230869 = view;
    ((CompoundButton) view).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton p0, boolean p1) {
        target.initRg(p0, p1);
      }
    });
    view = Utils.findRequiredView(source, R.id.person_save, "method 'initSave'");
    view2131230867 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.initSave();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    PersonFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.rg = null;
    target.iv = null;
    target.username = null;
    target.email = null;
    target.age = null;
    target.nickname = null;

    ((CompoundButton) view2131230864).setOnCheckedChangeListener(null);
    view2131230864 = null;
    ((CompoundButton) view2131230869).setOnCheckedChangeListener(null);
    view2131230869 = null;
    view2131230867.setOnClickListener(null);
    view2131230867 = null;
  }
}
