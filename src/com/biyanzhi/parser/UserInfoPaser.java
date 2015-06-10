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
import com.biyanzhi.data.User;
import com.biyanzhi.data.UserInfo;
import com.biyanzhi.data.result.Result;

public class UserInfoPaser implements IParser {

	@Override
	public Result<PictureList> parse(JSONObject jsonObj) throws Exception {
		if (jsonObj == null) {
			return Result.defContentErrorResult();
		}
		String strUser = jsonObj.getString("user");

		JSONObject objUser = new JSONObject(strUser);
		int userID = objUser.getInt("user_id");
		String userName = objUser.getString("user_name");
		String userAvatar = objUser.getString("user_avatar");
		String userGender = objUser.getString("user_gender");
		String userBirthday = objUser.getString("user_birthday");
		String user_address = objUser.getString("user_address");
		String user_province = objUser.getString("user_province");
		int guanzhu_count = objUser.getInt("guanzhu_count");
		User member = new User();
		member.setUser_id(userID);
		member.setUser_name(userName);
		member.setUser_avatar(userAvatar);
		member.setUser_birthday(userBirthday);
		member.setUser_gender(userGender);
		member.setUser_address(user_address);
		member.setUser_province(user_province);
		member.setGuanzhu_count(guanzhu_count);
		JSONArray jsonArr = jsonObj.getJSONArray("pictures");
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
			int picture_image_width = obj.getInt("picture_image_width");
			int picture_image_height = obj.getInt("picture_image_height");
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
			picture.setPicture_image_height(picture_image_height);
			picture.setPicture_image_width(picture_image_width);
			picture.setScore_number(score_number);
			picture.setComments(comments);
			lists.add(picture);
		}
		UserInfo info = new UserInfo();
		info.setPictureList(lists);
		info.setUser(member);
		Result ret = new Result();
		ret.setData(info);
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
