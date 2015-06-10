package com.biyanzhi.task;

import com.biyanzhi.data.GuanZhu;
import com.biyanzhi.enums.RetError;

public class AddGuanZhuTask extends BaseAsyncTask<GuanZhu, Void, RetError> {
	private GuanZhu guanzhu;

	@Override
	protected RetError doInBackground(GuanZhu... params) {
		guanzhu = params[0];
		return guanzhu.addGuanZhu();
	}

}
