package com.biyanzhi.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.biyanzhi.R;
import com.biyanzhi.utils.SharedUtils;
import com.biyanzhi.utils.Utils;

public class UserSelfInfoInfoView implements OnClickListener {
	private Context mActivity;
	private View mContentRootView;
	private TextView txt_gender;
	private TextView txt_birthday;
	private TextView txt_address;
	private TextView txt_guanzhu;
	private TextView txt_nick_name;
	private RelativeLayout layout_nick_name;

	public UserSelfInfoInfoView(Context activity, View contentRootView) {
		this.mActivity = activity;
		this.mContentRootView = contentRootView;
		initView();
	}

	private void initView() {
		txt_nick_name = (TextView) mContentRootView
				.findViewById(R.id.txt_nick_name);
		txt_guanzhu = (TextView) mContentRootView
				.findViewById(R.id.txt_guanzhu);
		txt_address = (TextView) mContentRootView
				.findViewById(R.id.txt_address);
		txt_birthday = (TextView) mContentRootView
				.findViewById(R.id.txt_birthday);
		txt_gender = (TextView) mContentRootView.findViewById(R.id.txt_gender);
		layout_nick_name = (RelativeLayout) mContentRootView
				.findViewById(R.id.layout_nick_name);
		layout_nick_name.setOnClickListener(this);
		setValue();
	}

	public void setValue() {
		txt_nick_name.setText(SharedUtils.getAPPUserName());
		txt_address.setText(SharedUtils.getAPPUserAddress());
		txt_birthday.setText(SharedUtils.getAPPUserBirthday());
		txt_gender.setText(SharedUtils.getAPPUserGender());
		txt_guanzhu.setText(SharedUtils.getAPPUserGuanZhuCount() + "»À");
	}

	public void setGuanZhuCount(int count) {
		txt_guanzhu.setText(count + "»À");
		SharedUtils.setAPPUserGuanZhuCount(count);

	}

	public void setNickName() {
		txt_nick_name.setText(SharedUtils.getAPPUserName());

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.layout_nick_name:
			mActivity.startActivity(new Intent(mActivity,
					UpDateUserNameActivity.class));
			Utils.leftOutRightIn(mActivity);
			break;

		default:
			break;
		}
	}
}
