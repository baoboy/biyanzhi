package com.biyanzhi.activity;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.biyanzhi.R;
import com.biyanzhi.applation.MyApplation;
import com.biyanzhi.task.GetVersionTask;
import com.biyanzhi.task.GetVersionTask.UpDateVersion;
import com.biyanzhi.utils.Constants;
import com.biyanzhi.utils.DialogUtil;
import com.biyanzhi.utils.SharedUtils;
import com.biyanzhi.utils.ToastUtil;
import com.biyanzhi.utils.UniversalImageLoadTool;
import com.biyanzhi.utils.Utils;
import com.biyanzhi.view.DampView;
import com.biyianzhi.interfaces.ConfirmDialog;
import com.easemob.EMCallBack;

import fynn.app.PromptDialog;

public class PersonalCenterActivity extends BaseActivity {
	private DampView view;
	private ImageView img_avatar;
	private TextView txt_title;
	private TextView txt_version;
	private TextView txt_name;
	private int unReadCount;
	private ImageView img_prompt;
	private RelativeLayout layout_message;
	private Dialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_personal_center);
		unReadCount = getIntent().getIntExtra("unReadCount", 0);
		initView();
		registerBoradcastReceiver();
	}

	private void initView() {
		img_prompt = (ImageView) findViewById(R.id.img_prompt);
		if (unReadCount > 0) {
			img_prompt.setVisibility(View.VISIBLE);
		} else {
			img_prompt.setVisibility(View.GONE);
		}
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
		layout_message = (RelativeLayout) findViewById(R.id.layout_message);
		layout_message.setOnClickListener(this);
		findViewById(R.id.btn_exit).setOnClickListener(this);
		findViewById(R.id.back).setOnClickListener(this);
		findViewById(R.id.layout_about).setOnClickListener(this);
		findViewById(R.id.layout_version).setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_avatar_bg:
			startActivity(new Intent(this, SelfInfoActivity.class).putExtra(
					"user_id", SharedUtils.getIntUid()));
			Utils.leftOutRightIn(this);
			break;
		case R.id.layout_message:
			startActivity(new Intent(this, ChatAllHistoryActivity.class));
			Utils.leftOutRightIn(this);
			break;
		case R.id.btn_exit:
			quitPrompt();
			break;
		case R.id.back:
			finishThisActivity();
			break;
		case R.id.layout_about:
			startActivity(new Intent(this, AboutActivity.class));
			Utils.leftOutRightIn(this);
			break;
		case R.id.layout_version:
			getNewVersion();
			break;
		default:
			break;
		}
	}

	private void quitPrompt() {
		PromptDialog.Builder dialog = DialogUtil.confirmDialog(this, "确定要退出吗?",
				"确定", "取消", new ConfirmDialog() {
					@Override
					public void onOKClick() {
						quit();
					}

					@Override
					public void onCancleClick() {
					}
				});
		dialog.show();
	}

	private void quit() {
		dialog = DialogUtil.createLoadingDialog(this, "请稍候");
		dialog.show();
		MyApplation.logoutHuanXin(new EMCallBack() {

			@Override
			public void onSuccess() {
				dialog.dismiss();
				SharedUtils.clearData();
				MyApplation.exit(false);
				startActivity(new Intent(PersonalCenterActivity.this,
						LoginActivity.class));
			}

			@Override
			public void onProgress(int arg0, String arg1) {

			}

			@Override
			public void onError(int arg0, String arg1) {
				dialog.dismiss();
				ToastUtil.showToast("退出失败");
			}
		});

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

	private void getNewVersion() {
		final Dialog dialog = DialogUtil.createLoadingDialog(this, "请稍候");
		dialog.show();
		GetVersionTask task = new GetVersionTask(this, true);
		task.setCallBack(new UpDateVersion() {
			@Override
			public void getNewVersion(int rt, String versionCode, String link,
					String version_info) {
				if (dialog != null) {
					dialog.dismiss();
				}
				if (rt == 0) {
					return;
				}
				DialogUtil.newVewsionDialog(PersonalCenterActivity.this,
						versionCode, link, version_info);
			}
		});
		task.execute();
	}
}
