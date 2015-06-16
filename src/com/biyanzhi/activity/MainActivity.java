package com.biyanzhi.activity;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.StoreHouseHeader;

import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.biyanzhi.R;
import com.biyanzhi.adapter.PictureAdapter;
import com.biyanzhi.data.Picture;
import com.biyanzhi.data.PictureList;
import com.biyanzhi.enums.RetError;
import com.biyanzhi.popwindow.SelectPicPopwindow.SelectOnclick;
import com.biyanzhi.task.GetPictureListTask;
import com.biyanzhi.task.LoadMorePictureListTask;
import com.biyanzhi.utils.Constants;
import com.biyanzhi.utils.DialogUtil;
import com.biyanzhi.utils.FileUtils;
import com.biyanzhi.utils.SharedUtils;
import com.biyanzhi.utils.UniversalImageLoadTool;
import com.biyanzhi.utils.Utils;
import com.biyanzhi.view.CircularImage;
import com.biyianzhi.interfaces.AbstractTaskPostCallBack;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMConversation;
import com.easemob.chat.EMMessage;
import com.easemob.chat.EMMessage.ChatType;

public class MainActivity extends BaseActivity implements SelectOnclick {
	private ImageView img_select;
	private String cameraPath = "";
	private Dialog dialog;
	private List<Picture> mLists = new ArrayList<Picture>();
	private PictureList list = new PictureList();
	private GridView mGridView;
	private PictureAdapter adapter;
	private TextView txt_title;
	private CircularImage img_avatar;
	private ImageView img_prompt;
	private PtrClassicFrameLayout mPtrFrame;
	private boolean isLoading = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		setValue();
		dialog = DialogUtil.createLoadingDialog(this);
		dialog.show();
		getPictureList();
		registerBoradcastReceiver();
		updateUnreadLabel();
	}

	@Override
	protected void onResume() {
		super.onResume();
		updateUnreadLabel();
		EMChatManager.getInstance().activityResumed();
	}

	private void initView() {
		initFefushView();
		img_prompt = (ImageView) findViewById(R.id.img_prompt);
		img_avatar = (CircularImage) findViewById(R.id.img_avatar);
		UniversalImageLoadTool.disPlay(SharedUtils.getAPPUserAvatar(),
				img_avatar, R.drawable.default_avatar);
		txt_title = (TextView) findViewById(R.id.title_txt);
		img_select = (ImageView) findViewById(R.id.img_create);
		img_select.setOnClickListener(this);
		// mGridView = (StaggeredGridView) findViewById(R.id.grid_view);
		mGridView = (GridView) findViewById(R.id.gridView1);
		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				startActivity(new Intent(MainActivity.this,
						PictureCommentActivity.class).putExtra("picture",
						mLists.get(position)));
				Utils.leftOutRightIn(MainActivity.this);
			}
		});
		img_avatar.setOnClickListener(this);
		mGridView.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {

				if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
					// ��������,������һҳ����
					if (view.getLastVisiblePosition() == (view.getCount() - 1)) {
						if (isLoading) {
							return;
						}
						loadMorePictureList();
					}
				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {

			}
		});
	}

	private void setValue() {
		// mAdapter = new StaggeredAdapter(this, mLists);
		// mGridView.setAdapter(mAdapter);
		txt_title.setText("����ֵ");
		adapter = new PictureAdapter(this, mLists);
		mGridView.setAdapter(adapter);

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mBroadcastReceiver);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.img_create:
			startActivity(new Intent(this, PublicshPictureActivity.class));
			Utils.leftOutRightIn(this);
			break;
		case R.id.img_avatar:
			startActivity(new Intent(this, PersonalCenterActivity.class)
					.putExtra("unReadCount", unReadCount));
			Utils.leftOutRightIn(this);
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
		// �������ָ������������պ����Ƭ�洢��·��
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

	private void getPictureList() {
		GetPictureListTask task = new GetPictureListTask();
		task.setmCallBack(new AbstractTaskPostCallBack<RetError>() {
			@Override
			public void taskFinish(RetError result) {
				if (dialog != null) {
					dialog.dismiss();
				}
				mPtrFrame.refreshComplete();
				mLists.addAll(0, list.getPictureList());
				adapter.notifyDataSetChanged();
			}
		});
		task.executeParallel(list);
	}

	private void loadMorePictureList() {
		if (mLists.size() == 0) {
			return;
		}
		isLoading = true;
		list.setPublish_time(mLists.get(mLists.size() - 1).getPublish_time());
		LoadMorePictureListTask task = new LoadMorePictureListTask();
		task.setmCallBack(new AbstractTaskPostCallBack<RetError>() {
			@Override
			public void taskFinish(RetError result) {
				if (dialog != null) {
					dialog.dismiss();
				}
				mLists.addAll(list.getPictureList());
				adapter.notifyDataSetChanged();
				isLoading = false;

			}
		});
		task.executeParallel(list);
	}

	/**
	 * ע��ù㲥
	 */
	public void registerBoradcastReceiver() {
		IntentFilter myIntentFilter = new IntentFilter(EMChatManager
				.getInstance().getNewMessageBroadcastAction());
		myIntentFilter.addAction(Constants.UPDATE_USER_AVATAR);
		// ע��㲥
		registerReceiver(mBroadcastReceiver, myIntentFilter);
	}

	/**
	 * ����㲥
	 */
	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(Constants.UPDATE_USER_AVATAR)) {
				UniversalImageLoadTool.disPlay(SharedUtils.getAPPUserAvatar(),
						img_avatar, R.drawable.default_avatar);
			} else {
				// ��ҳ���յ���Ϣ����ҪΪ����ʾδ����ʵ����Ϣ������Ҫ��chatҳ��鿴
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
	 * ˢ��δ����Ϣ��
	 */
	public void updateUnreadLabel() {
		unReadCount = getUnreadMsgCountTotal();
		if (unReadCount > 0) {
			img_prompt.setVisibility(View.VISIBLE);
		} else {
			img_prompt.setVisibility(View.GONE);

		}
	}

	/**
	 * ��ȡδ����Ϣ��
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

	private void initFefushView() {
		mPtrFrame = (PtrClassicFrameLayout) findViewById(R.id.rotate_header_grid_view_frame);
		mPtrFrame.setLastUpdateTimeRelateObject(this);
		mPtrFrame.setPtrHandler(new PtrHandler() {
			@Override
			public void onRefreshBegin(PtrFrameLayout frame) {
				if (mLists.size() > 0) {
					list.setPublish_time(mLists.get(0).getPublish_time());
					getPictureList();
					return;
				}
				mPtrFrame.refreshComplete();

			}

			@Override
			public boolean checkCanDoRefresh(PtrFrameLayout frame,
					View content, View header) {
				return PtrDefaultHandler.checkContentCanBePulledDown(frame,
						content, header);
			}
		});
		StoreHouseHeader header = new StoreHouseHeader(this);
		// header.setPadding(0, LocalDisplay.dp2px(20), 0,
		// LocalDisplay.dp2px(20));
		header.setPadding(0, 40, 0, 40);
		header.initWithString("Loading...");
		mPtrFrame.setHeaderView(header);
		mPtrFrame.addPtrUIHandler(header);
		// the following are default settings
		mPtrFrame.setResistance(1.7f);
		mPtrFrame.setRatioOfHeaderHeightToRefresh(1.2f);
		mPtrFrame.setDurationToClose(500);
		mPtrFrame.setDurationToCloseHeader(2000);
		// default is false
		mPtrFrame.setPullToRefresh(true);
		// default is true
		mPtrFrame.setKeepHeaderWhenRefresh(true);
		// mPtrFrame.postDelayed(new Runnable() {
		// @Override
		// public void run() {
		// mPtrFrame.autoRefresh();
		// }
		// }, 100);
	}
}
// end of class
// public class MainActivity extends BaseActivity {
// private PtrClassicFrameLayout mPtrFrame;
//
// @Override
// protected void onCreate(Bundle savedInstanceState) {
// super.onCreate(savedInstanceState);
// setContentView(R.layout.activity_main);
// initView();
// }
//
// private void initView() {
// initFefushView();
// }
//
// private void initFefushView() {
// mPtrFrame = (PtrClassicFrameLayout)
// findViewById(R.id.rotate_header_grid_view_frame);
// mPtrFrame.setLastUpdateTimeRelateObject(this);
// mPtrFrame.setPtrHandler(new PtrHandler() {
// @Override
// public void onRefreshBegin(PtrFrameLayout frame) {
// mPtrFrame.refreshComplete();
// }
//
// @Override
// public boolean checkCanDoRefresh(PtrFrameLayout frame,
// View content, View header) {
// return PtrDefaultHandler.checkContentCanBePulledDown(frame,
// content, header);
// }
// });
// // the following are default settings
// mPtrFrame.setResistance(1.7f);
// mPtrFrame.setRatioOfHeaderHeightToRefresh(1.2f);
// mPtrFrame.setDurationToClose(200);
// mPtrFrame.setDurationToCloseHeader(1000);
// // default is false
// mPtrFrame.setPullToRefresh(false);
// // default is true
// mPtrFrame.setKeepHeaderWhenRefresh(true);
// mPtrFrame.postDelayed(new Runnable() {
// @Override
// public void run() {
// // mPtrFrame.autoRefresh();
// }
// }, 100);
// }
// }
