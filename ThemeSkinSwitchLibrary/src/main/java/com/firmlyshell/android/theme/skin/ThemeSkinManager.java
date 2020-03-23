package com.firmlyshell.android.theme.skin;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;

import com.firmlyshell.android.theme.skin.config.ThemeSkinConfig;
import com.firmlyshell.android.theme.skin.entity.StateColor;
import com.firmlyshell.android.theme.skin.listener.OnLoaderListener;
import com.firmlyshell.android.theme.skin.listener.OnThemeSkinLoader;
import com.firmlyshell.android.theme.skin.listener.OnThemeSkinUpdate;
import com.firmlyshell.android.theme.skin.loader.AssetsResourceThemeSkinLoader;
import com.firmlyshell.android.theme.skin.loader.ThemeSkinLoader;


/**
 * Skin Manager Instance
 */
public class ThemeSkinManager implements OnThemeSkinLoader {

    private static Object synchronizedLock = new Object();
    private static ThemeSkinManager instance;

    private List<OnThemeSkinUpdate> skinObservers;
    private Context context;
    private ThemeSkinLoader mThemeSkinLoader;


    private ThemeSkinManager() {
    }

    /**
     * return a global static instance of {@link ThemeSkinManager}
     *
     * @return
     */
    public static ThemeSkinManager getInstance() {
        if (instance == null) {
            synchronized (synchronizedLock) {
                if (instance == null) {
                    instance = new ThemeSkinManager();
                }
            }
        }
        return instance;
    }


    public void init(Context ctx) {
        context = ctx.getApplicationContext();
        mThemeSkinLoader = new AssetsResourceThemeSkinLoader(context, "");

    }

    public void init(Context ctx, ThemeSkinLoader themeSkinLoader) {
        context = ctx.getApplicationContext();
        mThemeSkinLoader = themeSkinLoader;
    }


    public void restoreDefaultTheme() {
        ThemeSkinConfig.saveSkinPath(context, ThemeSkinConfig.DEFAULT_SKIN);
        mThemeSkinLoader.restoreDefaultThemeSkin();
        notifySkinUpdate();
    }

    public void load() {
        String skin = ThemeSkinConfig.getCustomSkinPath(context);
        load(skin, new OnLoaderListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess() {
                notifySkinUpdate();
            }

            @Override
            public void onFailed() {

            }
        });
    }

    public void load(String skinPath, OnLoaderListener callback) {
        mThemeSkinLoader.loadThemeSkin(skinPath, callback);
    }


    @Override
    public void attach(OnThemeSkinUpdate observer) {
        if (skinObservers == null) {
            skinObservers = new ArrayList<OnThemeSkinUpdate>();
        }
        if (!skinObservers.contains(skinObservers)) {
            skinObservers.add(observer);
        }
    }

    @Override
    public void detach(OnThemeSkinUpdate observer) {
        if (skinObservers == null) return;
        if (skinObservers.contains(observer)) {
            skinObservers.remove(observer);
        }
    }

    @Override
    public void notifySkinUpdate() {
        if (skinObservers == null) return;
        for (OnThemeSkinUpdate observer : skinObservers) {
            observer.onThemeUpdate();
        }
    }


    public boolean isExternalSkin() {
        return mThemeSkinLoader.isDefaultThemeSkin();
    }


    public int getColor(int resId) {
        return mThemeSkinLoader.getColor(resId);
    }


    public Drawable getDrawable(int resId) {
        return mThemeSkinLoader.getDrawable(resId);
    }


    public StateColor getStateColor(int resId) {
        return mThemeSkinLoader.getStateColor(resId);
    }

    public ColorStateList convertToColorStateList(int resId) {
        return mThemeSkinLoader.getColorStateList(resId);
    }
}