package com.firmlyshell.android.theme.skin.listener;

/**
 *
 */
public interface OnThemeSkinLoader {
	void attach(OnThemeSkinUpdate observer);
	void detach(OnThemeSkinUpdate observer);
	void notifySkinUpdate();
}
