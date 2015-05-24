package com.biyanzhi.parser;

import org.json.JSONObject;

import com.biyanzhi.data.User;
import com.biyanzhi.data.result.Result;

public class UserSelfPaser implements IParser {

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
		int userID = obj.getInt("userID");
		String userName = obj.getString("userName");
		String userAvatar = obj.getString("userAvatar");
		String userGender = obj.getString("userGender");
		String userBirthday = obj.getString("userBirthday");
		User member = new User();
		member.setUser_id(userID);
		member.setUser_name(userName);
		member.setUser_avatar(userAvatar);
		member.setUser_birthday(userBirthday);
		member.setUser_gender(userGender);
		Result ret = new Result();
		ret.setData(member);
		return ret;
	}
}
