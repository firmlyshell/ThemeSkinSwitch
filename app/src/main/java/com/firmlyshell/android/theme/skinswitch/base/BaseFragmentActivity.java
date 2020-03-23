package com.firmlyshell.android.theme.skinswitch.base;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.firmlyshell.android.theme.skin.ThemeSkinInflaterFactory;
import com.firmlyshell.android.theme.skin.ThemeSkinManager;
import com.firmlyshell.android.theme.skin.ThemeSkinPairsManager;
import com.firmlyshell.android.theme.skin.entity.DynamicAttr;
import com.firmlyshell.android.theme.skin.listener.DynamicNewView;
import com.firmlyshell.android.theme.skin.listener.OnThemeSkinUpdate;

import java.lang.reflect.Field;
import java.util.List;


/**
 * Base Fragment Activity for development
 * 
 * <p>NOTICE:<br> 
 * You should extends from this if you want to do skin change
 * 
 * @author fengjun
 */
public class BaseFragmentActivity extends FragmentActivity implements OnThemeSkinUpdate, DynamicNewView {

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
        try {
            Field field = LayoutInflater.class.getDeclaredField("mFactorySet");
            field.setAccessible(true);
            field.setBoolean(getLayoutInflater(), false);
            
    		mSkinInflaterFactory = new ThemeSkinInflaterFactory(themeSkinManager);
    		getLayoutInflater().setFactory(mSkinInflaterFactory);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } 
		
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
		if(!isResponseOnSkinChanging) return;
//		mSkinInflaterFactory.applySkin();
	}
	
	@Override
	public void dynamicAddView(View view, List<DynamicAttr> pDAttrs) {
		themeSkinManager.dynamicAddSkinEnableView(this, view, pDAttrs);
	}
}
