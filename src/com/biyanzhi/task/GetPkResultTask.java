package com.biyanzhi.task;

import com.biyanzhi.data.PKResultList;
import com.biyanzhi.enums.RetError;

public class GetPkResultTask extends
		BaseAsyncTask<PKResultList, Void, RetError> {
	private PKResultList list;

	@Override
	protected RetError doInBackground(PKResultList... params) {
		list = params[0];
		return list.getPkResultList();
	}

}
