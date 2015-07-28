package com.biyanzhi.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.biyanzhi.applation.MyApplation;

/**
 * * SharedPreferences 的公具类
 * 
 * @author teeker_bin
 * 
 */
public class SharedUtils {
	private static final String SP_NAME = "tf";
	private static SharedPreferences sharedPreferences = MyApplation
			.getInstance().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
	private static Editor editor = sharedPreferences.edit();
	public static final String SP_UID = "uid";;
	private static String SHARED_KEY_SETTING_NOTIFICATION = "shared_key_setting_notification";
	private static String SHARED_KEY_SETTING_SOUND = "shared_key_setting_sound";
	private static String SHARED_KEY_SETTING_VIBRATE = "shared_key_setting_vibrate";
	private static final String HUANXIN_USERNAME = "huanxin_username";
	private static final String HUANXIN_PWD = "huanxin_pwd";

	public static String getString(String key, String defaultValue) {
		return sharedPreferences.getString(key, defaultValue);
	}

	public static int getInt(String key, int defaultValue) {
		return sharedPreferences.getInt(key, defaultValue);
	}

	public static boolean getBoolean(String key, boolean defaultValue) {
		return sharedPreferences.getBoolean(key, defaultValue);
	}

	public static void setString(String key, String value) {
		editor.putString(key, value);
		editor.commit();

	}

	public static long getLong(String key, long defaultValue) {
		return sharedPreferences.getLong(key, defaultValue);

	}

	public static void setLong(String key, long value) {
		editor.putLong(key, value);
		editor.commit();
	}

	public static void setInt(String key, int value) {
		editor.putInt(key, value);
		editor.commit();
	}

	public static void setBoolean(String key, boolean value) {
		editor.putBoolean(key, value);
		editor.commit();
	}

	public static void setFirstPlayScore(boolean flag) {
		editor.putBoolean("first_paly_score", flag);
		editor.commit();
	}

	public static boolean getFirstPlayScore() {
		return sharedPreferences.getBoolean("first_paly_score", true);
	}

	public static void setFirstPK(boolean flag) {
		editor.putBoolean("first_pk", flag);
		editor.commit();
	}

	public static boolean getFirstPK() {
		return sharedPreferences.getBoolean("first_pk", true);
	}

	public static void setHunXinLoginFlag(boolean flag) {
		editor.putBoolean("is_login_flag", flag);
		editor.commit();
	}

	public static boolean getHuanXinLogin() {
		return sharedPreferences.getBoolean("is_login_flag", false);

	}

	public static void setSettingMsgNotification(boolean paramBoolean) {
		editor.putBoolean(SHARED_KEY_SETTING_NOTIFICATION, paramBoolean);
		editor.commit();
	}

	public static boolean getSettingMsgNotification() {
		return sharedPreferences.getBoolean(SHARED_KEY_SETTING_NOTIFICATION,
				true);
	}

	public static void setSettingMsgSound(boolean paramBoolean) {
		editor.putBoolean(SHARED_KEY_SETTING_SOUND, paramBoolean);
		editor.commit();
	}

	public static boolean getSettingMsgSound() {

		return sharedPreferences.getBoolean(SHARED_KEY_SETTING_SOUND, false);
	}

	public static void setSettingMsgVibrate(boolean paramBoolean) {
		editor.putBoolean(SHARED_KEY_SETTING_VIBRATE, paramBoolean);
		editor.commit();
	}

	public static boolean getSettingMsgVibrate() {
		return sharedPreferences.getBoolean(SHARED_KEY_SETTING_VIBRATE, true);
	}

	public static void setUid(String uid) {
		setString(SP_UID, uid);
	}

	public static String getUid() {
		return getString(SP_UID, "0");
	}

	public static int getIntUid() {
		String uid = getUid();
		if (uid.length() > 0) {
			return Integer.parseInt(uid);
		}
		return 0;
	}

	public static void settingNewVersion(boolean isNewVersion) {
		editor.putBoolean("new_version", isNewVersion);
		editor.commit();
	}

	public static boolean getNewVersion() {
		return sharedPreferences.getBoolean("new_version", false);

	}

	public static void setUserName(String value) {
		editor.putString("username", value);
		editor.commit();
	}

	public String getUserName() {
		return sharedPreferences.getString("username", "");

	}

	public static String getAPPUserName() {
		return sharedPreferences.getString("app_user_name", "");

	}

	public static void setAPPUserName(String value) {
		editor.putString("app_user_name", value);
		editor.commit();
	}

	public static String getAPPUserAvatar() {
		return sharedPreferences.getString("app_user_avatar", "");

	}

	public static void setAPPUserAvatar(String value) {
		editor.putString("app_user_avatar", value);
		editor.commit();
	}

	public static String getAPPUserGender() {
		return sharedPreferences.getString("app_user_gender", "");

	}

	public static void setAPPUserGender(String value) {
		editor.putString("app_user_gender", value);
		editor.commit();
	}

	public static String getAPPUserBirthday() {
		return sharedPreferences.getString("app_user_birthday", "");

	}

	public static void setAPPUserBirthday(String value) {
		editor.putString("app_user_birthday", value);
		editor.commit();
	}

	public static void setAPPUserAddress(String value) {
		editor.putString("app_user_address", value);
		editor.commit();
	}

	public static String getAPPUserAddress() {
		return sharedPreferences.getString("app_user_address", "");

	}

	public static void setAPPUserGuanZhuCount(int count) {
		editor.putInt("guanzhu_count", count);
		editor.commit();
	}

	public static int getAPPUserGuanZhuCount() {
		return sharedPreferences.getInt("guanzhu_count", 0);

	}

	public static void setAPPUserMyGuanZhuCount(int count) {
		editor.putInt("my_guanzhu_count", count);
		editor.commit();
	}

	public static int getAPPUserMyGuanZhuCount() {
		return sharedPreferences.getInt("my_guanzhu_count", 0);

	}

	public static void setAPPUserChatID(String value) {
		editor.putString("app_user_chat_id", value);
		editor.commit();
	}

	public static String getAPPUserChatID() {
		return sharedPreferences.getString("app_user_chat_id", "");

	}

	public static void saveHXId(String hxId) {
		editor.putString(HUANXIN_USERNAME, hxId);
		editor.commit();
	}

	public static String getHXId() {
		return sharedPreferences.getString(HUANXIN_USERNAME, null);
	}

	public static void saveHuanXinPassword(String pwd) {
		editor.putString(HUANXIN_PWD, pwd);
		editor.commit();
	}

	public static String getHuanXinPwd() {
		return sharedPreferences.getString(HUANXIN_PWD, null);
	}

	public static void clearData() {
		editor.clear();
		editor.commit();
	}
}
