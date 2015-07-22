package com.biyanzhi.task;

import com.biyanzhi.data.PictureList;
import com.biyanzhi.enums.RetError;

public class GetPictureListByUserIDTask extends
		BaseAsyncTask<PictureList, Void, RetError> {
	private PictureList list;
	private int publicsher_user_id;

	public GetPictureListByUserIDTask(int publicsher_user_id) {
		this.publicsher_user_id = publicsher_user_id;
	}

	@Override
	protected RetError doInBackground(PictureList... params) {
		list = params[0];
		return list.getPictureListByUserID(publicsher_user_id);
	}

}
