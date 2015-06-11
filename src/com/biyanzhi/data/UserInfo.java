package com.biyanzhi.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.biyanzhi.data.result.ApiRequest;
import com.biyanzhi.data.result.Result;
import com.biyanzhi.enums.RetError;
import com.biyanzhi.enums.RetStatus;
import com.biyanzhi.parser.IParser;
import com.biyanzhi.parser.UserInfoPaser;

public class UserInfo {
	private static final String GET_USER_INFO_API = "getUserInfo.do";
	private int user_id;
	private User user;
	private List<Picture> pictureList = new ArrayList<Picture>();

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Picture> getPictureList() {
		return pictureList;
	}

	public void setPictureList(List<Picture> pictureList) {
		this.pictureList = pictureList;
	}

	public RetError getUserInfo() {
		IParser parser = new UserInfoPaser();
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("publicsher_user_id", user_id);
		Result ret = ApiRequest.request(GET_USER_INFO_API, params, parser);
		if (ret.getStatus() == RetStatus.SUCC) {
			UserInfo info = (UserInfo) ret.getData();
			this.user = info.getUser();
			this.pictureList = info.getPictureList();
			return RetError.NONE;
		} else {
			return ret.getErr();
		}
	}
}
