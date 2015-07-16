package com.biyanzhi.task;

import com.biyanzhi.data.FeedBack;
import com.biyanzhi.enums.RetError;

public class AddFeedBackTask extends BaseAsyncTask<FeedBack, Void, RetError> {
	FeedBack fb = null;

	@Override
	protected RetError doInBackground(FeedBack... params) {
		fb = params[0];
		return fb.addFeedBack();
	}

}
