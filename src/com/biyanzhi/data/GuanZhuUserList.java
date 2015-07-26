package com.biyanzhi.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.biyanzhi.data.result.ApiRequest;
import com.biyanzhi.data.result.Result;
import com.biyanzhi.enums.RetError;
import com.biyanzhi.enums.RetStatus;
import com.biyanzhi.parser.GuanZhuListPaser;
import com.biyanzhi.parser.IParser;

public class GuanZhuUserList {
	private static final String GET_GUANZHU_LIST_USERS_API = "getGuanZhuUserListsByUserID.do";
	private static final String GET_MY_GUANZHU_LIST_USERS_API = "getMyGuanZhuUserListsByUserID.do";

	private int guanzhu_user_id;
	private List<User> lists = new ArrayList<User>();

	public int getGuanzhu_user_id() {
		return guanzhu_user_id;
	}

	public void setGuanzhu_user_id(int guanzhu_user_id) {
		this.guanzhu_user_id = guanzhu_user_id;
	}

	public List<User> getLists() {
		return lists;
	}

	public void setLists(List<User> lists) {
		this.lists = lists;
	}

	public RetError getGuanZhuListUsers() {
		IParser parser = new GuanZhuListPaser();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("guanzhu_user_id", guanzhu_user_id);
		Result ret = ApiRequest.request(GET_GUANZHU_LIST_USERS_API, params,
				parser);
		if (ret.getStatus() == RetStatus.SUCC) {
			GuanZhuUserList lists = (GuanZhuUserList) ret.getData();
			this.lists = lists.getLists();
			return RetError.NONE;
		} else {
			return ret.getErr();
		}
	}

	public RetError getMyGuanZhuListUsers() {
		IParser parser = new GuanZhuListPaser();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("user_id", guanzhu_user_id);
		Result ret = ApiRequest.request(GET_MY_GUANZHU_LIST_USERS_API, params,
				parser);
		if (ret.getStatus() == RetStatus.SUCC) {
			GuanZhuUserList lists = (GuanZhuUserList) ret.getData();
			this.lists = lists.getLists();
			return RetError.NONE;
		} else {
			return ret.getErr();
		}
	}
}
