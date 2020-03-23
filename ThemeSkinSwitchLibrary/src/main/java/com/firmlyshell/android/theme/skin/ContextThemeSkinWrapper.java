package com.firmlyshell.android.theme.skin;

import android.content.Context;
import android.content.ContextWrapper;

import com.firmlyshell.android.theme.skin.listener.OnThemeSkinUpdate;


/**
 * 包裹Context 提供页面的主题皮肤管理
 */
public class ContextThemeSkinWrapper extends ContextWrapper implements OnThemeSkinUpdate {


    private ThemeSkinPairsManager mThemeSkinManager;


    public ContextThemeSkinWrapper(Context base) {
        super(base);
    }

    public ContextThemeSkinWrapper(Context base, ThemeSkinPairsManager mThemeSkinManager) {
        super(base);
        this.mThemeSkinManager = mThemeSkinManager;
    }


    public ThemeSkinPairsManager getThemeSkinManager() {
        return mThemeSkinManager;
    }

    public void setThemeSkinManager(ThemeSkinPairsManager mThemeSkinManager) {
        this.mThemeSkinManager = mThemeSkinManager;
    }

    @Override
    public void onThemeUpdate() {
        if (mThemeSkinManager != null) {
            mThemeSkinManager.onThemeUpdate();
        }
    }
}
