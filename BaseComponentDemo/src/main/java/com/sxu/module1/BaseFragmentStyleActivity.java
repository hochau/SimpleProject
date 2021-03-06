package com.sxu.module1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.sxu.basecomponent.activity.BaseActivity;

/*******************************************************************************
 * Description: 
 *
 * Author: Freeman
 *
 * Date: 2018/7/30
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/

public class BaseFragmentStyleActivity extends AppCompatActivity {

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_container_layout);

		Fragment fragment = BaseFragmentStyleFragment.getInstance(getIntent().getIntExtra("toolbarStyle", 0));
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction transaction = fm.beginTransaction();
		transaction.add(R.id.container_layout, fragment);
		transaction.commit();
	}

	public static void enter(Context context, int toolbarStyle) {
		Intent intent = new Intent(context, BaseFragmentStyleActivity.class);
		intent.putExtra("toolbarStyle", toolbarStyle);
		context.startActivity(intent);
	}
}
