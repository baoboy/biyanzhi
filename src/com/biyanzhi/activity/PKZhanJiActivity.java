package com.biyanzhi.activity;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.StoreHouseHeader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import cn.sharesdk.framework.ShareSDK;

import com.biyanzhi.R;
import com.biyanzhi.adapter.PKZhanJiApapter;
import com.biyanzhi.data.PKData;
import com.biyanzhi.data.PKList;
import com.biyanzhi.enums.RetError;
import com.biyanzhi.task.GetPKListByUserIDTask;
import com.biyanzhi.task.LoadMorePKListByUserIDTask;
import com.biyanzhi.utils.DialogUtil;
import com.biyanzhi.utils.UniversalImageLoadTool;
import com.biyianzhi.interfaces.AbstractTaskPostCallBack;

public class PKZhanJiActivity extends BaseActivity {
	private ListView mListView;
	private PKList list = new PKList();
	private List<PKData> mlists = new ArrayList<PKData>();
	private Dialog dialog;
	private PKZhanJiApapter adapter;
	private PtrClassicFrameLayout mPtrFrame;
	private boolean isLoading = false;
	private View foot_view;
	private LinearLayout layout_foot;
	private int user_id = 0;
	private TextView txt_title;
	private int page = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pkzhan_ji);
		user_id = getIntent().getIntExtra("user_id", 0);
		initView();
		dialog = DialogUtil.createLoadingDialog(this);
		dialog.show();
		getPkList(page);
		ShareSDK.initSDK(this);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("BypassApproval", false);
		ShareSDK.setPlatformDevInfo("Wechat", map);
		ShareSDK.setPlatformDevInfo("WechatMoments", map);
	}

	private void initView() {
		findViewById(R.id.back).setOnClickListener(this);
		txt_title = (TextView) findViewById(R.id.title_txt);
		txt_title.setText("PK战绩");
		foot_view = LayoutInflater.from(this).inflate(R.layout.pk_foot_view,
				null);
		layout_foot = (LinearLayout) foot_view
				.findViewById(R.id.layout_footview);
		initFefushView();
		mListView = (ListView) findViewById(R.id.listView1);
		mListView.addFooterView(foot_view, null, true);
		layout_foot.setVisibility(View.GONE);
		mListView.setFooterDividersEnabled(false);
		mListView.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
					UniversalImageLoadTool.resume();
					adapter.notifyDataSetChanged();
					// 滚动到底,请求下一页数据
					if (view.getLastVisiblePosition() == (view.getCount() - 1)) {
						if (isLoading) {
							return;
						}
						if (list.getLoad_more_count() < 9) {
							return;
						}
						// loadMorePKList();
						layout_foot.setVisibility(View.VISIBLE);
						mListView.setSelection(mListView.getCount() - 1);
						isLoading = true;
						getPkList(++page);
					}
				} else {
					UniversalImageLoadTool.pause();

				}

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {

			}
		});
		adapter = new PKZhanJiApapter(this, mlists);
		mListView.setAdapter(adapter);
	}

	private void initFefushView() {
		mPtrFrame = (PtrClassicFrameLayout) findViewById(R.id.rotate_header_grid_view_frame);
		mPtrFrame.setLastUpdateTimeRelateObject(this);
		mPtrFrame.setPtrHandler(new PtrHandler() {
			@Override
			public void onRefreshBegin(PtrFrameLayout frame) {
				// if (mlists.size() > 0) {
				// list.setPk_time(mlists.get(0).getPk_time());
				// } else {
				// list.setPk_time("0");
				// }
				// getPkList(1);
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

	private void loadMorePKList() {
		if (mlists.size() == 0) {
			return;
		}
		layout_foot.setVisibility(View.VISIBLE);
		mListView.setSelection(mListView.getCount() - 1);
		isLoading = true;
		list.setPk_time(mlists.get(mlists.size() - 1).getPk_time());
		LoadMorePKListByUserIDTask task = new LoadMorePKListByUserIDTask(
				user_id);
		task.setmCallBack(new AbstractTaskPostCallBack<RetError>() {
			@Override
			public void taskFinish(RetError result) {
				if (result != RetError.NONE) {
					return;
				}
				mlists.addAll(list.getMlists());
				adapter.notifyDataSetChanged();
				isLoading = false;
				layout_foot.setVisibility(View.INVISIBLE);
			}
		});
		task.executeParallel(list);
	}

	private void getPkList(int page) {
		GetPKListByUserIDTask task = new GetPKListByUserIDTask(user_id, page);
		task.setmCallBack(new AbstractTaskPostCallBack<RetError>() {
			@Override
			public void taskFinish(RetError result) {
				mPtrFrame.refreshComplete();
				if (dialog != null) {
					dialog.dismiss();
				}
				if (result != RetError.NONE) {
					return;
				}
				// mlists.clear();
				mlists.addAll(list.getMlists());
				adapter.notifyDataSetChanged();
				isLoading = false;
				layout_foot.setVisibility(View.INVISIBLE);
			}
		});
		task.executeParallel(list);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finishThisActivity();
			break;
		default:
			break;
		}
	}
}
