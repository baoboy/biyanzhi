package com.biyanzhi.data;

import java.util.HashMap;

import com.biyanzhi.data.result.ApiRequest;
import com.biyanzhi.data.result.Result;
import com.biyanzhi.enums.RetError;
import com.biyanzhi.enums.RetStatus;
import com.biyanzhi.parser.IParser;
import com.biyanzhi.parser.SimpleParser;

public class GuanZhu {
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

	public RetError addGuanZhu() {
		IParser parser = new SimpleParser();
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("guanzhu_user_id", guanzhu_user_id);
		Result ret = ApiRequest.request(ADD_GUANZHU_API, params, parser);
		if (ret.getStatus() == RetStatus.SUCC) {
			return RetError.NONE;
		} else {
			return ret.getErr();
		}
	}
}
