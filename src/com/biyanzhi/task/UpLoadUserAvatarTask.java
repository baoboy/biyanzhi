package com.biyanzhi.task;

import com.biyanzhi.data.User;
import com.biyanzhi.enums.RetError;

public class UpLoadUserAvatarTask extends BaseAsyncTask<User, Void, RetError> {
	private User member;

	@Override
	protected RetError doInBackground(User... params) {
		member = params[0];
		return member.upLoadAvatar();
	}

}
