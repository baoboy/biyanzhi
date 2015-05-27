package com.biyanzhi.task;

import com.biyanzhi.data.User;
import com.biyanzhi.enums.RetError;

public class VerifyCellPhoneTask extends BaseAsyncTask<User, Void, RetError> {
	private User register;

	@Override
	protected RetError doInBackground(User... params) {
		register = params[0];
		RetError ret = register.vefifyCellPhone();
		return ret;
	}
}
