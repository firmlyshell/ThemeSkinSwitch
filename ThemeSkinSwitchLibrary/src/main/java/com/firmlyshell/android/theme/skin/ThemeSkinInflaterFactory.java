package com.firmlyshell.android.theme.skin;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.LayoutInflater.Factory;
import android.view.View;

import com.firmlyshell.android.theme.skin.config.ThemeSkinConfig;
import com.firmlyshell.android.theme.skin.entity.AttrFactory;
import com.firmlyshell.android.theme.skin.entity.ThemeSkinAttr;
import com.firmlyshell.android.theme.skin.entity.ThemeSkinPair;
import com.firmlyshell.android.theme.skin.util.LogUtils;
import com.firmlyshell.android.theme.skin.util.ListUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Supply {@link ThemeSkinInflaterFactory} to be called when inflating from a LayoutInflater.
 *
 * <p>Use this to collect the {skin:enable="true|false"} views availabled in our XML layout files.
 *
 * @author fengjun
 */
public class ThemeSkinInflaterFactory implements Factory {


    /**
     * 处理默认的页面没有指定主题管理器的
     */
    private ThemeSkinPairsManager mThemeSkinManager;


    public ThemeSkinInflaterFactory(ThemeSkinPairsManager mThemeSkinManager) {
        this.mThemeSkinManager = mThemeSkinManager;
    }

    /**
     * Store the view item that need skin changing in the activity
     */

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        // if this is NOT enable to be skined , simplly skip it
        boolean isSkinEnable = attrs.getAttributeBooleanValue(ThemeSkinConfig.NAMESPACE, ThemeSkinConfig.ATTR_SKIN_ENABLE, false);
        if (!isSkinEnable) {
            return null;
        }

        View view = onRealCreateView(context, name, attrs);

        if (view == null) {
            return null;
        }

        ThemeSkinPair themeSkinPair = parseThemeSkinAttr(context, attrs, view);
        if (context instanceof ContextThemeSkinWrapper) {
            ((ContextThemeSkinWrapper) context).getThemeSkinManager().addSkinView(themeSkinPair);
        } else {
            if (mThemeSkinManager != null) {
                mThemeSkinManager.addSkinView(themeSkinPair);
            } else {
                LogUtils.e("ThemeSkinInflaterFactory: mThemeSkinManager is null!");
            }
        }

        if (ThemeSkinManager.getInstance().isExternalSkin()) {
            themeSkinPair.apply();
        }

        return view;
    }


    /**
     * Invoke low-level function for instantiating a view by name. This attempts to
     * instantiate a view class of the given <var>name</var> found in this
     * LayoutInflater's ClassLoader.
     *
     * @param context
     * @param name    The full name of the class to be instantiated.
     * @param attrs   The XML attributes supplied for this instance.
     * @return View The newly instantiated view, or null.
     */
    private View onRealCreateView(Context context, String name, AttributeSet attrs) {
        View view = null;
        try {
            if (-1 == name.indexOf('.')) {
                if ("View".equals(name)) {
                    view = LayoutInflater.from(context).createView(name, "android.view.", attrs);
                }
                if (view == null) {
                    view = LayoutInflater.from(context).createView(name, "android.widget.", attrs);
                }
                if (view == null) {
                    view = LayoutInflater.from(context).createView(name, "android.webkit.", attrs);
                }
            } else {
                view = LayoutInflater.from(context).createView(name, null, attrs);
            }

            LogUtils.i("about to create " + name);

        } catch (Exception e) {
            LogUtils.e("error while create 【" + name + "】 : " + e.getMessage());
            view = null;
        }
        return view;
    }

    /**
     * Collect skin able tag such as background , textColor and so on
     *
     * @param context
     * @param attrs
     * @param view
     */
    public ThemeSkinPair parseThemeSkinAttr(Context context, AttributeSet attrs, View view) {
        List<ThemeSkinAttr> viewAttrs = new ArrayList<ThemeSkinAttr>();
        ThemeSkinPair themeSkinPair = null;

        for (int i = 0; i < attrs.getAttributeCount(); i++) {
            String attrName = attrs.getAttributeName(i);
            String attrValue = attrs.getAttributeValue(i);

            if (!AttrFactory.isSupportedAttr(attrName)) {
                continue;
            }

            if (attrValue.startsWith("@")) {
                try {
                    int id = Integer.parseInt(attrValue.substring(1));
                    String entryName = context.getResources().getResourceEntryName(id);
                    String typeName = context.getResources().getResourceTypeName(id);
                    ThemeSkinAttr mThemeSkinAttr = AttrFactory.get(attrName, id, entryName, typeName);
                    if (mThemeSkinAttr != null) {
                        viewAttrs.add(mThemeSkinAttr);
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                } catch (NotFoundException e) {
                    e.printStackTrace();
                }
            }
        }


        if (!ListUtils.isEmpty(viewAttrs)) {
            themeSkinPair = new ThemeSkinPair(view, viewAttrs);
        }
        return themeSkinPair;
    }


}
