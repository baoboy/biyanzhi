package com.biyanzhi.task;

import com.biyanzhi.data.Picture;
import com.biyanzhi.enums.RetError;

public class GetPictureTask extends BaseAsyncTask<Picture, Void, RetError> {
	private Picture pic;

	@Override
	protected RetError doInBackground(Picture... params) {
		pic = params[0];
		return pic.getPictureByID();
	}

}
