package com.biyanzhi.data;

public class ShuoShuoImage {
	private int img_id = 0;
	private int shuoshuo_id = 0;
	private String img_url = "";

	public int getImg_id() {
		return img_id;
	}

	public void setImg_id(int img_id) {
		this.img_id = img_id;
	}

	public int getShuoshuo_id() {
		return shuoshuo_id;
	}

	public void setShuoshuo_id(int shuoshuo_id) {
		this.shuoshuo_id = shuoshuo_id;
	}

	public String getImg_url() {
		return img_url;
	}

	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}

	@Override
	public String toString() {
		return "img_id" + this.img_id + "  img_url:" + this.img_url;
	}
}
