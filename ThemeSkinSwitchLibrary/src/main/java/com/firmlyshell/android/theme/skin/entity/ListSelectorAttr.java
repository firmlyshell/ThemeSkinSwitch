package com.firmlyshell.android.theme.skin.entity;

import android.view.View;
import android.widget.AbsListView;

import com.firmlyshell.android.theme.skin.ThemeSkinManager;


public class ListSelectorAttr extends ThemeSkinAttr {

	@Override
	public void apply(View view) {
		if(view instanceof AbsListView){
			AbsListView tv = (AbsListView)view;
			
			if(RES_TYPE_NAME_COLOR.equals(attrValueTypeName)){
				tv.setSelector(ThemeSkinManager.getInstance().getColor(attrValueRefId));
			}else if(RES_TYPE_NAME_DRAWABLE.equals(attrValueTypeName)){
				tv.setSelector(ThemeSkinManager.getInstance().getDrawable(attrValueRefId));
			}
		}
	}
}
