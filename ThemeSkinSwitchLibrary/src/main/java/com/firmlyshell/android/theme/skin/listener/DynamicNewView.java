package com.firmlyshell.android.theme.skin.listener;

import java.util.List;

import android.view.View;

import com.firmlyshell.android.theme.skin.entity.DynamicAttr;


public interface DynamicNewView {
	void dynamicAddView(View view, List<DynamicAttr> pDAttrs);
}
