package com.sxu.basecomponent.activity;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.sxu.basecomponent.R;
import com.sxu.basecomponent.uiwidget.NavigationBar;
import com.sxu.basecomponent.uiwidget.ToolbarEx;
import com.sxu.baselibrary.commonutils.DisplayUtil;
import com.sxu.baselibrary.commonutils.InputMethodUtil;
import com.sxu.baselibrary.commonutils.ViewBgUtil;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/*******************************************************************************
 * Description: Activity最基础的类, 为BaseActivity和BaseProgressActivity提供公共逻辑
 *
 * Author: Freeman
 *
 * Date: 2018/7/17
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/

public abstract class CommonActivity extends SwipeBackActivity {

	protected Activity context;
	protected ToolbarEx toolbar;
	protected FragmentManager fragmentManager;
	protected ViewGroup containerLayout;

	protected final int TOOL_BAR_STYLE_NONE = 0;
	protected final int TOOL_BAR_STYLE_NORMAL = 1;
	protected final int TOOL_BAR_STYLE_TRANSPARENT = 2;
	protected final int TOOL_BAR_STYLE_TRANSLUCENT = 3;

	protected int toolbarStyle = TOOL_BAR_STYLE_NONE;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
		fragmentManager = getSupportFragmentManager();

		// 设置沉浸式状态栏
//		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//		}
//		requestWindowFeature(Window.FEATURE_NO_TITLE);

		// 设置Activity的切换动画
		overridePendingTransition(R.anim.anim_translate_right_in_300, R.anim.anim_translate_left_out_300);
		if (savedInstanceState != null) {
			recreateActivity(savedInstanceState);
		}

		pretreatment();

		initLayout();

		if (containerLayout != null) {
			setContentView(containerLayout);
		}
	}

	/**
	 * 根据toolbar的样式返回不同的布局
	 */
	protected void initLayout() {
		switch (toolbarStyle) {
			case TOOL_BAR_STYLE_NORMAL:
				containerLayout = createNormalToolbarLayout();
				break;
			case TOOL_BAR_STYLE_TRANSPARENT:
				containerLayout = createTransparentToolbarLayout(true);
				break;
			case TOOL_BAR_STYLE_TRANSLUCENT:
				containerLayout = createTransparentToolbarLayout(false);
				break;
		}

		if (toolbar != null) {
			setSupportActionBar(toolbar);
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
			toolbar.setNavigationOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					finish();
				}
			});
		}
	}

	/**
	 * 预处理，加载布局之前添加相关逻辑，如设置window样式，重置切换动画，设置toolbar风格
	 */
	protected void pretreatment() {

	}

	/**
	 * 设置toolbar的样式
	 * @param style
	 */
	protected void setToolbarStyle(int style) {
		this.toolbarStyle = style;
	}

	/**
	 * 获取Activity加载的布局ID
	 * @return
	 */
	protected abstract int getLayoutResId();

	/**
	 * 获取布局中的所有控件
	 */
	protected abstract void getViews();

	/**
	 * Activity的逻辑处理过程
	 */
	protected abstract void initActivity();

	protected void recreateActivity(Bundle savedInstanceState) {

	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
	}

	private ViewGroup createNormalToolbarLayout() {
		LinearLayout containerLayout = new LinearLayout(this);
		containerLayout.setOrientation(LinearLayout.VERTICAL);
		toolbar = new ToolbarEx(this);
		containerLayout.addView(toolbar);
		return containerLayout;
	}

	private ViewGroup createTransparentToolbarLayout(boolean isTransparent) {
		FrameLayout containerLayout = new FrameLayout(this);
		toolbar = new ToolbarEx(this);
		toolbar.setChildViewStyle();
		if (isTransparent) {
			toolbar.setBackgroundColor(Color.TRANSPARENT);
		} else {
			ViewBgUtil.setShapeBg(toolbar, GradientDrawable.Orientation.TOP_BOTTOM, new int[] {
					ContextCompat.getColor(this, R.color.black_50), Color.TRANSPARENT}, 0);
		}
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
				DisplayUtil.dpToPx(56));
		containerLayout.addView(toolbar, params);

		return containerLayout;
	}

	@Override
	public void finish() {
		super.finish();
		InputMethodUtil.hideKeyboard(this);
		overridePendingTransition(R.anim.anim_translate_left_in_300, R.anim.anim_translate_right_out_300);
	}
}
