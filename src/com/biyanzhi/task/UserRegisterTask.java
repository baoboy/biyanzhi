package com.biyanzhi.task;

import com.biyanzhi.data.User;
import com.biyanzhi.enums.RetError;

public class UserRegisterTask extends BaseAsyncTask<User, Void, RetError> {
	private User user;

	@Override
	protected RetError doInBackground(User... params) {
		user = params[0];
		RetError ret = user.userRegister();
		// SharedUtils.setAPPUserAddress(user.getUser_address());
		// SharedUtils.setAPPUserAvatar(user.getUser_avatar());
		// SharedUtils.setAPPUserBirthday(user.getUser_birthday());
		// SharedUtils.setAPPUserGender(user.getUser_gender());
		// SharedUtils.setAPPUserName(user.getUser_name());
		// SharedUtils.setAPPUserProvince(user.getUser_province());
		return ret;
	}

}
