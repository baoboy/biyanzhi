package com.biyanzhi.data;

public class Share {
	private String share_name;
	private int share_img_id;

	public Share(String share_name, int share_img_id) {
		this.share_img_id = share_img_id;
		this.share_name = share_name;
	}

	public String getShare_name() {
		return share_name;
	}

	public void setShare_name(String share_name) {
		this.share_name = share_name;
	}

	public int getShare_img_id() {
		return share_img_id;
	}

	public void setShare_img_id(int share_img_id) {
		this.share_img_id = share_img_id;
	}

}
