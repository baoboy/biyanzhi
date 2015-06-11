package com.biyanzhi.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.biyanzhi.R;
import com.biyanzhi.utils.Constants;
import com.biyanzhi.utils.SharedUtils;
import com.biyanzhi.utils.UniversalImageLoadTool;
import com.biyanzhi.utils.Utils;
import com.biyanzhi.view.DampView;

public class PersonalCenterActivity extends BaseActivity {
	private DampView view;
	private ImageView img_avatar;
	private TextView txt_title;
	private TextView txt_version;
	private TextView txt_name;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_personal_center);
		initView();
		registerBoradcastReceiver();
	}

	private void initView() {
		img_avatar = (ImageView) findViewById(R.id.img_avatar_bg);
		view = (DampView) findViewById(R.id.scrollView1);
		view.setImageView(img_avatar);
		UniversalImageLoadTool.disPlay(SharedUtils.getAPPUserAvatar(),
				img_avatar, R.drawable.default_avatar);
		img_avatar.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
		txt_title = (TextView) findViewById(R.id.title_txt);
		txt_title.setText("个人中心");
		txt_version = (TextView) findViewById(R.id.txt_version);
		txt_version.setText("当前版本 " + Utils.getVersionName(this));
		txt_name = (TextView) findViewById(R.id.txt_name);
		txt_name.setText(SharedUtils.getAPPUserName());
		img_avatar.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_avatar_bg:
			startActivity(new Intent(this, SelfInfoActivity.class).putExtra(
					"user_id", SharedUtils.getIntUid()));
			Utils.leftOutRightIn(this);
			break;

		default:
			break;
		}
	}

	/**
	 * 注册该广播
	 */
	public void registerBoradcastReceiver() {
		IntentFilter myIntentFilter = new IntentFilter();
		myIntentFilter.addAction(Constants.UPDATE_USER_AVATAR);
		// 注册广播
		registerReceiver(mBroadcastReceiver, myIntentFilter);
	}

	/**
	 * 定义广播
	 */
	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(Constants.UPDATE_USER_AVATAR)) {
				UniversalImageLoadTool.disPlay(SharedUtils.getAPPUserAvatar(),
						img_avatar, R.drawable.default_avatar);
			}
		}
	};

	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mBroadcastReceiver);
	};
}
