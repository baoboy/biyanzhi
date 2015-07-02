package com.biyanzhi.activity;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.StoreHouseHeader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.biyanzhi.R;
import com.biyanzhi.adapter.PictureAdapter;
import com.biyanzhi.data.Picture;
import com.biyanzhi.data.PictureList;
import com.biyanzhi.enums.RetError;
import com.biyanzhi.task.GetPictureListTask;
import com.biyanzhi.task.LoadMorePictureListTask;
import com.biyanzhi.utils.Constants;
import com.biyanzhi.utils.DialogUtil;
import com.biyanzhi.utils.GridViewWithHeaderAndFooter;
import com.biyanzhi.utils.SharedUtils;
import com.biyanzhi.utils.Utils;
import com.biyianzhi.interfaces.AbstractTaskPostCallBack;

public class BiYanZhiFragment extends Fragment implements OnItemClickListener {
	private Dialog dialog;
	private List<Picture> mLists = new ArrayList<Picture>();
	private PictureList list = new PictureList();
	private GridViewWithHeaderAndFooter mGridView;
	private PictureAdapter adapter;
	private PtrClassicFrameLayout mPtrFrame;
	private boolean isLoading = false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.biyanzhi_layout, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
		setValue();
		dialog = DialogUtil.createLoadingDialog(getActivity());
		dialog.show();
		getPictureList();
		registerBoradcastReceiver();
	}

	private void initView() {
		initFefushView();
		LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
		View footerView = layoutInflater
				.inflate(R.layout.pulldown_footer, null);
		mGridView = (GridViewWithHeaderAndFooter) getView().findViewById(
				R.id.gridView1);
		mGridView.addFooterView(footerView);
		mGridView.hideFootView();
		mGridView.setOnItemClickListener(this);
		mGridView.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {

				if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
					// 滚动到底,请求下一页数据
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
		adapter = new PictureAdapter(getActivity(), mLists);
		mGridView.setAdapter(adapter);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		if (SharedUtils.getIntUid() == 0) {
			startActivity(new Intent(getActivity(), LoginActivity.class));
			Utils.leftOutRightIn(getActivity());
			return;
		}
		startActivity(new Intent(getActivity(), PictureCommentActivity.class)
				.putExtra("picture", mLists.get(position)).putExtra("position",
						position));
		Utils.leftOutRightIn(getActivity());
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
				// Set<Picture> picSet = new HashSet<Picture>();
				// picSet.addAll(mLists);
				// picSet.addAll(list.getPictureList());
				// mLists.clear();
				// mLists.addAll(0, picSet);
				adapter.notifyDataSetChanged();

			}
		});
		task.executeParallel(list);
	}

	private void loadMorePictureList() {
		if (mLists.size() == 0) {
			return;
		}
		mGridView.showFootView();
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
				mGridView.hideFootView();
			}
		});
		task.executeParallel(list);
	}

	private void initFefushView() {
		mPtrFrame = (PtrClassicFrameLayout) getView().findViewById(
				R.id.rotate_header_grid_view_frame);
		mPtrFrame.setLastUpdateTimeRelateObject(this);
		mPtrFrame.setPtrHandler(new PtrHandler() {
			@Override
			public void onRefreshBegin(PtrFrameLayout frame) {
				if (mLists.size() > 0) {
					list.setPublish_time(mLists.get(0)
							.getPublish_time_last_update());
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
		StoreHouseHeader header = new StoreHouseHeader(getActivity());
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

	/**
	 * 注册该广播
	 */
	public void registerBoradcastReceiver() {
		IntentFilter myIntentFilter = new IntentFilter();
		myIntentFilter.addAction(Constants.PLAY_SCORE);
		// 注册广播
		getActivity().registerReceiver(mBroadcastReceiver, myIntentFilter);
	}

	/**
	 * 定义广播
	 */
	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(Constants.PLAY_SCORE)) {
				int position = intent.getIntExtra("position", -1);
				int score = intent.getIntExtra("score", 0);
				if (position < 0) {
					return;
				}

				int all_score = mLists.get(position).getAverage_score()
						* mLists.get(position).getScore_number();
				mLists.get(position).setScore_number(
						mLists.get(position).getScore_number() + 1);
				int new_avg_score = (all_score + score)
						/ mLists.get(position).getScore_number();
				mLists.get(position).setAverage_score(new_avg_score);
				adapter.notifyDataSetChanged();
			}
		}
	};

	public void onDestroy() {
		super.onDestroy();
		getActivity().unregisterReceiver(mBroadcastReceiver);
	};
}
