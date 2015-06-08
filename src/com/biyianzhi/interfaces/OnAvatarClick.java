package com.biyianzhi.interfaces;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

import com.biyanzhi.activity.UserInfoActivity;
import com.biyanzhi.utils.SharedUtils;
import com.biyanzhi.utils.Utils;

public class OnAvatarClick implements OnClickListener {
	private int user_id;
	private Context mContext;

	public OnAvatarClick(int user_id, Context context) {
		this.user_id = user_id;
		this.mContext = context;
	}

	@Override
	public void onClick(View v) {
		if (user_id < 0) {
			return;
		}

		Intent intent = new Intent();
		if (user_id == SharedUtils.getIntUid()) {
			intent.setClass(mContext, UserInfoActivity.class).putExtra(
					"user_id", user_id);
		} else {
			intent.setClass(mContext, UserInfoActivity.class).putExtra(
					"user_id", user_id);
		}
		mContext.startActivity(intent);
		Utils.leftOutRightIn(mContext);
	}
}
