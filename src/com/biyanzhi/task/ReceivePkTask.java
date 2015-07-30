package com.biyanzhi.task;

import com.biyanzhi.data.PKData;
import com.biyanzhi.enums.RetError;

public class ReceivePkTask extends BaseAsyncTask<PKData, Void, RetError> {
	private PKData pk;

	@Override
	protected RetError doInBackground(PKData... params) {
		pk = params[0];
		return pk.receivePK();
	}

}
