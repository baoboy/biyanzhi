package com.biyanzhi.task;

import com.biyanzhi.data.GuanZhu;
import com.biyanzhi.enums.RetError;

public class AddGuanZhuTask extends BaseAsyncTask<GuanZhu, Void, RetError> {
	private GuanZhu guanzhu;
	private String guanzhu_user_chat_id;

	public AddGuanZhuTask(String guanzhu_user_chat_id) {
		this.guanzhu_user_chat_id = guanzhu_user_chat_id;
	}

	@Override
	protected RetError doInBackground(GuanZhu... params) {
		guanzhu = params[0];
		return guanzhu.addGuanZhu(guanzhu_user_chat_id);
	}

}
