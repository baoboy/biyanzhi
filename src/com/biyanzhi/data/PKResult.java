package com.biyanzhi.data;

public class PKResult {
	private int id;
	private int user_id;
	private String picture_id;
	private int user_win_count;
	private int user_fail_count;
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getPicture_id() {
		return picture_id;
	}

	public void setPicture_id(String picture_id) {
		this.picture_id = picture_id;
	}

	public int getUser_win_count() {
		return user_win_count;
	}

	public void setUser_win_count(int user_win_count) {
		this.user_win_count = user_win_count;
	}

	public int getUser_fail_count() {
		return user_fail_count;
	}

	public void setUser_fail_count(int user_fail_count) {
		this.user_fail_count = user_fail_count;
	}

}
