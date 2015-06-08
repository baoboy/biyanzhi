package com.biyanzhi.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.biyanzhi.R;
import com.biyanzhi.data.User;
import com.biyanzhi.data.UserInfo;
import com.biyanzhi.enums.RetError;
import com.biyanzhi.task.GetUserInfoTask;
import com.biyanzhi.utils.DialogUtil;
import com.biyanzhi.utils.UniversalImageLoadTool;
import com.biyanzhi.utils.Utils;
import com.biyanzhi.view.CircularImage;
import com.biyianzhi.interfaces.AbstractTaskPostCallBack;

public class UserInfoActivity extends BaseActivity {
	private RelativeLayout layout_title;
	private Button btn_info;
	private Button btn_yanzhi;
	private View line1, line2, line3;
	private ViewFlipper mVfFlipper;
	private TextView txt_title;
	private ImageView img_avatar_bg;
	private CircularImage img_avatar;
	private int user_id;

	private UserInfo info = new UserInfo();

	private User user;
	private Dialog dialog;

	private UserInfoInfoView info_View;
	private UserInfoYanZhiView yanzhi_View;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_info);
		user_id = getIntent().getIntExtra("user_id", 0);
		info.setUser_id(user_id);
		initView();
		getValue();
	}

	@SuppressWarnings("deprecation")
	private void initView() {
		img_avatar = (CircularImage) findViewById(R.id.img_avatar);
		img_avatar_bg = (ImageView) findViewById(R.id.img_avatar_bg);
		img_avatar_bg.setAlpha(100);
		txt_title = (TextView) findViewById(R.id.title_txt);
		Utils.getFocus(txt_title);
		mVfFlipper = (ViewFlipper) findViewById(R.id.viewflipper);
		mVfFlipper.setDisplayedChild(1);
		layout_title = (RelativeLayout) findViewById(R.id.title);
		layout_title.setBackgroundResource(R.color.black);
		layout_title.getBackground().setAlpha(100);
		btn_info = (Button) findViewById(R.id.btn_info);
		btn_yanzhi = (Button) findViewById(R.id.btn_yanzhi);
		line1 = (View) findViewById(R.id.line1);
		line2 = (View) findViewById(R.id.line2);
		line3 = (View) findViewById(R.id.line3);
		line2.getBackground().setAlpha(120);
		line1.getBackground().setAlpha(120);
		line3.getBackground().setAlpha(120);
		info_View = new UserInfoInfoView(this, mVfFlipper.getChildAt(0));
		yanzhi_View = new UserInfoYanZhiView(this, mVfFlipper.getChildAt(1));
		setListener();
	}

	private void setListener() {
		btn_info.setOnClickListener(this);
		btn_yanzhi.setOnClickListener(this);
	}

	private void getValue() {
		dialog = DialogUtil.createLoadingDialog(this);
		dialog.show();
		GetUserInfoTask task = new GetUserInfoTask();
		task.setmCallBack(new AbstractTaskPostCallBack<RetError>() {
			@Override
			public void taskFinish(RetError result) {
				if (dialog != null) {
					dialog.dismiss();
				}
				if (result != RetError.NONE) {
					return;
				}
				user = info.getUser();
				info_View.setValue(user.getUser_address(),
						user.getUser_gender(), user.getUser_birthday());
				txt_title.setText(user.getUser_name());
				UniversalImageLoadTool.disPlay(user.getUser_avatar(),
						img_avatar_bg, R.drawable.default_avatar);
				UniversalImageLoadTool.disPlay(user.getUser_avatar(),
						img_avatar, R.drawable.default_avatar);
				yanzhi_View.setValue(info.getPictureList());
			}
		});
		task.executeParallel(info);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_info:
			btn_info.setTextColor(getResources().getColor(R.color.pciture_blue));
			btn_yanzhi.setTextColor(getResources().getColor(
					R.color.pciture_text));
			mVfFlipper.setDisplayedChild(0);

			break;
		case R.id.btn_yanzhi:
			btn_yanzhi.setTextColor(getResources().getColor(
					R.color.pciture_blue));
			btn_info.setTextColor(getResources().getColor(R.color.pciture_text));
			mVfFlipper.setDisplayedChild(1);
			break;
		default:
			break;
		}
	}
}
