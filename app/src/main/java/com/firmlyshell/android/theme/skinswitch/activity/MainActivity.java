package com.firmlyshell.android.theme.skinswitch.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.firmlyshell.android.theme.skinswitch.R;
import com.firmlyshell.android.theme.skinswitch.base.BaseFragmentActivity;
import com.firmlyshell.android.theme.skinswitch.fragment.ArticleListFragment;


public class MainActivity extends BaseFragmentActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initFragment();
	}

	private void initFragment() {
		FragmentManager fm = getSupportFragmentManager();
		Fragment fragment = fm.findFragmentById(R.id.fragment_container);
		if(fragment == null){
			fragment = ArticleListFragment.newInstance();
			fm.beginTransaction()
				.add(R.id.fragment_container, fragment)
				.commit();
		}
	}
}
