/**
 * Copyright (C) 2013-2014 EaseMob Technologies. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.huanxin.helper;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentFilter;

import com.biyanzhi.R;
import com.biyanzhi.activity.ChatActivity;
import com.biyanzhi.activity.LoginActivity;
import com.biyanzhi.utils.Constants;
import com.biyanzhi.utils.SharedUtils;
import com.biyanzhi.utils.Utils;
import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMMessage;
import com.easemob.chat.EMMessage.ChatType;
import com.easemob.chat.OnMessageNotifyListener;
import com.easemob.chat.OnNotificationClickListener;
import com.easemob.exceptions.EaseMobException;

/**
 * Demo UI HX SDK helper class which subclass HXSDKHelper
 * 
 * @author easemob
 * 
 */
public class QuYouHXSDKHelper extends HXSDKHelper {

	@Override
	protected void initHXOptions() {
		super.initHXOptions();
		// you can also get EMChatOptions to set related SDK options
		// EMChatOptions options = EMChatManager.getInstance().getChatOptions();
	}

	@Override
	protected OnMessageNotifyListener getMessageNotifyListener() {
		// 取消注释，app在后台，有新消息来时，状态栏的消息提示换成自己写的
		return new OnMessageNotifyListener() {

			@Override
			public String onNewMessageNotify(EMMessage message) {
				if (Constants.COMMENT_USER_ID.equals(message.getFrom())) {
					return "有人评论的你的照片";

				}
				if (Constants.PLAY_SCORE_USER_ID.equals(message.getFrom())) {
					return "有人给你的照片打分了";

				}
				if (Constants.GUANZHU_USER_ID.equals(message.getFrom())) {
					return "有人关注了你";

				}
				if (Utils.isSystemUser(message.getFrom())) {
					return "系统通知";
				}
				String user_name = "";
				try {
					user_name = message.getStringAttribute("from_user_name");
				} catch (EaseMobException e) {
					try {
						user_name = message.getStringAttribute("user_name");
					} catch (EaseMobException e1) {
						e1.printStackTrace();
					}
					e.printStackTrace();
				}
				return "你的好友 " + user_name + " 发来了一条消息";
			}

			@Override
			public String onLatestMessageNotify(EMMessage message,
					int fromUsersNum, int messageNum) {
				if (Constants.COMMENT_USER_ID.equals(message.getFrom())) {
					return "有人评论的你的照片";

				}
				if (Constants.PLAY_SCORE_USER_ID.equals(message.getFrom())) {
					return "有人给你的照片打分了";

				}
				if (Constants.GUANZHU_USER_ID.equals(message.getFrom())) {
					return "有人关注了你";

				}
				if (Utils.isSystemUser(message.getFrom())) {
					return "系统通知";
				}
				String user_name = "";
				try {
					user_name = message.getStringAttribute("from_user_name");
				} catch (EaseMobException e) {
					e.printStackTrace();
					try {
						user_name = message.getStringAttribute("user_name");
					} catch (EaseMobException e1) {
						e1.printStackTrace();
					}
				}
				return "'" + user_name + "' 发来了" + messageNum + "条消息。";

			}

			@Override
			public String onSetNotificationTitle(EMMessage message) {
				// 修改标题
				return "比颜值";
			}

			@Override
			public int onSetSmallIcon(EMMessage arg0) {
				return R.drawable.message_icon;
			}
		};
	}

	@Override
	protected OnNotificationClickListener getNotificationClickListener() {
		return new OnNotificationClickListener() {

			@SuppressLint("InlinedApi")
			@Override
			public Intent onNotificationClick(EMMessage message) {
				Intent intent = new Intent();
				intent.setClass(appContext, LoginActivity.class);
				ChatType chatType = message.getChatType();
				String username = message.getFrom();
				return intent;
			}
		};
	}

	@Override
	protected void onConnectionConflict() {
		Intent intent = new Intent(appContext, LoginActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.putExtra("conflict", true);
		appContext.startActivity(intent);
	}

	@Override
	public void logout(final EMCallBack callback) {
		super.logout(new EMCallBack() {

			@Override
			public void onSuccess() {
				if (callback != null) {
					callback.onSuccess();
				}
			}

			@Override
			public void onError(int code, String message) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProgress(int progress, String status) {
				// TODO Auto-generated method stub
				if (callback != null) {
					callback.onProgress(progress, status);
				}
			}

		});
	}
}
