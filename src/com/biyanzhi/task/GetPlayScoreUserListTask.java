package com.biyanzhi.task;

import com.biyanzhi.data.PictureScoreUserList;
import com.biyanzhi.enums.RetError;

public class GetPlayScoreUserListTask extends
		BaseAsyncTask<PictureScoreUserList, Void, RetError> {
	private PictureScoreUserList list;

	@Override
	protected RetError doInBackground(PictureScoreUserList... params) {
		list = params[0];
		return list.getPlayScoreUserListsByPictureID();
	}

}
