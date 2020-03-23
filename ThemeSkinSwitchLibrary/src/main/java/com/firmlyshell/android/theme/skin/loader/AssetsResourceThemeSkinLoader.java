package com.firmlyshell.android.theme.skin.loader;


import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.TypedValue;

import com.firmlyshell.android.theme.skin.config.ThemeSkinConfig;
import com.firmlyshell.android.theme.skin.entity.StateColor;
import com.firmlyshell.android.theme.skin.listener.OnLoaderListener;
import com.firmlyshell.android.theme.skin.util.LogUtils;

import java.io.File;
import java.lang.reflect.Method;


/**
 * 单独资源包加载，可以实现动态加载皮肤
 */
public class AssetsResourceThemeSkinLoader extends AbstractThemeSkinLoader {


    private Context context;
    private String skinPackageName;
    private Resources mResources;
    private String skinPath;
    private boolean isDefaultSkin = false;




    public AssetsResourceThemeSkinLoader(Context context, String skinPath) {
        this.context = context;
        this.skinPath = skinPath;
    }


    public String getSkinPath() {
        return skinPath;
    }


    @Override
    public void restoreDefaultThemeSkin() {
        isDefaultSkin = true;
        mResources = null;
    }

    /**
     * whether the skin being used is from external .skin file
     *
     * @return is external skin = true
     */
    public boolean isDefaultThemeSkin() {
        return !isDefaultSkin && mResources != null;
    }

    /**
     * Load resources from apk in asyc task
     *
     * @param skinPackagePath path of skin apk
     * @param callback        callback to notify user
     */
    public void loadThemeSkin(String skinPackagePath, final OnLoaderListener callback) {
        LoadAsyncTask loadAsyncTask = new LoadAsyncTask(callback);
        skinPath = skinPackagePath;
        loadAsyncTask.execute(skinPackagePath);
    }

    /**
     *
     */
    public class LoadAsyncTask extends AsyncTask<String, Void, Resources> {

        OnLoaderListener callback;

        public LoadAsyncTask(OnLoaderListener callback) {
            this.callback = callback;
        }

        @Override
        protected void onPreExecute() {
            if (callback != null) {
                callback.onStart();
            }
        }

        @Override
        public Resources doInBackground(String... params) {
            try {
                if (params.length == 1) {
                    String skinPkgPath = params[0];

                    File file = new File(skinPkgPath);
                    if (file == null || !file.exists()) {
                        return null;
                    }

                    PackageManager mPm = context.getPackageManager();
                    PackageInfo mInfo = mPm.getPackageArchiveInfo(skinPkgPath, PackageManager.GET_ACTIVITIES);
                    skinPackageName = mInfo.packageName;

                    AssetManager assetManager = AssetManager.class.newInstance();
                    Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
                    addAssetPath.invoke(assetManager, skinPkgPath);

                    Resources superRes = context.getResources();
                    Resources skinResource = new Resources(assetManager, superRes.getDisplayMetrics(), superRes.getConfiguration());

                    ThemeSkinConfig.saveSkinPath(context, skinPkgPath);

                    skinPath = skinPkgPath;
                    isDefaultSkin = false;
                    return skinResource;
                }
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }


        protected void onPostExecute(Resources result) {
            mResources = result;

            if (mResources != null) {
                if (callback != null) callback.onSuccess();
            } else {
                isDefaultSkin = true;
                if (callback != null) callback.onFailed();
            }
        }
    }

    public int getColor(int resId) {
        int originColor = context.getResources().getColor(resId);
        if (mResources == null || isDefaultSkin) {
            return originColor;
        }

        String resName = context.getResources().getResourceEntryName(resId);

        int trueResId = mResources.getIdentifier(resName, "color", skinPackageName);
        int trueColor = 0;

        try {
            trueColor = mResources.getColor(trueResId);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            trueColor = originColor;
        }

        return trueColor;
    }

    @Override
    public Drawable getDrawable(int resId) {
        Drawable originDrawable = context.getResources().getDrawable(resId);
        if (mResources == null || isDefaultSkin) {
            return originDrawable;
        }
        String resName = context.getResources().getResourceEntryName(resId);

        int trueResId = mResources.getIdentifier(resName, "drawable", skinPackageName);

        Drawable trueDrawable = null;
        try {
            LogUtils.e("ttgg", "SDK_INT = " + android.os.Build.VERSION.SDK_INT);
            if (android.os.Build.VERSION.SDK_INT < 22) {
                trueDrawable = mResources.getDrawable(trueResId);
            } else {
                trueDrawable = mResources.getDrawable(trueResId, null);
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            trueDrawable = originDrawable;
        }

        return trueDrawable;
    }


    public StateColor getStateColor(int resId) {
        StateColor stateColor = null;

        boolean isExtendSkin = true;

        if (mResources == null || isDefaultSkin) {
            isExtendSkin = false;
        }
        String resName = context.getResources().getResourceEntryName(resId);
        if (isExtendSkin) {
            int trueResId = mResources.getIdentifier(resName, "color", skinPackageName);
            LogUtils.e("attr1", "trueResId = " + trueResId);
            if (trueResId != 0) {
                stateColor = getStateColor(mResources, resName, trueResId);
            }
        }
        if (stateColor == null) {
            stateColor = getStateColor(context.getResources(), resName, resId);
        }

        return stateColor;
    }






    /**
     * 加载指定资源颜色drawable,转化为ColorStateList，保证selector类型的Color也能被转换。</br>
     * 无皮肤包资源返回默认主题颜色
     *
     * @param resId
     * @return
     * @author pinotao
     */
    public ColorStateList getColorStateList(int resId) {
        LogUtils.e("attr1", "getColorStateList");

        boolean isExtendSkin = true;

        if (mResources == null || isDefaultSkin) {
            isExtendSkin = false;
        }

        String resName = context.getResources().getResourceEntryName(resId);
        LogUtils.e("attr1", "resName = " + resName);
        if (isExtendSkin) {
            LogUtils.e("attr1", "isExtendSkin");
            int trueResId = mResources.getIdentifier(resName, "color", skinPackageName);
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
                    trueColorList = mResources.getColorStateList(trueResId);
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
