package com.biyanzhi.task;

import com.biyanzhi.data.Picture;
import com.biyanzhi.enums.RetError;

public class UpdatePicturePublishTimeTask extends
		BaseAsyncTask<Picture, Void, RetError> {
	private Picture picture;

	@Override
	protected RetError doInBackground(Picture... params) {
		picture = params[0];
		return picture.updatePicturePublishTime();
	}

}
