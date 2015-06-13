package com.biyanzhi.task;

import com.biyanzhi.data.GuanZhuUserList;
import com.biyanzhi.enums.RetError;

public class GetGuanZhuListTask extends
		BaseAsyncTask<GuanZhuUserList, Void, RetError> {
	private GuanZhuUserList list;

	@Override
	protected RetError doInBackground(GuanZhuUserList... params) {
		list = params[0];
		return list.getGuanZhuListUsers();
	}

}
