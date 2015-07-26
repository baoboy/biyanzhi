package com.biyanzhi.parser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.biyanzhi.data.PK1;
import com.biyanzhi.data.PK2;
import com.biyanzhi.data.PKData;
import com.biyanzhi.data.PKList;
import com.biyanzhi.data.result.Result;

public class PKListParser implements IParser {

	@Override
	public Result<PKList> parse(JSONObject jsonObj) throws Exception {
		if (jsonObj == null) {
			return Result.defContentErrorResult();
		}
		JSONArray jsonArr = jsonObj.getJSONArray("pks");
		if (jsonArr == null) {
			return Result.defContentErrorResult();
		}
		List<PKData> lists = new ArrayList<PKData>();
		for (int i = 0; i < jsonArr.length(); i++) {
			JSONObject obj = (JSONObject) jsonArr.opt(i);
			int pk_id = obj.getInt("pk_id");
			int pk1_user_id = obj.getInt("pk1_user_id");
			String pk1_user_gender = obj.getString("pk1_user_gender");
			String pk1_user_picture = obj.getString("pk1_user_picture");
			int pk1_ticket_count = obj.getInt("pk1_ticket_count");
			int pk2_user_id = obj.getInt("pk2_user_id");
			String pk2_user_picture = obj.getString("pk2_user_picture");
			int pk2_ticket_count = obj.getInt("pk2_ticket_count");
			String pk_time = obj.getString("pk_time");
			boolean is_voted = obj.getBoolean("is_voted");
			int pk_state = obj.getInt("pk_state");
			int pk_winer_user_id = obj.getInt("pk_winer_user_id");
			PKData pk = new PKData();
			PK1 pk1 = new PK1();
			PK2 pk2 = new PK2();
			pk1.setPk1_ticket_count(pk1_ticket_count);
			pk1.setPk1_user_id(pk1_user_id);
			pk1.setPk1_user_gender(pk1_user_gender);
			pk1.setPk1_user_picture(pk1_user_picture);
			pk2.setPk2_ticket_count(pk2_ticket_count);
			pk2.setPk2_user_id(pk2_user_id);
			pk2.setPk2_user_picture(pk2_user_picture);
			pk.setPk1(pk1);
			pk.setPk2(pk2);
			pk.setPk_time(pk_time);
			pk.setPk_id(pk_id);
			pk.setIs_voted(is_voted);
			pk.setPk_state(pk_state);
			pk.setPk_winer_user_id(pk_winer_user_id);
			lists.add(pk);
		}
		PKList pl = new PKList();
		pl.setMlists(lists);
		Result<PKList> ret = new Result<PKList>();
		ret.setData(pl);
		return ret;
	}

}
