package com.biyanzhi.task;

import com.biyanzhi.data.PKList;
import com.biyanzhi.enums.RetError;

public class GetPKListByUserIDTask extends
		BaseAsyncTask<PKList, Void, RetError> {
	private PKList list;
	private int pk_user_id;

	public GetPKListByUserIDTask(int pk_user_id) {
		this.pk_user_id = pk_user_id;
	}

	@Override
	protected RetError doInBackground(PKList... params) {
		list = params[0];
		return list.getPKListByUserID(pk_user_id);
	}

}
