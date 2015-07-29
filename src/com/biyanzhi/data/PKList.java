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
import com.biyanzhi.parser.PKListParser;

public class PKList {
	private static final String GET_PK_LIST_URL = "getPKList.do";
	private static final String LOAD_MORE_PK_LIST = "loadMorePKList.do";
	private static final String GET_PK_ING_LIST_URL = "getPKIngList.do";
	private static final String LOAD_PK_ING_MORE_PK_LIST = "loadPKIngMorePKList.do";
	private static final String GET_PK_FINISHED_LIST_URL = "getPKFinishedList.do";
	private static final String LOAD_PK_FINISHED_ING_MORE_PK_LIST = "loadPKFinishedMorePKList.do";
	private List<PKData> mlists = new ArrayList<PKData>();
	private String pk_time = "";

	public String getPk_time() {
		return pk_time;
	}

	public void setPk_time(String pk_time) {
		this.pk_time = pk_time;
	}

	public List<PKData> getMlists() {
		return mlists;
	}

	public void setMlists(List<PKData> mlists) {
		this.mlists = mlists;
	}

	public RetError getPKList() {
		IParser parser = new PKListParser();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pk_time", pk_time);
		Result ret = ApiRequest.request(GET_PK_LIST_URL, params, parser);
		if (ret.getStatus() == RetStatus.SUCC) {
			PKList pk_lists = (PKList) ret.getData();
			this.mlists.clear();
			this.mlists = pk_lists.getMlists();
			return RetError.NONE;
		} else {
			return ret.getErr();
		}
	}

	public RetError loadMorePKList() {
		IParser parser = new PKListParser();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pk_time", pk_time);
		Result ret = ApiRequest.request(LOAD_MORE_PK_LIST, params, parser);
		if (ret.getStatus() == RetStatus.SUCC) {
			PKList pk_lists = (PKList) ret.getData();
			this.mlists.clear();
			this.mlists = pk_lists.getMlists();
			return RetError.NONE;
		} else {
			return ret.getErr();
		}
	}

	public RetError getPKIngList() {
		IParser parser = new PKListParser();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pk_time", pk_time);
		Result ret = ApiRequest.request(GET_PK_ING_LIST_URL, params, parser);
		if (ret.getStatus() == RetStatus.SUCC) {
			PKList pk_lists = (PKList) ret.getData();
			this.mlists.clear();
			this.mlists = pk_lists.getMlists();
			return RetError.NONE;
		} else {
			return ret.getErr();
		}
	}

	public RetError loadPKIngMorePKList() {
		IParser parser = new PKListParser();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pk_time", pk_time);
		Result ret = ApiRequest.request(LOAD_PK_ING_MORE_PK_LIST, params,
				parser);
		if (ret.getStatus() == RetStatus.SUCC) {
			PKList pk_lists = (PKList) ret.getData();
			this.mlists.clear();
			this.mlists = pk_lists.getMlists();
			return RetError.NONE;
		} else {
			return ret.getErr();
		}
	}

	public RetError getPKFinishedList() {
		IParser parser = new PKListParser();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pk_time", pk_time);
		Result ret = ApiRequest.request(GET_PK_FINISHED_LIST_URL, params,
				parser);
		if (ret.getStatus() == RetStatus.SUCC) {
			PKList pk_lists = (PKList) ret.getData();
			this.mlists.clear();
			this.mlists = pk_lists.getMlists();
			return RetError.NONE;
		} else {
			return ret.getErr();
		}
	}

	public RetError loadPKFinishedMorePKList() {
		IParser parser = new PKListParser();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pk_time", pk_time);
		Result ret = ApiRequest.request(LOAD_PK_FINISHED_ING_MORE_PK_LIST,
				params, parser);
		if (ret.getStatus() == RetStatus.SUCC) {
			PKList pk_lists = (PKList) ret.getData();
			this.mlists.clear();
			this.mlists = pk_lists.getMlists();
			return RetError.NONE;
		} else {
			return ret.getErr();
		}
	}
}
