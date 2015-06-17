package com.biyanzhi.task;

import com.biyanzhi.data.PictureList;
import com.biyanzhi.enums.RetError;

public class GetGirlBangPictureListTask extends
		BaseAsyncTask<PictureList, Void, RetError> {
	private PictureList list;

	@Override
	protected RetError doInBackground(PictureList... params) {
		list = params[0];
		return list.getGirlBangPictureList();
	}

}
