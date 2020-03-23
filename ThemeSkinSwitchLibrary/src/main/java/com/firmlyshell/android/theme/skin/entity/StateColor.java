package com.firmlyshell.android.theme.skin.entity;

import android.content.res.ColorStateList;

/**
 * 记录颜色的类型与值，处理{@link TextColorAttr} 解决 ColorStateList 问题
 */
public class StateColor {

    public static final int TYPE_COLOR_VALUE = 1;
    public static final int TYPE_COLOR_STATE_LIST = 2;


    private int mType;
    private int mValue;
    private ColorStateList mColorStateList;

    public StateColor() {
    }

    public StateColor(int mType, int mValue) {
        this.mType = mType;
        this.mValue = mValue;
    }

    public StateColor(int mType, ColorStateList mColorStateList) {
        this.mType = mType;
        this.mColorStateList = mColorStateList;
    }

    public int getType() {
        return mType;
    }

    public void setType(int mType) {
        this.mType = mType;
    }

    public int getValue() {
        return mValue;
    }

    public void setValue(int mValue) {
        this.mValue = mValue;
    }

    public ColorStateList getColorStateList() {
        return mColorStateList;
    }

    public void setColorStateList(ColorStateList mColorStateList) {
        this.mColorStateList = mColorStateList;
    }
}
