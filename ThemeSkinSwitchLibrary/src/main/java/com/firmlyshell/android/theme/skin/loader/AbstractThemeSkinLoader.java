package com.firmlyshell.android.theme.skin.loader;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.util.TypedValue;

import com.firmlyshell.android.theme.skin.entity.StateColor;
import com.firmlyshell.android.theme.skin.util.LogUtils;

public abstract class AbstractThemeSkinLoader implements ThemeSkinLoader {


    private final Object mTmpValueLock = new Object();
    private TypedValue mTmpValue = new TypedValue();


    public StateColor getStateColor(Resources resources, String name, int resId) {
        final TypedValue value = obtainTempTypedValue();
        try {
            resources.getValue(resId, value, true);
            if (value.type >= TypedValue.TYPE_FIRST_INT && value.type <= TypedValue.TYPE_LAST_INT) {
                return new StateColor(StateColor.TYPE_COLOR_VALUE, value.data);
            } else if (value.type == TypedValue.TYPE_STRING) {
                final ColorStateList csl = resources.getColorStateList(resId);
                if (csl != null) {
                    return new StateColor(StateColor.TYPE_COLOR_VALUE, csl);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        releaseTempTypedValue(value);
        LogUtils.e("attr1", "getStateColor() not found ! name=" + name);
        return null;
    }

    /**
     * Returns a TypedValue suitable for temporary use. The obtained TypedValue
     * should be released using {@link #releaseTempTypedValue(TypedValue)}.
     *
     * @return a typed value suitable for temporary use
     */
    public TypedValue obtainTempTypedValue() {
        TypedValue tmpValue = null;
        synchronized (mTmpValueLock) {
            if (mTmpValue != null) {
                tmpValue = mTmpValue;
                mTmpValue = null;
            }
        }
        if (tmpValue == null) {
            return new TypedValue();
        }
        return tmpValue;
    }

    /**
     * Returns a TypedValue to the pool. After calling this method, the
     * specified TypedValue should no longer be accessed.
     *
     * @param value the typed value to return to the pool
     */
    public void releaseTempTypedValue(TypedValue value) {
        synchronized (mTmpValueLock) {
            if (mTmpValue == null) {
                mTmpValue = value;
            }
        }
    }

}
