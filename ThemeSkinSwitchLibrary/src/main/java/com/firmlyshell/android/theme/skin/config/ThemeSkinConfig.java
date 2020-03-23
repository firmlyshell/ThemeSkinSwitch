package com.firmlyshell.android.theme.skin.config;

import android.content.Context;

import com.firmlyshell.android.theme.skin.util.PreferencesUtils;


public class ThemeSkinConfig {

    public static final String NAMESPACE = "http://schemas.android.com/android/skin";
    public static final String SKIN_SUFFIX = ".theme";
    public static final String SKIN_FOLER_NAME = "skin";
    public static final String PREF_CUSTOM_SKIN_PATH = "theme_skin_custom_path";
    public static final String DEFAULT_SKIN = "theme_skin_default";
    public static final String SKIN_FROM = "theme_skin_from";
    public static final int FROM_INTERNAL = 0;
    public static final int FROM_EXTERNAL = 1;
    public static final String ATTR_SKIN_ENABLE = "enable";

    /**
     * get path of last skin package path
     *
     * @param context
     * @return path of skin package
     */
    public static String getCustomSkinPath(Context context) {
        return PreferencesUtils.getString(context, PREF_CUSTOM_SKIN_PATH, DEFAULT_SKIN);
    }

    public static void saveSkinPath(Context context, String path) {
        PreferencesUtils.putString(context, PREF_CUSTOM_SKIN_PATH, path);
    }

    public static boolean isDefaultSkin(Context context) {
        return DEFAULT_SKIN.equals(getCustomSkinPath(context));
    }
}
