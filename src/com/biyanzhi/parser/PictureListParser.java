package com.biyanzhi.parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.biyanzhi.data.Comment;
import com.biyanzhi.data.Picture;
import com.biyanzhi.data.PictureList;
import com.biyanzhi.data.result.Result;

public class PictureListParser implements IParser {

	@Override
	public Result<PictureList> parse(JSONObject jsonObj) throws Exception {
		if (jsonObj == null) {
			return Result.defContentErrorResult();
		}
		JSONArray jsonArr = jsonObj.getJSONArray("pictures");
		if (jsonArr == null) {
			return Result.defContentErrorResult();
		}
		List<Picture> lists = new ArrayList<Picture>();
		for (int i = 0; i < jsonArr.length(); i++) {
			JSONObject obj = (JSONObject) jsonArr.opt(i);
			int picture_id = obj.getInt("picture_id");
			int publisher_id = obj.getInt("publisher_id");
			String publisher_name = obj.getString("publisher_name");
			String publisher_avatar = obj.getString("publisher_avatar");
			String publish_time = obj.getString("publish_time");
			String content = obj.getString("content");
			String picture_image_url = obj.getString("picture_image_url");
			int average_score = obj.getInt("average_score");
			int score_number = obj.getInt("score_number");
			// comments
			JSONArray commentsJson = obj.getJSONArray("comments");
			List<Comment> comments = new ArrayList<Comment>();
			for (int j = 0; j < commentsJson.length(); j++) {
				JSONObject obj2 = (JSONObject) commentsJson.opt(j);
				int comment_id = obj2.getInt("comment_id");
				int comment_publisher_id = obj2.getInt("publisher_id");
				String comment_time = obj2.getString("comment_time");
				String comment_content = obj2.getString("comment_content");
				String comm_publisher_name = obj2.getString("publisher_name");
				String comm_publisher_avatar = obj2
						.getString("publisher_avatar");
				String reply_someone_name = obj2
						.getString("reply_someone_name");
				int reply_someone_id = obj2.getInt("reply_someone_id");
				Comment comment = new Comment();
				comment.setComment_content(comment_content);
				comment.setComment_id(comment_id);
				comment.setComment_time(comment_time);
				comment.setPublisher_id(comment_publisher_id);
				comment.setPicture_id(picture_id);
				comment.setPublisher_avatar(comm_publisher_avatar);
				comment.setPublisher_name(comm_publisher_name);
				comment.setReply_someone_name(reply_someone_name);
				comment.setReply_someone_id(reply_someone_id);
				comments.add(comment);
			}
			sortComment(comments);
			Picture picture = new Picture();
			picture.setContent(content);
			picture.setPicture_id(picture_id);
			picture.setPublish_time(publish_time);
			picture.setPublisher_avatar(publisher_avatar);
			picture.setPublisher_id(publisher_id);
			picture.setPublisher_name(publisher_name);
			picture.setPicture_image_url(picture_image_url);
			picture.setAverage_score(average_score);
			picture.setScore_number(score_number);
			picture.setComments(comments);
			lists.add(picture);
		}
		PictureList cl = new PictureList();
		cl.setPictureList(lists);
		Result<PictureList> ret = new Result<PictureList>();
		ret.setData(cl);
		return ret;
	}

	private void sortComment(List<Comment> comments) {
		Collections.sort(comments, new Comparator<Comment>() {
			@Override
			public int compare(Comment lhs, Comment rhs) {
				return rhs.getComment_time().compareTo(lhs.getComment_time());
			}
		});

	}
}
