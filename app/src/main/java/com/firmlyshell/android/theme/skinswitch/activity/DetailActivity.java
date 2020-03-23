package com.firmlyshell.android.theme.skinswitch.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.firmlyshell.android.theme.skin.util.ResourceUtils;
import com.firmlyshell.android.theme.skinswitch.R;
import com.firmlyshell.android.theme.skinswitch.base.BaseActivity;


public class DetailActivity extends BaseActivity {
	
	private TextView titleText;
	private TextView detailText;
	private String article;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		
		initData();
		initView();


//        ThemeSkinManager.getInstance().getStateColor(R.color.news_item_text_color_selector);
//        ThemeSkinManager.getInstance().getStateColor(R.color.color_new_item_bg);
	}

	private void initData() {
		article = ResourceUtils.geFileFromAssets(this, "article.txt");
	}

	private void initView() {
		titleText = (TextView) findViewById(R.id.title_text);
		detailText = (TextView) findViewById(R.id.detail_text);
		
		titleText.setText("生命中的美好都是免费的");
		detailText.setText(article);
	}
}
