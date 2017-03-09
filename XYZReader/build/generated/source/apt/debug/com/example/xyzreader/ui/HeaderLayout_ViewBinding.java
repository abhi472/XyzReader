// Generated code from Butter Knife. Do not modify!
package com.example.xyzreader.ui;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.xyzreader.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class HeaderLayout_ViewBinding<T extends HeaderLayout> implements Unbinder {
  protected T target;

  @UiThread
  public HeaderLayout_ViewBinding(T target, View source) {
    this.target = target;

    target.title = Utils.findRequiredViewAsType(source, R.id.article_title, "field 'title'", TextView.class);
    target.subTitle = Utils.findRequiredViewAsType(source, R.id.article_byline, "field 'subTitle'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.title = null;
    target.subTitle = null;

    this.target = null;
  }
}
