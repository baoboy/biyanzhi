package com.biyanzhi.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.biyanzhi.R;

public class AboutActivity extends BaseActivity {
	private TextView txt_title;
	private TextView txt_version;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		txt_title = (TextView) findViewById(R.id.title_txt);
		txt_title.setText("关于");
		findViewById(R.id.back).setOnClickListener(this);
		txt_version = (TextView) findViewById(R.id.txt_version);
		txt_version.setText("比颜值");

	}

	@Override
	public void onClick(View v) {
		finishThisActivity();
	}
}
