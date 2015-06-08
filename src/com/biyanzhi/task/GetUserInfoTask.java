package com.biyanzhi.task;

import com.biyanzhi.data.UserInfo;
import com.biyanzhi.enums.RetError;

public class GetUserInfoTask extends BaseAsyncTask<UserInfo, Void, RetError> {
	private UserInfo info;

	@Override
	protected RetError doInBackground(UserInfo... params) {
		info = params[0];
		return info.getUserInfo();
	}

}
