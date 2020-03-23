package com.firmlyshell.android.theme.skinswitch.app;

import android.app.Application;
import android.content.res.AssetManager;
import android.os.Environment;

import com.firmlyshell.android.theme.skin.ThemeSkinManager;
import com.firmlyshell.android.theme.skin.util.LogUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class SkinApplication extends Application {


    private static final String SKIN_NAME = "BlackFantacy.skin";
    private static  String SKIN_DIR = Environment
            .getExternalStorageDirectory() + File.separator + SKIN_NAME;

    public void onCreate() {
        super.onCreate();
        prepareCopySkin();
        initSkinLoader();
    }

    /**
     * Must call init first
     */
    private void initSkinLoader() {

        ThemeSkinManager.getInstance().init(this);
        ThemeSkinManager.getInstance().load();
    }

    private void prepareCopySkin() {

        SKIN_DIR =  getFilesDir()+ File.separator + SKIN_NAME;

        File file = new File(SKIN_DIR);
        LogUtils.i("prepareCopySkin");
        if (!file.exists()) {
            LogUtils.i("prepareCopySkin");
            AssetManager assetManager = getAssets();
            try {
                InputStream inputStream = assetManager.open(SKIN_NAME);

                FileOutputStream outputStream = new FileOutputStream(file);

                byte[] bytes = new byte[1024];
                int len = inputStream.read(bytes);
                while (len != -1) {
                    outputStream.write(bytes, 0, len);
                    len = inputStream.read(bytes);
                }

                inputStream.close();
                outputStream.flush();
                outputStream.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}