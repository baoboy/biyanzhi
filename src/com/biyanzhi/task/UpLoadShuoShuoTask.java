package com.biyanzhi.task;

import com.biyanzhi.data.ShuoShuo;
import com.biyanzhi.enums.RetError;

public class UpLoadShuoShuoTask extends BaseAsyncTask<ShuoShuo, Void, RetError> {
	private ShuoShuo shuo;

	@Override
	protected RetError doInBackground(ShuoShuo... params) {
		shuo = params[0];
		return shuo.publishShuoShuo();
	}

}
