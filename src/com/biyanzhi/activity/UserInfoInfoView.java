package com.biyanzhi.activity;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.biyanzhi.R;
import com.biyanzhi.utils.Utils;

public class UserInfoInfoView implements OnClickListener {
	private UserInfoActivity mActivity;
	private View mContentRootView;
	private TextView txt_gender;
	private TextView txt_birthday;
	private TextView txt_address;
	private TextView txt_guanzhu;
	private TextView txt_ta_guanzhu;
	private RelativeLayout layout_guanzhu;

	public UserInfoInfoView(UserInfoActivity activity, View contentRootView) {
		this.mActivity = activity;
		this.mContentRootView = contentRootView;
		initView();
	}

	private void initView() {
		layout_guanzhu = (RelativeLayout) mContentRootView
				.findViewById(R.id.layout_guanzhu);
		txt_guanzhu = (TextView) mContentRootView
				.findViewById(R.id.txt_guanzhu);
		txt_ta_guanzhu = (TextView) mContentRootView
				.findViewById(R.id.txt_ta_guanzhu);
		txt_address = (TextView) mContentRootView
				.findViewById(R.id.txt_address);
		txt_birthday = (TextView) mContentRootView
				.findViewById(R.id.txt_birthday);
		txt_gender = (TextView) mContentRootView.findViewById(R.id.txt_gender);
		layout_guanzhu.setOnClickListener(this);
		mContentRootView.findViewById(R.id.layout_ta_guanzhu)
				.setOnClickListener(this);

	}

	public void setValue(String str_address, String str_gender,
			String str_birthday, int guanzhu_count, int my_guanzhu_count) {
		txt_address.setText(str_address);
		txt_birthday.setText(str_birthday);
		txt_gender.setText(str_gender);
		txt_guanzhu.setText(guanzhu_count + "»À");
		txt_ta_guanzhu.setText(my_guanzhu_count + "»À");
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.layout_guanzhu:
			mActivity
					.startActivity(new Intent(mActivity, GuanZhuActivity.class)
							.putExtra("user_id", mActivity.getUserID()));
			Utils.leftOutRightIn(mActivity);
			break;
		case R.id.layout_ta_guanzhu:
			mActivity.startActivity(new Intent(mActivity,
					MyGuanZhuActivity.class).putExtra("user_id",
					mActivity.getUserID()));
			Utils.leftOutRightIn(mActivity);
		default:
			break;
		}
	}
}
