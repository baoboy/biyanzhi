package com.biyanzhi.data;

import java.io.File;
import java.util.HashMap;

import com.biyanzhi.data.result.ApiRequest;
import com.biyanzhi.data.result.Result;
import com.biyanzhi.enums.RetError;
import com.biyanzhi.enums.RetStatus;
import com.biyanzhi.parser.IParser;
import com.biyanzhi.parser.SimpleParser;
import com.biyanzhi.parser.UserLoginPaser;
import com.biyanzhi.utils.BitmapUtils;
import com.biyanzhi.utils.SharedUtils;

public class User {
	private static final String VERIFY_CELLPHONE_API = "getVerifyCode.do";
	private static final String USER_REGISTER_API = "userRegister.do";
	private static final String USER_LOGIN_API = "userLogin.do";
	private static final String GET_USER_INFO = "GetUserInfoServlet";
	private static final String FIND_PASSWORD_VERIFY_CODE = "getVerifyCode.do";
	private static final String UPDATE_USER_LOGIN_PASSWORD = "UpdateUserLoginPassword";
	private static final String CHECK_VERIFY_CODE = "checkVerifyCode.do";

	private String user_cellphone = "";// 用户注册电话
	private String user_name = "";// 用户注册姓名
	private String user_avatar = "";// 用户注册头像
	private String user_gender = "";// 用户注册性别
	private String user_birthday = "";// 用户注册生日
	private String user_password = "";// 用户注册密码
	private String user_address = "";
	private String user_province = "";
	private int user_id = 0;
	private int guanzhu_count;

	public int getGuanzhu_count() {
		return guanzhu_count;
	}

	public void setGuanzhu_count(int guanzhu_count) {
		this.guanzhu_count = guanzhu_count;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getUser_cellphone() {
		return user_cellphone;
	}

	public void setUser_cellphone(String user_cellphone) {
		this.user_cellphone = user_cellphone;
	}

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

	public String getUser_gender() {
		return user_gender;
	}

	public void setUser_gender(String user_gender) {
		this.user_gender = user_gender;
	}

	public String getUser_birthday() {
		return user_birthday;
	}

	public void setUser_birthday(String user_birthday) {
		this.user_birthday = user_birthday;
	}

	public String getUser_password() {
		return user_password;
	}

	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}

	public String getUser_address() {
		return user_address;
	}

	public void setUser_address(String user_address) {
		this.user_address = user_address;
	}

	public String getUser_province() {
		return user_province;
	}

	public void setUser_province(String user_province) {
		this.user_province = user_province;
	}

	/**
	 * 验证手机号手机号是否已被注册
	 * 
	 * @return
	 */
	public RetError vefifyCellPhone() {
		IParser parser = new SimpleParser();
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("user_cellphone", user_cellphone);
		Result ret = ApiRequest.request(VERIFY_CELLPHONE_API, params, parser);
		if (ret.getStatus() == RetStatus.SUCC) {
			return RetError.NONE;
		} else {
			return ret.getErr();
		}
	}

	public RetError getFindPasswordVerifyCode() {
		IParser parser = new SimpleParser();
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("user_cellphone", user_cellphone);
		Result ret = ApiRequest.request(FIND_PASSWORD_VERIFY_CODE, params,
				parser);
		if (ret.getStatus() == RetStatus.SUCC) {
			return RetError.NONE;
		} else {
			return ret.getErr();
		}
	}

	/**
	 * 用户注册
	 * 
	 * @return
	 */
	public RetError userRegister() {
		IParser parser = new SimpleParser();
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("user_name", user_name);
		params.put("user_cellphone", user_cellphone);
		params.put("user_password", user_password);
		params.put("user_gender", user_gender);
		params.put("user_birthday", user_birthday);
		params.put("user_address", user_address);
		params.put("user_province", user_province);
		File file = BitmapUtils.getImageFile(user_avatar);
		Result ret = ApiRequest.requestWithFile(USER_REGISTER_API, params,
				file, parser);
		if (ret.getStatus() == RetStatus.SUCC) {
			return RetError.NONE;
		} else {
			return ret.getErr();
		}

	}

	public RetError updateUserLoginPassword() {
		IParser parser = new SimpleParser();
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("user_password", user_password);
		params.put("cell_phone", user_cellphone);
		Result ret = ApiRequest.request(UPDATE_USER_LOGIN_PASSWORD, params,
				parser);
		if (ret.getStatus() == RetStatus.SUCC) {
			return RetError.NONE;
		} else {
			return ret.getErr();
		}
	}

	/**
	 * 用户 登录
	 * 
	 * @return
	 */
	public RetError userLogin() {
		IParser parser = new UserLoginPaser();
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("user_cellphone", user_cellphone);
		params.put("user_password", user_password);
		Result ret = ApiRequest.request(USER_LOGIN_API, params, parser);
		if (ret.getStatus() == RetStatus.SUCC) {
			User user = (User) ret.getData();
			SharedUtils.setAPPUserAddress(user.getUser_address());
			SharedUtils.setAPPUserAvatar(user.getUser_avatar());
			SharedUtils.setAPPUserBirthday(user.getUser_birthday());
			SharedUtils.setAPPUserGender(user.getUser_gender());
			SharedUtils.setAPPUserName(user.getUser_name());
			SharedUtils.setAPPUserProvince(user.getUser_province());
			SharedUtils.setUid(user.getUser_id() + "");
			this.user_id = user.getUser_id();
			return RetError.NONE;
		} else {
			return ret.getErr();
		}

	}

	// public RetError getUserInfo() {
	// IParser parser = new UserSelfPaser();
	// HashMap<String, Object> params = new HashMap<String, Object>();
	// Result ret = ApiRequest.request(GET_USER_INFO, params, parser);
	// if (ret.getStatus() == RetStatus.SUCC) {
	// User member = (User) ret.getData();
	// user_avatar = member.getUser_avatar();
	// user_name = member.getUser_name();
	// return RetError.NONE;
	// } else {
	// return ret.getErr();
	// }
	// }

	/**
	 * 验证验证码是否正确
	 * 
	 * @param code
	 */
	public RetError checkVerifyCode(String code) {
		IParser parser = new SimpleParser();
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("user_cellphone", user_cellphone);
		params.put("sms_code", code);
		Result ret = ApiRequest.request(CHECK_VERIFY_CODE, params, parser);
		if (ret.getStatus() == RetStatus.SUCC) {
			return RetError.NONE;
		} else {
			return ret.getErr();
		}
	}
}
