package com.biyanzhi.parser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.biyanzhi.data.PKResult;
import com.biyanzhi.data.PKResultList;
import com.biyanzhi.data.result.Result;
import com.google.gson.Gson;

public class PKResultListParser implements IParser {

	@Override
	public Result<PKResultList> parse(JSONObject jsonObj) throws Exception {
		if (jsonObj == null) {
			return Result.defContentErrorResult();
		}
		JSONArray jsonArr = jsonObj.getJSONArray("results");
		if (jsonArr == null) {
			return Result.defContentErrorResult();
		}
		List<PKResult> lists = new ArrayList<PKResult>();
		for (int i = 0; i < jsonArr.length(); i++) {
			JSONObject obj = (JSONObject) jsonArr.opt(i);
			Gson gson = new Gson();
			PKResult result = gson.fromJson(obj.toString(), PKResult.class);
			lists.add(result);
		}

		PKResultList list = new PKResultList();
		list.setLists(lists);
		Result<PKResultList> ret = new Result<PKResultList>();
		ret.setData(list);
		return ret;
	}
}
