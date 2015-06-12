package com.biyanzhi.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.biyanzhi.R;
import com.biyanzhi.data.User;
import com.biyanzhi.enums.RetError;
import com.biyanzhi.popwindow.CityListPopWindow;
import com.biyanzhi.popwindow.CityListPopWindow.SelectCity;
import com.biyanzhi.task.UpdateUserAddressTask;
import com.biyanzhi.utils.DialogUtil;
import com.biyanzhi.utils.SharedUtils;
import com.biyanzhi.utils.ToastUtil;
import com.biyanzhi.utils.Utils;
import com.biyianzhi.interfaces.AbstractTaskPostCallBack;

public class UserSelfInfoInfoView implements OnClickListener {
	private Context mActivity;
	private View mContentRootView;
	private TextView txt_gender;
	private TextView txt_birthday;
	private TextView txt_address;
	private TextView txt_guanzhu;
	private TextView txt_nick_name;
	private RelativeLayout layout_nick_name;
	private CityListPopWindow city_pop;
	private User user = new User();

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
		mContentRootView.findViewById(R.id.layout_address).setOnClickListener(
				this);
		setValue();
	}

	public void setValue() {
		txt_nick_name.setText(SharedUtils.getAPPUserName());
		txt_address.setText(SharedUtils.getAPPUserAddress());
		txt_birthday.setText(SharedUtils.getAPPUserBirthday());
		txt_gender.setText(SharedUtils.getAPPUserGender());
		txt_guanzhu.setText(SharedUtils.getAPPUserGuanZhuCount() + "人");
	}

	public void setGuanZhuCount(int count) {
		txt_guanzhu.setText(count + "人");
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
		case R.id.layout_address:
			city_pop = new CityListPopWindow(mActivity, v);
			city_pop.setmCallBack(new SelectCity() {

				@Override
				public void selectCity(String province, String province_key,
						String city) {
					txt_address.setText(province + " " + city);
					user.setUser_address(province + " " + city);
					updateAddress();
				}
			});
			city_pop.show();
			break;
		default:
			break;
		}
	}

	private Dialog dialog;

	private void updateAddress() {
		dialog = DialogUtil.createLoadingDialog(mActivity);
		dialog.show();
		UpdateUserAddressTask task = new UpdateUserAddressTask();
		task.setmCallBack(new AbstractTaskPostCallBack<RetError>() {
			@Override
			public void taskFinish(RetError result) {
				if (dialog != null) {
					dialog.dismiss();
				}
				if (result != RetError.NONE) {
					return;
				}
				ToastUtil.showToast("修改成功", Toast.LENGTH_SHORT);
				SharedUtils.setAPPUserAddress(user.getUser_address());
			}
		});
		task.execute(user);
	}

}
