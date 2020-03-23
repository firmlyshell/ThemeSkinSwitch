package com.firmlyshell.android.theme.skin.entity;

import java.util.ArrayList;
import java.util.List;

import android.view.View;

import com.firmlyshell.android.theme.skin.util.ListUtils;

public class ThemeSkinPair {

    public View view;

    public List<ThemeSkinAttr> attrs;

    public ThemeSkinPair() {
        attrs = new ArrayList<ThemeSkinAttr>();
    }

    public ThemeSkinPair(View view, List<ThemeSkinAttr> attrs) {
        this.view = view;
        this.attrs = attrs;
    }

    public void apply() {
        if (ListUtils.isEmpty(attrs)) {
            return;
        }
        for (ThemeSkinAttr at : attrs) {
            at.apply(view);
        }
    }

    public void clean() {
        if (!ListUtils.isEmpty(attrs)) {
            attrs.clear();
        }
    }

    @Override
    public String toString() {
        return "ThemeSkinPair [view=" + view.getClass().getSimpleName() + ", attrs=" + attrs + "]";
    }
}
