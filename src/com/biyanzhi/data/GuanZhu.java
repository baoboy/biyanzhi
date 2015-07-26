package com.biyanzhi.data;

import java.util.HashMap;

import com.biyanzhi.data.result.ApiRequest;
import com.biyanzhi.data.result.Result;
import com.biyanzhi.enums.RetError;
import com.biyanzhi.enums.RetStatus;
import com.biyanzhi.parser.IParser;
import com.biyanzhi.parser.SimpleParser;
import com.biyanzhi.utils.SharedUtils;

public class GuanZhu {
	private static final String CANCLE_GUANZHU_API = "cancleGuanZhu.do";
	private static final String ADD_GUANZHU_API = "addGuanZhu.do";
	private int user_id;
	private int guanzhu_user_id;

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getGuanzhu_user_id() {
		return guanzhu_user_id;
	}

	public void setGuanzhu_user_id(int guanzhu_user_id) {
		this.guanzhu_user_id = guanzhu_user_id;
	}

	public RetError addGuanZhu(String guanzhu_user_chat_id) {
		IParser parser = new SimpleParser();
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("guanzhu_user_id", guanzhu_user_id);
		params.put("user_name", SharedUtils.getAPPUserName());
		params.put("guanzhu_user_chat_id", guanzhu_user_chat_id);

		Result ret = ApiRequest.request(ADD_GUANZHU_API, params, parser);
		if (ret.getStatus() == RetStatus.SUCC) {
			return RetError.NONE;
		} else {
			return ret.getErr();
		}
	}

	public RetError cancleGuanZhu() {
		IParser parser = new SimpleParser();
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("guanzhu_user_id", guanzhu_user_id);
		params.put("user_id", user_id);
		Result ret = ApiRequest.request(CANCLE_GUANZHU_API, params, parser);
		if (ret.getStatus() == RetStatus.SUCC) {
			return RetError.NONE;
		} else {
			return ret.getErr();
		}
	}
}
