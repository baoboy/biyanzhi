package com.biyanzhi.parser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.biyanzhi.data.PictureScore;
import com.biyanzhi.data.PictureScoreUserList;
import com.biyanzhi.data.User;
import com.biyanzhi.data.result.Result;

public class GetPlayScoreUserListPaser implements IParser {

	@Override
	public Result<PictureScoreUserList> parse(JSONObject jsonObj)
			throws Exception {
		if (jsonObj == null) {
			return Result.defContentErrorResult();
		}
		JSONArray jsonArr = jsonObj.getJSONArray("users");
		if (jsonArr == null) {
			return Result.defContentErrorResult();
		}
		List<PictureScore> lists = new ArrayList<PictureScore>();
		for (int i = 0; i < jsonArr.length(); i++) {
			JSONObject obj = (JSONObject) jsonArr.opt(i);
			int picture_score = obj.getInt("picture_score");
			PictureScore score = new PictureScore();
			score.setPicture_score(picture_score);
			String userResult = obj.getString("user");
			if ("".equals(userResult) || "null".equals(userResult)) {
				continue;
			}
			JSONObject userObj = new JSONObject(userResult);
			User user = new User();
			user.setUser_id(userObj.getInt("user_id"));
			user.setUser_name(userObj.getString("user_name"));
			user.setUser_gender(userObj.getString("user_gender"));
			user.setUser_avatar(userObj.getString("user_avatar"));
			score.setUser(user);
			lists.add(score);

		}

		PictureScoreUserList list = new PictureScoreUserList();
		list.setLists(lists);
		Result<PictureScoreUserList> ret = new Result<PictureScoreUserList>();
		ret.setData(list);
		return ret;
	}
}
