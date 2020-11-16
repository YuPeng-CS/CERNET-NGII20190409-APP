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

public class RegisterActivity_ViewBinding implements Unbinder {
  private RegisterActivity target;

  private View view2131230874;

  @UiThread
  public RegisterActivity_ViewBinding(RegisterActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public RegisterActivity_ViewBinding(final RegisterActivity target, View source) {
    this.target = target;

    View view;
    target.email = Utils.findRequiredViewAsType(source, R.id.register_edittext_username, "field 'email'", EditText.class);
    target.password = Utils.findRequiredViewAsType(source, R.id.register_edittext_password, "field 'password'", EditText.class);
    target.confirmPassword = Utils.findRequiredViewAsType(source, R.id.register_edittext_confirm_password, "field 'confirmPassword'", EditText.class);
    view = Utils.findRequiredView(source, R.id.register_button_register, "method 'register'");
    view2131230874 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.register();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    RegisterActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.email = null;
    target.password = null;
    target.confirmPassword = null;

    view2131230874.setOnClickListener(null);
    view2131230874 = null;
  }
}
