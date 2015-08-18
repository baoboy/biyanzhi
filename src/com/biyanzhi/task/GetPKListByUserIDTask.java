package com.biyanzhi.task;

import com.biyanzhi.data.PKList;
import com.biyanzhi.enums.RetError;

public class GetPKListByUserIDTask extends
		BaseAsyncTask<PKList, Void, RetError> {
	private PKList list;
	private int pk_user_id;
	private int page;

	public GetPKListByUserIDTask(int pk_user_id, int page) {
		this.pk_user_id = pk_user_id;
		this.page = page;
	}

	@Override
	protected RetError doInBackground(PKList... params) {
		list = params[0];
		return list.getPKListByUserID(pk_user_id, page);
	}

}
