package com.firmlyshell.android.theme.skinswitch.activity;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firmlyshell.android.theme.skin.ThemeSkinManager;
import com.firmlyshell.android.theme.skin.listener.OnLoaderListener;
import com.firmlyshell.android.theme.skin.util.LogUtils;
import com.firmlyshell.android.theme.skinswitch.R;
import com.firmlyshell.android.theme.skinswitch.base.BaseActivity;

import java.io.File;


public class SettingActivity extends BaseActivity {

	/**
	 * Put this skin file on the root of sdcard
	 * eg:
	 * /mnt/sdcard/BlackFantacy.skin
	 */
	private static final String SKIN_NAME = "BlackFantacy.skin";
	private static  String SKIN_DIR = Environment
			.getExternalStorageDirectory() + File.separator + SKIN_NAME;
	
	
	private TextView titleText;
	private Button setOfficalSkinBtn;
	private Button setNightSkinBtn;
	
	private boolean isOfficalSelected = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);

		initView();
	}

	private void initView() {
		titleText = (TextView) findViewById(R.id.title_text);
		titleText.setText("设置皮肤");
		setOfficalSkinBtn = (Button) findViewById(R.id.set_default_skin);
		setNightSkinBtn = (Button) findViewById(R.id.set_night_skin);
		
		
		isOfficalSelected = !ThemeSkinManager.getInstance().isExternalSkin();
		
		if(isOfficalSelected){
			setOfficalSkinBtn.setText("官方默认(当前)");
			setNightSkinBtn.setText("黑色幻想");
		}else{
			setNightSkinBtn.setText("黑色幻想(当前)");
			setOfficalSkinBtn.setText("官方默认");			
		}
		
		setNightSkinBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onSkinSetClick();
			}
		});
		
		setOfficalSkinBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onSkinResetClick();
			}
		});
	}

	protected void onSkinResetClick() {
		if(!isOfficalSelected){
			ThemeSkinManager.getInstance().restoreDefaultTheme();
			Toast.makeText(getApplicationContext(), "切换成功", Toast.LENGTH_SHORT).show();			
			setOfficalSkinBtn.setText("官方默认(当前)");
			setNightSkinBtn.setText("黑色幻想");
			isOfficalSelected = true;
		}
	}

	private void onSkinSetClick() {
		if(!isOfficalSelected) return;

		SKIN_DIR =  getFilesDir()+ File.separator + SKIN_NAME;
		
		File skin = new File(SKIN_DIR);

		if(skin == null || !skin.exists()){
			Toast.makeText(getApplicationContext(), "请检查" + SKIN_DIR + "是否存在", Toast.LENGTH_SHORT).show();
			return;
		}

		ThemeSkinManager.getInstance().load(skin.getAbsolutePath(),
				new OnLoaderListener() {
					@Override
					public void onStart() {
						LogUtils.e("startloadSkin");
					}

					@Override
					public void onSuccess() {
						LogUtils.e("loadSkinSuccess");
						Toast.makeText(getApplicationContext(), "切换成功", Toast.LENGTH_SHORT).show();
						setNightSkinBtn.setText("黑色幻想(当前)");
						setOfficalSkinBtn.setText("官方默认");		
						isOfficalSelected = false;
						ThemeSkinManager.getInstance().notifySkinUpdate();
					}

					@Override
					public void onFailed() {
						LogUtils.e("loadSkinFail");
						Toast.makeText(getApplicationContext(), "切换失败", Toast.LENGTH_SHORT).show();
					}
				});
	}
}
