package com.biyanzhi.parser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.biyanzhi.data.GuanZhuUserList;
import com.biyanzhi.data.User;
import com.biyanzhi.data.result.Result;

public class GuanZhuListPaser implements IParser {

	@Override
	public Result<GuanZhuUserList> parse(JSONObject jsonObj) throws Exception {
		if (jsonObj == null) {
			return Result.defContentErrorResult();
		}
		JSONArray jsonArr = jsonObj.getJSONArray("users");
		if (jsonArr == null) {
			return Result.defContentErrorResult();
		}
		List<User> lists = new ArrayList<User>();
		for (int i = 0; i < jsonArr.length(); i++) {
			JSONObject obj = (JSONObject) jsonArr.opt(i);
			int userID = obj.getInt("user_id");
			String userName = obj.getString("user_name");
			String userAvatar = obj.getString("user_avatar");
			String user_address = obj.getString("user_address");
			String user_gender = obj.getString("user_gender");
			User user = new User();
			user.setUser_address(user_address);
			user.setUser_avatar(userAvatar);
			user.setUser_id(userID);
			user.setUser_name(userName);
			user.setUser_gender(user_gender);
			lists.add(user);
		}
		GuanZhuUserList list = new GuanZhuUserList();
		list.setLists(lists);
		Result<GuanZhuUserList> ret = new Result<GuanZhuUserList>();
		ret.setData(list);
		return ret;
	}

}
