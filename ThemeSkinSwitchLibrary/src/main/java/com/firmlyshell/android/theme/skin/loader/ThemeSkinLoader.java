package com.firmlyshell.android.theme.skin.loader;


import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;

import com.firmlyshell.android.theme.skin.entity.StateColor;
import com.firmlyshell.android.theme.skin.listener.OnLoaderListener;

/**
 * 加载主题皮肤资源
 */
public interface ThemeSkinLoader {


    void loadThemeSkin(String skinPackagePath, final OnLoaderListener callback);

    void restoreDefaultThemeSkin();

    boolean isDefaultThemeSkin();

    int getColor(int resId);

    Drawable getDrawable(int resId);

    StateColor getStateColor(int resId);

    ColorStateList getColorStateList(int resId);

}
