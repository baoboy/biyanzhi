package com.biyanzhi.parser;

import org.json.JSONObject;

import com.biyanzhi.data.User;
import com.biyanzhi.data.result.Result;

public class UserLoginPaser implements IParser {

	@Override
	public Result parse(JSONObject jsonObj) throws Exception {
		if (jsonObj == null) {
			return Result.defContentErrorResult();
		}

		String jsonArr = jsonObj.getString("user");
		if (jsonArr == null) {
			return Result.defContentErrorResult();
		}
		JSONObject obj = new JSONObject(jsonArr);
		int userID = obj.getInt("user_id");
		String userName = obj.getString("user_name");
		String userAvatar = obj.getString("user_avatar");
		String userGender = obj.getString("user_gender");
		String userBirthday = obj.getString("user_birthday");
		String user_address = obj.getString("user_address");
		String user_province = obj.getString("user_province");

		User member = new User();
		member.setUser_id(userID);
		member.setUser_name(userName);
		member.setUser_avatar(userAvatar);
		member.setUser_birthday(userBirthday);
		member.setUser_gender(userGender);
		member.setUser_address(user_address);
		member.setUser_province(user_province);
		Result ret = new Result();
		ret.setData(member);
		return ret;
	}
}
