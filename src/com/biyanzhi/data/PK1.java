package com.biyanzhi.data;

public class PK1 {
	private int pk1_user_id;
	private String pk1_user_gender = "";
	private String pk1_user_picture = "";
	private int pk1_ticket_count;
	private int pk1_win_count;
	private int pk1_fail_count;

	public int getPk1_win_count() {
		return pk1_win_count;
	}

	public void setPk1_win_count(int pk1_win_count) {
		this.pk1_win_count = pk1_win_count;
	}

	public int getPk1_fail_count() {
		return pk1_fail_count;
	}

	public void setPk1_fail_count(int pk1_fail_count) {
		this.pk1_fail_count = pk1_fail_count;
	}

	public int getPk1_user_id() {
		return pk1_user_id;
	}

	public String getPk1_user_gender() {
		return pk1_user_gender;
	}

	public void setPk1_user_gender(String pk1_user_gender) {
		this.pk1_user_gender = pk1_user_gender;
	}

	public void setPk1_user_id(int pk1_user_id) {
		this.pk1_user_id = pk1_user_id;
	}

	public String getPk1_user_picture() {
		return pk1_user_picture;
	}

	public void setPk1_user_picture(String pk1_user_picture) {
		this.pk1_user_picture = pk1_user_picture;
	}

	public int getPk1_ticket_count() {
		return pk1_ticket_count;
	}

	public void setPk1_ticket_count(int pk1_ticket_count) {
		this.pk1_ticket_count = pk1_ticket_count;
	}

}
