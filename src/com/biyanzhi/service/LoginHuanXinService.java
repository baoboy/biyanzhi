package com.biyanzhi.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.biyanzhi.utils.SharedUtils;
import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;

public class LoginHuanXinService extends Service {

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		loginHuanXin();
	}

	private void loginHuanXin() {
		new Thread() {
			public void run() {
				// ����sdk��½������½���������
				EMChatManager.getInstance().login(SharedUtils.getHXId(),
						SharedUtils.getHuanXinPwd(), new EMCallBack() {

							@Override
							public void onSuccess() {
								SharedUtils.setHunXinLoginFlag(true);
							}

							@Override
							public void onProgress(int progress, String status) {

							}

							@Override
							public void onError(int code, final String message) {
								SharedUtils.setHunXinLoginFlag(false);

							}
						});
				stopSelf();
			}
		}.start();
	}
}
