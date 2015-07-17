package com.biyanzhi.utils;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.inputmethod.InputMethodManager;

import com.biyanzhi.R;
import com.biyanzhi.applation.MyApplation;

public class Utils {
	private static long lastClickTime;

	public synchronized static boolean isFastClick() {
		long time = System.currentTimeMillis();
		if (time - lastClickTime < 500) {
			return true;
		}
		lastClickTime = time;
		return false;
	}

	public static void getError() {
		String a = null;
		System.out.println(a.length());
	}

	public static boolean isSystemUser(String user_id) {
		if (Constants.COMMENT_USER_ID.equals(user_id)
				|| Constants.PLAY_SCORE_USER_ID.equals(user_id)
				|| Constants.GUANZHU_USER_ID.equals(user_id)) {
			return true;
		}
		return false;
	}

	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * �ֻ�������֤
	 * 
	 * @param
	 * @return
	 */
	public static boolean isPhoneNum(String strPhoneNum) {
		// Pattern p = Pattern
		// .compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		// Matcher m = p.matcher(strPhoneNum);
		// return m.matches();
		return strPhoneNum.startsWith("1") && strPhoneNum.length() == 11;
	}

	/**
	 * ��֤�����ַ�Ƿ���ȷ
	 * 
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email) {
		String strPattern = "^[a-zA-Z0-9]*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
		Pattern p = Pattern.compile(strPattern);
		Matcher m = p.matcher(email);
		return m.matches();
	}

	/**
	 * ���������
	 */
	public static void hideSoftInput(Context context) {
		if (context == null) {
			return;
		}
		InputMethodManager imm = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm == null) {
			return;
		}
		imm.hideSoftInputFromWindow(((Activity) context).getCurrentFocus()
				.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	}

	/**
	 * ��ʾ�����
	 */
	public static void showSoftInput(Context context) {
		InputMethodManager imm = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(0, InputMethodManager.RESULT_SHOWN);
	} // �����л����������ұ߽��룬����˳�

	public static void leftOutRightIn(Context context) {
		((Activity) context).overridePendingTransition(R.anim.in_from_right,
				R.anim.out_to_left);
	}

	/**
	 * �Ҳ��˳�
	 * 
	 * @param context
	 */
	public static void rightOut(Context context) {
		((Activity) context).overridePendingTransition(R.anim.right_in,
				R.anim.right_out);

	}

	/**
	 * ��ȡ��Ļ���
	 * 
	 * @param context
	 * @return
	 */
	public static int getSecreenWidth(Context context) {
		DisplayMetrics dm = new DisplayMetrics();
		dm = context.getResources().getDisplayMetrics();
		int screenWidth = dm.widthPixels;
		return screenWidth;
	}

	/**
	 * ��ȡ��Ļ�߶�
	 * 
	 * @param context
	 * @return
	 */
	public static int getSecreenHeight(Context context) {
		DisplayMetrics dm = new DisplayMetrics();
		dm = context.getResources().getDisplayMetrics();
		int screenHeight = dm.heightPixels;
		return screenHeight;
	} // ý������

	// - ͨ�� Intent.ACTION_MEDIA_MOUNTED ����ȫɨ��
	public static void allScan(Context context) {
		context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri
				.parse("file://" + Environment.getExternalStorageDirectory())));
	}

	// - ͨ�� Intent.ACTION_MEDIA_SCANNER_SCAN_FILE ɨ��ĳ���ļ�
	public static void fileScan(Context context, String fName) {
		Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
		Uri uri = Uri.fromFile(new File(fName));
		intent.setData(uri);
		context.sendBroadcast(intent);
	}

	/**
	 * ��ȡ����
	 * 
	 * @param v
	 */
	public static void getFocus(View v) {
		v.setFocusable(true);
		v.setFocusableInTouchMode(true);
		v.requestFocus();
		v.requestFocusFromTouch();
	}

	public static void print(String str) {
		System.out.println(str);
	}

	public static int getVersionCode(Context context) {
		try {
			PackageManager manager = context.getPackageManager();
			PackageInfo info = manager.getPackageInfo(context.getPackageName(),
					0);
			String version = String.format("%d", info.versionCode);
			return Integer.valueOf(version);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * ��ȡӦ�õĵ�ǰ�汾��
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String getVersionName(Context context) {
		String version = "";
		try {

			// ��ȡpackagemanager��ʵ��
			PackageManager packageManager = context.getPackageManager();
			// getPackageName()���㵱ǰ��İ�����0�����ǻ�ȡ�汾��Ϣ
			PackageInfo packInfo;
			packInfo = packageManager.getPackageInfo(context.getPackageName(),
					0);
			version = packInfo.versionName;

		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return version;
	}

	/**
	 * 
	 * @Description �������״̬
	 * @param context
	 * @return boolean
	 */
	public static boolean isNetworkAvailable() {
		ConnectivityManager cm = (ConnectivityManager) MyApplation
				.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm.getActiveNetworkInfo() != null
				&& cm.getActiveNetworkInfo().isAvailable()
				&& cm.getActiveNetworkInfo().isConnected()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * ���Sdcard�Ƿ����
	 * 
	 * @return
	 */
	public static boolean isExitsSdcard() {
		if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED))
			return true;
		else
			return false;
	}

}
