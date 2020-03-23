package com.firmlyshell.android.theme.skinswitch.base;

import android.app.Activity;
import android.os.Bundle;

import android.view.View;

import com.firmlyshell.android.theme.skin.ThemeSkinInflaterFactory;
import com.firmlyshell.android.theme.skin.ThemeSkinManager;
import com.firmlyshell.android.theme.skin.ThemeSkinPairsManager;
import com.firmlyshell.android.theme.skin.entity.DynamicAttr;
import com.firmlyshell.android.theme.skin.listener.DynamicNewView;
import com.firmlyshell.android.theme.skin.listener.OnThemeSkinUpdate;

import java.util.List;


/**
 * Base Activity for development
 * 
 * <p>NOTICE:<br> 
 * You should extends from this if you what to do skin change
 * 
 * @author fengjun
 */
public class BaseActivity extends Activity implements OnThemeSkinUpdate, DynamicNewView {
	
	/**
	 * Whether response to skin changing after create
	 */
	private boolean isResponseOnSkinChanging = true;
	
	private ThemeSkinInflaterFactory mSkinInflaterFactory;
	private ThemeSkinPairsManager themeSkinManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		themeSkinManager = new ThemeSkinPairsManager();

		mSkinInflaterFactory = new ThemeSkinInflaterFactory(themeSkinManager);
		getLayoutInflater().setFactory(mSkinInflaterFactory);

	}
	
	@Override
	protected void onResume() {
		super.onResume();
		ThemeSkinManager.getInstance().attach(themeSkinManager);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		ThemeSkinManager.getInstance().detach(themeSkinManager);

	}
	
	/**
	 * dynamic add a skin view 
	 * 
	 * @param view
	 * @param attrName
	 * @param attrValueResId
	 */
	protected void dynamicAddSkinEnableView(View view, String attrName, int attrValueResId){
		themeSkinManager.dynamicAddSkinEnableView(this, view, attrName, attrValueResId);
	}
	
	protected void dynamicAddSkinEnableView(View view, List<DynamicAttr> pDAttrs){
		themeSkinManager.dynamicAddSkinEnableView(this, view, pDAttrs);
	}
	
	final protected void enableResponseOnSkinChanging(boolean enable){
		isResponseOnSkinChanging = enable;
	}

	@Override
	public void onThemeUpdate() {
		if(!isResponseOnSkinChanging){
			return;
		}
	}

	@Override
	public void dynamicAddView(View view, List<DynamicAttr> pDAttrs) {
		themeSkinManager.dynamicAddSkinEnableView(this, view, pDAttrs);
	}
}
