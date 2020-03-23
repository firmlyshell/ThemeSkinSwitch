package com.firmlyshell.android.theme.skin.entity;

import android.view.View;
import android.widget.TextView;

import com.firmlyshell.android.theme.skin.ThemeSkinManager;
import com.firmlyshell.android.theme.skin.util.LogUtils;

public class TextColorAttr extends ThemeSkinAttr {

	@Override
	public void apply(View view) {
		if(view instanceof TextView){
			TextView tv = (TextView)view;
			if(RES_TYPE_NAME_COLOR.equals(attrValueTypeName)){
				LogUtils.e("attr1", "TextColorAttr");
				tv.setTextColor(ThemeSkinManager.getInstance().convertToColorStateList(attrValueRefId));
			}
		}
	}
}
