package com.firmlyshell.android.theme.skin.entity;


public class AttrFactory {
	
	public static final String BACKGROUND = "background";
	public static final String TEXT_COLOR = "textColor";
	public static final String LIST_SELECTOR = "listSelector";
	public static final String DIVIDER = "divider";
	
	public static ThemeSkinAttr get(String attrName, int attrValueRefId, String attrValueRefName, String typeName){
		
		ThemeSkinAttr mThemeSkinAttr = null;
		
		if(BACKGROUND.equals(attrName)){ 
			mThemeSkinAttr = new BackgroundAttr();
		}else if(TEXT_COLOR.equals(attrName)){ 
			mThemeSkinAttr = new TextColorAttr();
		}else if(LIST_SELECTOR.equals(attrName)){ 
			mThemeSkinAttr = new ListSelectorAttr();
		}else if(DIVIDER.equals(attrName)){ 
			mThemeSkinAttr = new DividerAttr();
		}else{
			return null;
		}
		
		mThemeSkinAttr.attrName = attrName;
		mThemeSkinAttr.attrValueRefId = attrValueRefId;
		mThemeSkinAttr.attrValueRefName = attrValueRefName;
		mThemeSkinAttr.attrValueTypeName = typeName;
		return mThemeSkinAttr;
	}
	
	/**
	 * Check whether the attribute is supported
	 * @param attrName
	 * @return true : supported <br>
	 * 		   false: not supported
	 */
	public static boolean isSupportedAttr(String attrName){
		if(BACKGROUND.equals(attrName)){ 
			return true;
		}
		if(TEXT_COLOR.equals(attrName)){ 
			return true;
		}
		if(LIST_SELECTOR.equals(attrName)){ 
			return true;
		}
		if(DIVIDER.equals(attrName)){ 
			return true;
		}
		return false;
	}
}
