// Generated code from Butter Knife. Do not modify!
package sics.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.EditText;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import sics.cling.R;

public class LoginActivity_ViewBinding implements Unbinder {
  private LoginActivity target;

  private View view2131230836;

  private View view2131230837;

  @UiThread
  public LoginActivity_ViewBinding(LoginActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public LoginActivity_ViewBinding(final LoginActivity target, View source) {
    this.target = target;

    View view;
    target.email = Utils.findRequiredViewAsType(source, R.id.login_edittext_username, "field 'email'", EditText.class);
    target.password = Utils.findRequiredViewAsType(source, R.id.login_edittext_password, "field 'password'", EditText.class);
    view = Utils.findRequiredView(source, R.id.login_button_login, "method 'login'");
    view2131230836 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.login(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.login_button_register, "method 'navToRegister'");
    view2131230837 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.navToRegister();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    LoginActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.email = null;
    target.password = null;

    view2131230836.setOnClickListener(null);
    view2131230836 = null;
    view2131230837.setOnClickListener(null);
    view2131230837 = null;
  }
}
