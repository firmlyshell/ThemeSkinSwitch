package com.firmlyshell.android.theme.skin.entity;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;

import com.firmlyshell.android.theme.skin.ThemeSkinManager;

public class BackgroundAttr extends ThemeSkinAttr {

	@Override
	public void apply(View view) {
		if (view != null) {
			if (RES_TYPE_NAME_COLOR.equals(attrValueTypeName)) {
				view.setBackgroundColor(ThemeSkinManager.getInstance().getColor(attrValueRefId));
				Log.i("attr", "_________________________________________________________");
				Log.i("attr", "apply as color");
			} else if (RES_TYPE_NAME_DRAWABLE.equals(attrValueTypeName)) {
				Drawable bg = ThemeSkinManager.getInstance().getDrawable(attrValueRefId);
				view.setBackground(bg);
				Log.i("attr", "_________________________________________________________");
				Log.i("attr", "apply as drawable");
				Log.i("attr", "bg.toString()  " + bg.toString());

				Log.i("attr", this.attrValueRefName + " 是否可变换状态? : " + bg.isStateful());
			}
		}
	}
}
