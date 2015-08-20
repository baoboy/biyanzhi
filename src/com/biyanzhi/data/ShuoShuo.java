package com.biyanzhi.data;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.biyanzhi.data.result.ApiRequest;
import com.biyanzhi.data.result.Result;
import com.biyanzhi.enums.RetError;
import com.biyanzhi.enums.RetStatus;
import com.biyanzhi.parser.IParser;
import com.biyanzhi.parser.SimpleParser;
import com.biyanzhi.utils.BitmapUtils;

public class ShuoShuo {
	private static final String PUBLISH_SHUOSHUO_API = "addShuoShuo.do";
	private int shuoshuo_id = 0;
	private int publisher_id = 0;
	private String time = "";
	private String content = "";
	private List<ShuoShuoImage> images = new ArrayList<ShuoShuoImage>();
	private List<ShuoShuoComment> comments = new ArrayList<ShuoShuoComment>();
	private String publisher_name = "";
	private String publisher_avatar = "";
	private boolean isPraise;// 1 ÔÞ 0Î´ÔÞ
	private List<ShuoShuoPraise> praises = new ArrayList<ShuoShuoPraise>();
	private int state;

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public List<ShuoShuoPraise> getPraises() {
		return praises;
	}

	public void setPraises(List<ShuoShuoPraise> praises) {
		this.praises = praises;
	}

	public String getPublisher_name() {
		return publisher_name;
	}

	public void setPublisher_name(String publisher_name) {
		this.publisher_name = publisher_name;
	}

	public String getPublisher_avatar() {
		return publisher_avatar;
	}

	public void setPublisher_avatar(String publisher_avatar) {
		this.publisher_avatar = publisher_avatar;
	}

	public int getPublisher_id() {
		return publisher_id;
	}

	public void setPublisher_id(int publisher_id) {
		this.publisher_id = publisher_id;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<ShuoShuoImage> getImages() {
		return images;
	}

	public void setImages(List<ShuoShuoImage> images) {
		this.images = images;
	}

	public List<ShuoShuoComment> getComments() {
		return comments;
	}

	public void setComments(List<ShuoShuoComment> comments) {
		this.comments = comments;
	}

	public int getShuoshuo_id() {
		return shuoshuo_id;
	}

	public void setShuoshuo_id(int shuoshuo_id) {
		this.shuoshuo_id = shuoshuo_id;
	}

	public boolean isPraise() {
		return isPraise;
	}

	public void setPraise(boolean isPraise) {
		this.isPraise = isPraise;
	}

	@Override
	public String toString() {
		return "shuoshuo_id:" + this.shuoshuo_id + "  content:" + this.content
				+ "  images:" + this.images;
	}

	public RetError publishShuoShuo() {
		List<File> bytesimg = new ArrayList<File>();
		for (ShuoShuoImage img : this.images) {
			File file = BitmapUtils.getImageFile(img.getImg_url());
			if (file == null) {
				continue;
			}
			bytesimg.add(file);
		}
		IParser parser = new SimpleParser();
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("publisher_id", publisher_id);
		params.put("content", content);
		params.put("publisher_name", publisher_name);
		params.put("publisher_avatar", publisher_avatar);
		Result ret = ApiRequest.uploadFileArrayWithToken(PUBLISH_SHUOSHUO_API,
				params, bytesimg, parser);
		delImgFile(bytesimg);
		if (ret.getStatus() == RetStatus.SUCC) {
			return RetError.NONE;
		} else {
			return ret.getErr();
		}
	}

	private void delImgFile(List<File> files) {
		for (File file : files) {
			if (file.exists()) {
				file.delete();
			}
		}
	}
}
