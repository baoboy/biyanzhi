package com.biyanzhi.task;

import com.biyanzhi.data.PKData;
import com.biyanzhi.enums.RetError;

public class UpDatePK2Task extends BaseAsyncTask<PKData, Void, RetError> {
	private PKData pk;
	private String pk2_user_name;

	public UpDatePK2Task(String pk2_user_name) {
		this.pk2_user_name = pk2_user_name;
	}

	@Override
	protected RetError doInBackground(PKData... params) {
		pk = params[0];
		return pk.upDatePK2(pk2_user_name);
	}
}
