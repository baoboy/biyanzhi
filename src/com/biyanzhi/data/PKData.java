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
	private static final String UPDATE_PK2_URL = "upDatePK2.do";
	private static final String UPDATE_PK2_TICKET_COUNT_URL = "upDatePK2TicketCount.do";
	private static final String UPDATE_PK1_TICKET_COUNT_URL = "upDatePK1TicketCount.do";

	private PK1 pk1;
	private PK2 pk2;
	private String pk_time = "";
	private int pk_id;
	private boolean is_voted;// 是否已经投票
	private int pk_state;// 0 PK中 ，1 PK结束
	private int pk_winer_user_id;

	public int getPk_state() {
		return pk_state;
	}

	public void setPk_state(int pk_state) {
		this.pk_state = pk_state;
	}

	public int getPk_winer_user_id() {
		return pk_winer_user_id;
	}

	public void setPk_winer_user_id(int pk_winer_user_id) {
		this.pk_winer_user_id = pk_winer_user_id;
	}

	public boolean isIs_voted() {
		return is_voted;
	}

	public void setIs_voted(boolean is_voted) {
		this.is_voted = is_voted;
	}

	public int getPk_id() {
		return pk_id;
	}

	public void setPk_id(int pk_id) {
		this.pk_id = pk_id;
	}

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
		params.put("pk1_user_gender", pk1.getPk1_user_gender());
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

	public RetError upDatePK2(String pk2_user_name) {
		IParser parser = new SimpleParser();
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("pk_id", pk_id);
		params.put("pk2_user_id", pk2.getPk2_user_id());
		params.put("pk2_user_picture", pk2.getPk2_user_picture());
		params.put("pk1_user_id", pk1.getPk1_user_id());
		params.put("pk2_user_name", pk2_user_name);
		Result ret = ApiRequest.request(UPDATE_PK2_URL, params, parser);
		if (ret.getStatus() == RetStatus.SUCC) {
			return RetError.NONE;
		} else {
			return ret.getErr();
		}
	}

	public RetError upDatePK2TicketCount() {
		IParser parser = new SimpleParser();
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("pk_id", pk_id);
		params.put("pk2_ticket_count", pk2.getPk2_ticket_count());
		params.put("pk2_user_id", pk2.getPk2_user_id());
		params.put("pk1_user_id", pk1.getPk1_user_id());
		Result ret = ApiRequest.request(UPDATE_PK2_TICKET_COUNT_URL, params,
				parser);
		if (ret.getStatus() == RetStatus.SUCC) {
			return RetError.NONE;
		} else {
			return ret.getErr();
		}
	}

	public RetError upDatePK1TicketCount() {
		IParser parser = new SimpleParser();
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("pk_id", pk_id);
		params.put("pk1_ticket_count", pk1.getPk1_ticket_count());
		params.put("pk1_user_id", pk1.getPk1_user_id());
		params.put("pk2_user_id", pk2.getPk2_user_id());
		Result ret = ApiRequest.request(UPDATE_PK1_TICKET_COUNT_URL, params,
				parser);
		if (ret.getStatus() == RetStatus.SUCC) {
			return RetError.NONE;
		} else {
			return ret.getErr();
		}
	}
}
