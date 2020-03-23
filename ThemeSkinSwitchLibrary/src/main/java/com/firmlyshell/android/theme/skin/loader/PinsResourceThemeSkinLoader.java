package com.firmlyshell.android.theme.skin.loader;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import com.firmlyshell.android.theme.skin.entity.StateColor;
import com.firmlyshell.android.theme.skin.listener.OnLoaderListener;
import com.firmlyshell.android.theme.skin.util.LogUtils;


/**
 * 加载资源文件中已有的不同主题皮肤资源，资源不能动态更新。适用于测试或者单独实现夜间模式的资源加载
 */
public class PinsResourceThemeSkinLoader extends AbstractThemeSkinLoader {


    private String sourcesSuffix;

    private Context context;
    private boolean isDefaultSkin;
    private String skinPackageName;


    public PinsResourceThemeSkinLoader(Context context) {
        this.context = context;
        sourcesSuffix = "";
        skinPackageName = context.getPackageName();
    }

    @Override
    public void loadThemeSkin(String skinPackagePath, OnLoaderListener callback) {
        callback.onStart();
        sourcesSuffix = skinPackagePath;
        callback.onSuccess();
    }

    @Override
    public void restoreDefaultThemeSkin() {
        sourcesSuffix = "";
        isDefaultSkin = true;
    }

    @Override
    public boolean isDefaultThemeSkin() {
        return isDefaultSkin;
    }

    @Override
    public int getColor(int resId) {

        int originColor = context.getResources().getColor(resId);
        if (isDefaultSkin) {
            return originColor;
        }

        String resName = context.getResources().getResourceEntryName(resId) + sourcesSuffix;

        int trueResId = context.getResources().getIdentifier(resName, "color", skinPackageName);
        int trueColor = 0;

        try {
            trueColor = context.getResources().getColor(trueResId);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            trueColor = originColor;
        }

        return trueColor;
    }

    @Override
    public Drawable getDrawable(int resId) {
        Drawable originDrawable = context.getResources().getDrawable(resId);
        if (isDefaultSkin) {
            return originDrawable;
        }
        String resName = context.getResources().getResourceEntryName(resId) + sourcesSuffix;

        int trueResId = context.getResources().getIdentifier(resName, "drawable", skinPackageName);

        Drawable trueDrawable = null;
        try {
            LogUtils.e("getDrawable", "SDK_INT = " + android.os.Build.VERSION.SDK_INT);
            if (android.os.Build.VERSION.SDK_INT < 22) {
                trueDrawable = context.getResources().getDrawable(trueResId);
            } else {
                trueDrawable = context.getResources().getDrawable(trueResId, null);
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            trueDrawable = originDrawable;
        }

        return trueDrawable;
    }

    @Override
    public StateColor getStateColor(int resId) {
        StateColor stateColor = null;

        boolean isExtendSkin = true;

        if (isDefaultSkin) {
            isExtendSkin = false;
        }
        String resName = context.getResources().getResourceEntryName(resId) + sourcesSuffix;
        if (isExtendSkin) {
            int trueResId = context.getResources().getIdentifier(resName, "color", skinPackageName);
            LogUtils.e("attr1", "trueResId = " + trueResId);
            if (trueResId != 0) {
                stateColor = getStateColor(context.getResources(), resName, trueResId);
            }
        }
        if (stateColor == null) {
            stateColor = getStateColor(context.getResources(), resName, resId);
        }

        return stateColor;
    }


    @Override
    public ColorStateList getColorStateList(int resId) {
        LogUtils.e("attr1", "getColorStateList");

        boolean isExtendSkin = true;

        if (isDefaultSkin) {
            isExtendSkin = false;
        }

        String resName = context.getResources().getResourceEntryName(resId);
        LogUtils.e("attr1", "resName = " + resName);
        if (isExtendSkin) {
            LogUtils.e("attr1", "isExtendSkin");
            int trueResId = context.getResources().getIdentifier(resName + sourcesSuffix, "color", skinPackageName);
            LogUtils.e("attr1", "trueResId = " + trueResId);
            ColorStateList trueColorList = null;
            if (trueResId == 0) { // 如果皮肤包没有复写该资源，但是需要判断是否是ColorStateList
                try {
                    ColorStateList originColorList = context.getResources().getColorStateList(resId);
                    return originColorList;
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                    LogUtils.e("resName = " + resName + " NotFoundException : " + e.getMessage());
                }
            } else {
                try {
                    trueColorList = context.getResources().getColorStateList(trueResId);
                    LogUtils.e("attr1", "trueColorList = " + trueColorList);
                    return trueColorList;
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                    LogUtils.w("resName = " + resName + " NotFoundException :" + e.getMessage());
                }
            }
        } else {
            try {
                ColorStateList originColorList = context.getResources().getColorStateList(resId);
                return originColorList;
            } catch (Resources.NotFoundException e) {
                e.printStackTrace();
                LogUtils.w("resName = " + resName + " NotFoundException :" + e.getMessage());
            }

        }

        int[][] states = new int[1][1];
        return new ColorStateList(states, new int[]{context.getResources().getColor(resId)});
    }
}
