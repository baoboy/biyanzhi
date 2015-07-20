package com.biyanzhi.task;

import com.biyanzhi.data.PictureList;
import com.biyanzhi.enums.RetError;

public class GetPictureListMoreByUserIDTask extends
		BaseAsyncTask<PictureList, Void, RetError> {
	private PictureList list;
	private int publicsher_user_id;

	public GetPictureListMoreByUserIDTask(int publicsher_user_id) {
		this.publicsher_user_id = publicsher_user_id;
	}

	@Override
	protected RetError doInBackground(PictureList... params) {
		list = params[0];
		return list.getPictureListMoreByUserID(publicsher_user_id);
	}

}
