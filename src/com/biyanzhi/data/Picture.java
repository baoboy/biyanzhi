package com.biyanzhi.data;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.biyanzhi.data.result.ApiRequest;
import com.biyanzhi.data.result.Result;
import com.biyanzhi.enums.RetError;
import com.biyanzhi.enums.RetStatus;
import com.biyanzhi.parser.IParser;
import com.biyanzhi.parser.SimpleParser;
import com.biyanzhi.utils.BitmapUtils;

public class Picture implements Serializable {
	private static final String PUBLISH_PICTURE = "addpicture.do";
	private int picture_id;
	private int publisher_id = 0;
	private String publish_time = "";
	private String content = "";
	private String publisher_name = "";
	private String publisher_avatar = "";
	private List<PictureImage> images = new ArrayList<PictureImage>();
	private String picture_image_url = "";
	private int average_score;
	private int picture_image_height;
	private int picture_image_width;
	private int score_number;// �������
	private List<Comment> comments = new ArrayList<Comment>();

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public int getScore_number() {
		return score_number;
	}

	public void setScore_number(int score_number) {
		this.score_number = score_number;
	}

	public int getPicture_id() {
		return picture_id;
	}

	public void setPicture_id(int picture_id) {
		this.picture_id = picture_id;
	}

	public int getPublisher_id() {
		return publisher_id;
	}

	public void setPublisher_id(int publisher_id) {
		this.publisher_id = publisher_id;
	}

	public String getPublish_time() {
		return publish_time;
	}

	public void setPublish_time(String publish_time) {
		this.publish_time = publish_time;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public List<PictureImage> getImages() {
		return images;
	}

	public void setImages(List<PictureImage> images) {
		this.images = images;
	}

	public String getPicture_image_url() {
		return picture_image_url;
	}

	public void setPicture_image_url(String picture_image_url) {
		this.picture_image_url = picture_image_url;
	}

	public int getAverage_score() {
		return average_score;
	}

	public void setAverage_score(int average_score) {
		this.average_score = average_score;
	}

	public int getPicture_image_height() {
		return picture_image_height;
	}

	public void setPicture_image_height(int picture_image_height) {
		this.picture_image_height = picture_image_height;
	}

	public int getPicture_image_width() {
		return picture_image_width;
	}

	public void setPicture_image_width(int picture_image_width) {
		this.picture_image_width = picture_image_width;
	}

	public RetError publishPicture() {
		int wh[] = BitmapUtils.getBitmapHeightAndWidth(picture_image_url);
		File file = BitmapUtils.getImageFile(picture_image_url);
		IParser parser = new SimpleParser();
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("publisher_id", publisher_id);
		params.put("publish_time", publish_time);
		params.put("content", content);
		params.put("publisher_name", publisher_name);
		params.put("publisher_avatar", publisher_avatar);
		params.put("picture_image_width", wh[1]);
		params.put("picture_image_height", wh[0]);

		Result ret = ApiRequest.requestWithFile(PUBLISH_PICTURE, params, file,
				parser);
		if (null != file & file.exists()) {
			file.delete();
		}
		if (ret.getStatus() == RetStatus.SUCC) {
			return RetError.NONE;
		} else {
			return ret.getErr();
		}
	}
}
