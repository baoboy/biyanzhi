package com.biyanzhi.data;

import java.io.Serializable;

public class PK2 implements Serializable {
	private int pk2_user_id;
	private String pk2_user_picture = "";
	private int pk2_ticket_count;

	public int getPk2_user_id() {
		return pk2_user_id;
	}

	public void setPk2_user_id(int pk2_user_id) {
		this.pk2_user_id = pk2_user_id;
	}

	public String getPk2_user_picture() {
		return pk2_user_picture;
	}

	public void setPk2_user_picture(String pk2_user_picture) {
		this.pk2_user_picture = pk2_user_picture;
	}

	public int getPk2_ticket_count() {
		return pk2_ticket_count;
	}

	public void setPk2_ticket_count(int pk2_ticket_count) {
		this.pk2_ticket_count = pk2_ticket_count;
	}

}
