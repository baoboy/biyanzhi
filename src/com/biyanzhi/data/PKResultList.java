package com.biyanzhi.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.biyanzhi.data.result.ApiRequest;
import com.biyanzhi.data.result.Result;
import com.biyanzhi.enums.RetError;
import com.biyanzhi.enums.RetStatus;
import com.biyanzhi.parser.IParser;
import com.biyanzhi.parser.PKResultListParser;

public class PKResultList {
	private static final String GETPKRESULTLIST_API = "getPkResultList.do";
	private List<PKResult> lists = new ArrayList<PKResult>();

	public List<PKResult> getLists() {
		return lists;
	}

	public void setLists(List<PKResult> lists) {
		this.lists = lists;
	}

	public RetError getPkResultList() {
		IParser parser = new PKResultListParser();
		Map<String, Object> params = new HashMap<String, Object>();
		Result ret = ApiRequest.request(GETPKRESULTLIST_API, params, parser);
		if (ret.getStatus() == RetStatus.SUCC) {
			PKResultList pk_lists = (PKResultList) ret.getData();
			this.lists.clear();
			this.lists = pk_lists.getLists();
			return RetError.NONE;
		} else {
			return ret.getErr();
		}
	}

}
