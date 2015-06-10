package com.biyanzhi.activity;

import com.biyanzhi.R;

import android.view.View;
import android.widget.TextView;

public class UserInfoInfoView {
	private UserInfoActivity mActivity;
	private View mContentRootView;
	private TextView txt_gender;
	private TextView txt_birthday;
	private TextView txt_address;
	private TextView txt_guanzhu;

	public UserInfoInfoView(UserInfoActivity activity, View contentRootView) {
		this.mActivity = activity;
		this.mContentRootView = contentRootView;
		initView();
	}

	private void initView() {
		txt_guanzhu = (TextView) mContentRootView
				.findViewById(R.id.txt_guanzhu);
		txt_address = (TextView) mContentRootView
				.findViewById(R.id.txt_address);
		txt_birthday = (TextView) mContentRootView
				.findViewById(R.id.txt_birthday);
		txt_gender = (TextView) mContentRootView.findViewById(R.id.txt_gender);

	}

	public void setValue(String str_address, String str_gender,
			String str_birthday, int guanzhu_count) {
		txt_address.setText(str_address);
		txt_birthday.setText(str_birthday);
		txt_gender.setText(str_gender);
		txt_guanzhu.setText(guanzhu_count + "»À");
	}
}
