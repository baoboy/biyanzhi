package com.biyanzhi.data;

public class ShuoShuoPraise {
	private int user_id;
	private int shuoshuo_id;
	private String user_avatar = "";
	private String user_name = "";

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_avatar() {
		return user_avatar;
	}

	public void setUser_avatar(String user_avatar) {
		this.user_avatar = user_avatar;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getShuoshuo_id() {
		return shuoshuo_id;
	}

	public void setShuoshuo_id(int shuoshuo_id) {
		this.shuoshuo_id = shuoshuo_id;
	}

}
