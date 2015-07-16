package com.biyanzhi.data;

import java.util.HashMap;

import com.biyanzhi.data.result.ApiRequest;
import com.biyanzhi.data.result.Result;
import com.biyanzhi.enums.RetError;
import com.biyanzhi.enums.RetStatus;
import com.biyanzhi.parser.IParser;
import com.biyanzhi.parser.SimpleParser;
import com.biyanzhi.utils.SharedUtils;

public class PictureScore {
	private static final String SEND_SCORE_API = "addpicturescore.do";
	private int user_id;
	private int picture_id;
	private int picture_score;
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getPicture_id() {
		return picture_id;
	}

	public void setPicture_id(int picture_id) {
		this.picture_id = picture_id;
	}

	public int getPicture_score() {
		return picture_score;
	}

	public void setPicture_score(int picture_score) {
		this.picture_score = picture_score;
	}

	public RetError sendScore() {
		if (user == null) {
			return RetError.NONE;

		}
		IParser parser = new SimpleParser();
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("user_id", user_id);
		params.put("picture_score", picture_score);
		params.put("picture_id", picture_id);
		params.put("picture_publisher_id", user.getUser_id());
		params.put("user_name", SharedUtils.getAPPUserName());
		Result ret = ApiRequest.request(SEND_SCORE_API, params, parser);
		if (ret.getStatus() == RetStatus.SUCC) {
			return RetError.NONE;
		} else {
			return ret.getErr();
		}
	}
}
