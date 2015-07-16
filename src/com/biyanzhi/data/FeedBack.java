package com.biyanzhi.data;

import java.util.HashMap;

import com.biyanzhi.data.result.ApiRequest;
import com.biyanzhi.data.result.Result;
import com.biyanzhi.data.result.StringResult;
import com.biyanzhi.enums.RetError;
import com.biyanzhi.enums.RetStatus;
import com.biyanzhi.parser.IParser;
import com.biyanzhi.parser.SimpleParser;

public class FeedBack {
	private static final String ADD_FEEDBACK_API = "addFeedBack.do";
	private int user_id;
	private String feedback_content = "";
	private String feedback_time = "";

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getFeedback_content() {
		return feedback_content;
	}

	public void setFeedback_content(String feedback_content) {
		this.feedback_content = feedback_content;
	}

	public String getFeedback_time() {
		return feedback_time;
	}

	public void setFeedback_time(String feedback_time) {
		this.feedback_time = feedback_time;
	}

	public RetError addFeedBack() {
		IParser parser = new SimpleParser();
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("feedback_content", feedback_content);
		Result ret = ApiRequest.request(ADD_FEEDBACK_API, params, parser);
		if (ret.getStatus() == RetStatus.SUCC) {
			return RetError.NONE;
		} else {
			return ret.getErr();
		}

	}
}
