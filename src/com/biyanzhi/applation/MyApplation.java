package com.biyanzhi.applation;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Application;

import com.biyanzhi.utils.CheckImageLoaderConfiguration;
import com.huanxin.helper.QuYouHXSDKHelper;

public class MyApplation extends Application {
	private static MyApplation instance;

	private static List<Activity> activityList = new ArrayList<Activity>();
	public static QuYouHXSDKHelper hxSDKHelper = new QuYouHXSDKHelper();

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		CheckImageLoaderConfiguration.checkImageLoaderConfiguration(this);
		System.out.println("huanx::::::::;" + hxSDKHelper.onInit(this));
	}

	public static MyApplation getInstance() {
		return instance;
	}

	// 添加Activity到容器中
	public static void addActivity(Activity activity) {
		activityList.add(activity);
	}

	// 遍历所有Activity并finish
	public static void exit(boolean flag) {
		for (int i = 0; i < activityList.size(); i++) {
			Activity activity = activityList.get(i);
			if (activity != null) {
				activity.finish();
			}
		}
		activityList.clear();
		if (flag) {
			System.exit(0);
		}

	}

}
