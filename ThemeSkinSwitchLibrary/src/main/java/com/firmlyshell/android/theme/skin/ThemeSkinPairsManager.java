package com.firmlyshell.android.theme.skin;

import android.content.Context;
import android.view.View;

import com.firmlyshell.android.theme.skin.entity.AttrFactory;
import com.firmlyshell.android.theme.skin.entity.DynamicAttr;
import com.firmlyshell.android.theme.skin.entity.ThemeSkinAttr;
import com.firmlyshell.android.theme.skin.entity.ThemeSkinPair;
import com.firmlyshell.android.theme.skin.listener.OnThemeSkinUpdate;
import com.firmlyshell.android.theme.skin.util.ListUtils;
import com.firmlyshell.android.theme.skin.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

public class ThemeSkinPairsManager implements OnThemeSkinUpdate {


    private List<ThemeSkinPair> mThemeSkinPairs = new ArrayList<ThemeSkinPair>();


    public void dynamicAddSkinEnableView(Context context, View view, List<DynamicAttr> pDAttrs) {
        List<ThemeSkinAttr> viewAttrs = new ArrayList<ThemeSkinAttr>();


        for (DynamicAttr dAttr : pDAttrs) {
            int id = dAttr.refResId;
            String entryName = context.getResources().getResourceEntryName(id);
            String typeName = context.getResources().getResourceTypeName(id);
            ThemeSkinAttr mThemeSkinAttr = AttrFactory.get(dAttr.attrName, id, entryName, typeName);
            viewAttrs.add(mThemeSkinAttr);
        }

        ThemeSkinPair themeSkinPair = new ThemeSkinPair(view, viewAttrs);
        addSkinView(themeSkinPair);
    }

    public void dynamicAddSkinEnableView(Context context, View view, String attrName, int attrValueResId) {
        int id = attrValueResId;
        String entryName = context.getResources().getResourceEntryName(id);
        String typeName = context.getResources().getResourceTypeName(id);
        ThemeSkinAttr mThemeSkinAttr = AttrFactory.get(attrName, id, entryName, typeName);

        List<ThemeSkinAttr> viewAttrs = new ArrayList<ThemeSkinAttr>();
        viewAttrs.add(mThemeSkinAttr);

        ThemeSkinPair themeSkinPair = new ThemeSkinPair(view, viewAttrs);
        addSkinView(themeSkinPair);
    }

    public void addSkinView(ThemeSkinPair item) {
        LogUtils.i("ThemeSkinPairsManager:addSkinView");
        mThemeSkinPairs.add(item);
    }

    public void applySkin() {
        if (!ListUtils.isEmpty(mThemeSkinPairs)) {
            for (ThemeSkinPair pair : mThemeSkinPairs) {
                if (pair != null) {
                    pair.apply();
                }
            }
        }
    }

    @Override
    public void onThemeUpdate() {
        applySkin();
    }


    public void clean() {
        if (ListUtils.isEmpty(mThemeSkinPairs)) {
            return;
        }

        for (ThemeSkinPair si : mThemeSkinPairs) {
            if (si.view == null) {
                continue;
            }
            si.clean();
        }
    }

}
