package com.firmlyshell.android.theme.skin.entity;

import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.ListView;

import com.firmlyshell.android.theme.skin.ThemeSkinManager;


public class DividerAttr extends ThemeSkinAttr {

	public int dividerHeight = 1;
	
	@Override
	public void apply(View view) {
		if(view instanceof ListView){
			ListView tv = (ListView)view;
			if(RES_TYPE_NAME_COLOR.equals(attrValueTypeName)){
				int color = ThemeSkinManager.getInstance().getColor(attrValueRefId);
				ColorDrawable sage = new ColorDrawable(color);
				tv.setDivider(sage);
				tv.setDividerHeight(dividerHeight);
			}else if(RES_TYPE_NAME_DRAWABLE.equals(attrValueTypeName)){
				tv.setDivider(ThemeSkinManager.getInstance().getDrawable(attrValueRefId));
			}
		}
	}
}
