package com.biyanzhi.task;

import com.biyanzhi.data.PKList;
import com.biyanzhi.enums.RetError;

public class GetPKIngListTask extends BaseAsyncTask<PKList, Void, RetError> {
	private PKList list;

	@Override
	protected RetError doInBackground(PKList... params) {
		list = params[0];
		return list.getPKIngList();
	}

}
