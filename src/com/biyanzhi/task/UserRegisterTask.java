package com.biyanzhi.task;

import com.biyanzhi.data.User;
import com.biyanzhi.enums.RetError;

public class UserRegisterTask extends BaseAsyncTask<User, Void, RetError> {
	private User user;

	@Override
	protected RetError doInBackground(User... params) {
		user = params[0];
		RetError ret = user.userRegister();
		return ret;
	}

}
