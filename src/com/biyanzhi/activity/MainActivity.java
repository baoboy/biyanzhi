package com.biyanzhi.activity;

import java.io.File;
import java.util.Hashtable;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.biyanzhi.R;
import com.biyanzhi.applation.MyApplation;
import com.biyanzhi.popwindow.SelectPicPopwindow.SelectOnclick;
import com.biyanzhi.task.GetVersionTask;
import com.biyanzhi.task.GetVersionTask.UpDateVersion;
import com.biyanzhi.utils.Constants;
import com.biyanzhi.utils.FileUtils;
import com.biyanzhi.utils.SharedUtils;
import com.biyanzhi.utils.UniversalImageLoadTool;
import com.biyanzhi.utils.Utils;
import com.biyanzhi.view.CircularImage;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMConversation;
import com.easemob.chat.EMMessage;
import com.easemob.chat.EMMessage.ChatType;

public class MainActivity extends FragmentActivity implements SelectOnclick,
		OnClickListener {
	private ImageView img_select;
	private String cameraPath = "";
	private CircularImage img_avatar;
	private ImageView img_prompt;
	private TextView btn_biyanzhi;
	private TextView btn_yanzhibang;
	private FragmentTransaction fraTra = null;
	private FragmentManager manager;
	private BiYanZhiFragment biyanzhi_fragment;
	private YanZhiBangFragment yanzhibang_fragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		MyApplation.addActivity(this);
		initView();
		initFragment();
		registerBoradcastReceiver();
		updateUnreadLabel();
		checkVersion();
	}

	@Override
	protected void onResume() {
		super.onResume();
		updateUnreadLabel();
		EMChatManager.getInstance().activityResumed();
	}

	private void initView() {
		btn_biyanzhi = (TextView) findViewById(R.id.btn_biyanzhi);
		btn_yanzhibang = (TextView) findViewById(R.id.btn_yanzhibang);

		img_prompt = (ImageView) findViewById(R.id.img_prompt);
		img_avatar = (CircularImage) findViewById(R.id.img_avatar);
		UniversalImageLoadTool.disPlay(SharedUtils.getAPPUserAvatar(),
				img_avatar, R.drawable.default_avatar);
		img_select = (ImageView) findViewById(R.id.img_create);
		img_select.setOnClickListener(this);
		img_avatar.setOnClickListener(this);
		btn_biyanzhi.setOnClickListener(this);
		btn_yanzhibang.setOnClickListener(this);

	}

	private void initFragment() {
		manager = getSupportFragmentManager();
		fraTra = manager.beginTransaction();
		biyanzhi_fragment = new BiYanZhiFragment();
		fraTra.add(R.id.main_layout, biyanzhi_fragment);
		fraTra.commit();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mBroadcastReceiver);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_create:
			if (SharedUtils.getIntUid() == 0) {
				startActivity(new Intent(this, LoginActivity.class));
				Utils.leftOutRightIn(this);
				return;
			}
			startActivity(new Intent(this, PublicshPictureActivity.class));
			Utils.leftOutRightIn(this);
			break;
		case R.id.img_avatar:
			if (SharedUtils.getIntUid() == 0) {
				startActivity(new Intent(this, LoginActivity.class));
				Utils.leftOutRightIn(this);
				return;
			}
			startActivity(new Intent(this, PersonalCenterActivity.class)
					.putExtra("unReadCount", unReadCount));
			Utils.leftOutRightIn(this);
			break;
		case R.id.btn_biyanzhi:
			fraTra = getSupportFragmentManager().beginTransaction();

			btn_biyanzhi.setTextColor(getResources().getColor(
					R.color.titleBarBackGround));
			btn_biyanzhi.setBackgroundColor(getResources().getColor(
					R.color.white));
			btn_yanzhibang.setTextColor(getResources().getColor(R.color.white));
			btn_yanzhibang.setBackgroundColor(getResources().getColor(
					R.color.titleBarBackGround));
			if (yanzhibang_fragment != null) {
				fraTra.hide(yanzhibang_fragment);
			}
			fraTra.show(biyanzhi_fragment);
			fraTra.commit();

			break;
		case R.id.btn_yanzhibang:
			fraTra = getSupportFragmentManager().beginTransaction();
			btn_yanzhibang.setTextColor(getResources().getColor(
					R.color.titleBarBackGround));
			btn_yanzhibang.setBackgroundColor(getResources().getColor(
					R.color.white));
			btn_biyanzhi.setTextColor(getResources().getColor(R.color.white));
			btn_biyanzhi.setBackgroundColor(getResources().getColor(
					R.color.titleBarBackGround));
			if (yanzhibang_fragment == null) {
				yanzhibang_fragment = new YanZhiBangFragment();
				fraTra.add(R.id.main_layout, yanzhibang_fragment);
			} else {
				yanzhibang_fragment.onResume();
			}
			if (biyanzhi_fragment != null) {
				fraTra.hide(biyanzhi_fragment);
			}
			fraTra.show(yanzhibang_fragment);
			fraTra.commit();

			break;
		default:
			break;
		}
	}

	@Override
	public void menu1_select() {
		String name = FileUtils.getFileName() + ".jpg";
		cameraPath = FileUtils.getCameraPath() + File.separator + name;
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// 下面这句指定调用相机拍照后的照片存储的路径
		intent.putExtra(MediaStore.EXTRA_OUTPUT,
				Uri.fromFile(new File(cameraPath)));
		startActivityForResult(intent, Constants.REQUEST_CODE_GETIMAGE_BYCAMERA);
	}

	@Override
	public void menu2_select() {
		Intent intent = new Intent(Intent.ACTION_PICK, null);
		intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				"image/*");
		startActivityForResult(intent, Constants.REQUEST_CODE_GETIMAGE_BYSDCARD);

	}

	/**
	 * 注册该广播
	 */
	public void registerBoradcastReceiver() {
		IntentFilter myIntentFilter = new IntentFilter(EMChatManager
				.getInstance().getNewMessageBroadcastAction());
		myIntentFilter.addAction(Constants.UPDATE_USER_AVATAR);
		// 注册广播
		registerReceiver(mBroadcastReceiver, myIntentFilter);
	}

	/**
	 * 定义广播
	 */
	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(Constants.UPDATE_USER_AVATAR)) {
				UniversalImageLoadTool.disPlay(SharedUtils.getAPPUserAvatar(),
						img_avatar, R.drawable.default_avatar);
			} else {
				// 主页面收到消息后，主要为了提示未读，实际消息内容需要到chat页面查看
				String msgId = intent.getStringExtra("msgid");
				EMMessage message = EMChatManager.getInstance().getMessage(
						msgId);
				if (message.getChatType() == ChatType.Chat) {
					updateUnreadLabel();
				}
			}
		}
	};
	private int unReadCount;

	/**
	 * 刷新未读消息数
	 */
	public void updateUnreadLabel() {
		unReadCount = getUnreadMsgCountTotal();
		if (unReadCount > 0 || SharedUtils.getNewVersion()) {
			img_prompt.setVisibility(View.VISIBLE);
		} else {
			img_prompt.setVisibility(View.GONE);

		}
	}

	/**
	 * 获取未读消息数
	 * 
	 * @return
	 */
	public int getUnreadMsgCountTotal() {
		int unreadMsgCountTotal = 0;
		Hashtable<String, EMConversation> conversations = EMChatManager
				.getInstance().getAllConversations();
		for (EMConversation conversation : conversations.values()) {
			if (conversation.getIsGroup()) {
				continue;
			}
			unreadMsgCountTotal += conversation.getUnreadMsgCount();
		}
		return unreadMsgCountTotal;
	}

	@Override
	public void onBackPressed() {
		moveTaskToBack(true);
	}

	private void checkVersion() {
		if (!Utils.isNetworkAvailable()) {
			return;
		}
		GetVersionTask task = new GetVersionTask(this, false);
		task.setCallBack(new UpDateVersion() {
			@Override
			public void getNewVersion(int rt, String versionCode, String link,
					String version_info) {
				if (rt == 0) {
					SharedUtils.settingNewVersion(false);
					return;
				}
				img_prompt.setVisibility(View.VISIBLE);
				SharedUtils.settingNewVersion(true);
			}
		});
		int sdk = android.os.Build.VERSION.SDK_INT;
		if (sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
			task.execute();
		} else {
			task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		}
	}
}
