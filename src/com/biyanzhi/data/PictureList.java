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
import com.biyanzhi.parser.MapParser;
import com.biyanzhi.parser.PictureListParser;

public class PictureList {
	private static final String GET_PICTURELIST_URL = "getpicturelists.do";
	private static final String LOAD_MORE_PICTURELIST_URL = "loadMorePictureList.do";

	private List<Picture> pictureList = new ArrayList<Picture>();
	private String publish_time = "";

	public String getPublish_time() {
		return publish_time;
	}

	public void setPublish_time(String publish_time) {
		this.publish_time = publish_time;
	}

	public void setPictureList(List<Picture> pictureList) {
		this.pictureList = pictureList;
	}

	public List<Picture> getPictureList() {
		return pictureList;
	}

	public RetError getPictureListFromServer() {
		IParser parser = new PictureListParser();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("publish_time", publish_time);
		Result ret = ApiRequest.request(GET_PICTURELIST_URL, params, parser);
		if (ret.getStatus() == RetStatus.SUCC) {
			PictureList lists = (PictureList) ret.getData();
			this.pictureList = lists.getPictureList();
			return RetError.NONE;
		} else {
			return ret.getErr();
		}
	}

	public RetError loadMorePictureList() {
		IParser parser = new PictureListParser();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("publish_time", publish_time);
		Result ret = ApiRequest.request(LOAD_MORE_PICTURELIST_URL, params,
				parser);
		if (ret.getStatus() == RetStatus.SUCC) {
			PictureList lists = (PictureList) ret.getData();
			this.pictureList = lists.getPictureList();
			return RetError.NONE;
		} else {
			return ret.getErr();
		}
	}
}
