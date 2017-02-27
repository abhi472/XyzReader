// Generated code from Butter Knife. Do not modify!
package com.example.xyzreader.ui;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.xyzreader.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ArticleDetailFragment_ViewBinding<T extends ArticleDetailFragment> implements Unbinder {
  protected T target;

  @UiThread
  public ArticleDetailFragment_ViewBinding(T target, View source) {
    this.target = target;

    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.collapsingToolbar = Utils.findRequiredViewAsType(source, R.id.collapsing_toolbar, "field 'collapsingToolbar'", CollapsingToolbarLayout.class);
    target.articleTitle = Utils.findRequiredViewAsType(source, R.id.article_title, "field 'articleTitle'", TextView.class);
    target.articleByline = Utils.findRequiredViewAsType(source, R.id.article_byline, "field 'articleByline'", TextView.class);
    target.metaBar = Utils.findRequiredViewAsType(source, R.id.meta_bar, "field 'metaBar'", LinearLayout.class);
    target.appbar = Utils.findRequiredViewAsType(source, R.id.appbar, "field 'appbar'", AppBarLayout.class);
    target.articleBody = Utils.findRequiredViewAsType(source, R.id.article_body, "field 'articleBody'", TextView.class);
    target.scroll = Utils.findRequiredViewAsType(source, R.id.scroll, "field 'scroll'", NestedScrollView.class);
    target.fab = Utils.findRequiredViewAsType(source, R.id.fab, "field 'fab'", FloatingActionButton.class);
    target.mainContent = Utils.findRequiredViewAsType(source, R.id.main_content, "field 'mainContent'", CoordinatorLayout.class);
    target.image = Utils.findRequiredViewAsType(source, R.id.image, "field 'image'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.toolbar = null;
    target.collapsingToolbar = null;
    target.articleTitle = null;
    target.articleByline = null;
    target.metaBar = null;
    target.appbar = null;
    target.articleBody = null;
    target.scroll = null;
    target.fab = null;
    target.mainContent = null;
    target.image = null;

    this.target = null;
  }
}
