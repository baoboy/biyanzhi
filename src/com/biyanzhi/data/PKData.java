package com.biyanzhi.data;

import java.util.HashMap;

import com.biyanzhi.data.result.ApiRequest;
import com.biyanzhi.data.result.Result;
import com.biyanzhi.enums.RetError;
import com.biyanzhi.enums.RetStatus;
import com.biyanzhi.parser.IParser;
import com.biyanzhi.parser.SimpleParser;

public class PKData {
	private static final String ADD_PK_URL = "addPK.do";
	private PK1 pk1;
	private PK2 pk2;
	private String pk_time = "";

	public String getPk_time() {
		return pk_time;
	}

	public void setPk_time(String pk_time) {
		this.pk_time = pk_time;
	}

	public PK1 getPk1() {
		return pk1;
	}

	public void setPk1(PK1 pk1) {
		this.pk1 = pk1;
	}

	public PK2 getPk2() {
		return pk2;
	}

	public void setPk2(PK2 pk2) {
		this.pk2 = pk2;
	}

	public RetError addPK() {
		IParser parser = new SimpleParser();
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("pk1_user_id", pk1.getPk1_user_id());
		params.put("pk1_user_picture", pk1.getPk1_user_picture());
		params.put("pk1_ticket_count", pk1.getPk1_ticket_count());
		params.put("pk2_user_id", pk2.getPk2_user_id());
		params.put("pk2_user_picture", pk2.getPk2_user_picture());
		params.put("pk2_ticket_count", pk2.getPk2_ticket_count());
		Result ret = ApiRequest.request(ADD_PK_URL, params, parser);
		if (ret.getStatus() == RetStatus.SUCC) {
			return RetError.NONE;
		} else {
			return ret.getErr();
		}
	}
}
