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
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import cn.sharesdk.framework.ShareSDK;

import com.biyanzhi.R;
import com.biyanzhi.adapter.PKFinishedApapter;
import com.biyanzhi.data.PKData;
import com.biyanzhi.data.PKList;
import com.biyanzhi.enums.RetError;
import com.biyanzhi.task.GetPKFinishedListTask;
import com.biyanzhi.task.LoadMorePKFinishedListTask;
import com.biyanzhi.utils.DialogUtil;
import com.biyanzhi.utils.UniversalImageLoadTool;
import com.biyianzhi.interfaces.AbstractTaskPostCallBack;

public class PKFinishedFramgent extends Fragment {
	private ListView mListView;
	private PKList list = new PKList();
	private List<PKData> mlists = new ArrayList<PKData>();
	private Dialog dialog;
	private PKFinishedApapter adapter;
	private PtrClassicFrameLayout mPtrFrame;
	private boolean isLoading = false;
	private View foot_view;
	private LinearLayout layout_foot;
	private int load_more_count = 10;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.pk_jieguo_fragment_layout, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
		dialog = DialogUtil.createLoadingDialog(getActivity());
		dialog.show();
		getPkList();
		ShareSDK.initSDK(getActivity());
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("BypassApproval", false);
		ShareSDK.setPlatformDevInfo("Wechat", map);
		ShareSDK.setPlatformDevInfo("WechatMoments", map);
	}

	private void initView() {
		foot_view = LayoutInflater.from(getActivity()).inflate(
				R.layout.pk_foot_view, null);
		layout_foot = (LinearLayout) foot_view
				.findViewById(R.id.layout_footview);
		initFefushView();
		mListView = (ListView) getView().findViewById(R.id.listView1);
		mListView.addFooterView(foot_view);
		layout_foot.setVisibility(View.GONE);
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
						if (load_more_count < 9) {
							return;
						}
						loadMorePKList();
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
		adapter = new PKFinishedApapter(getActivity(), mlists);
		mListView.setAdapter(adapter);
	}

	private void initFefushView() {
		mPtrFrame = (PtrClassicFrameLayout) getView().findViewById(
				R.id.rotate_header_grid_view_frame);
		mPtrFrame.setLastUpdateTimeRelateObject(this);
		mPtrFrame.setPtrHandler(new PtrHandler() {
			@Override
			public void onRefreshBegin(PtrFrameLayout frame) {
				// if (mlists.size() > 0) {
				// list.setPk_time(mlists.get(0).getPk_time());
				// } else {
				list.setPk_time("0");
				// }
				getPkList();
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

	private void loadMorePKList() {
		if (mlists.size() == 0) {
			return;
		}
		layout_foot.setVisibility(View.VISIBLE);
		mListView.setSelection(mListView.getCount() - 1);
		isLoading = true;
		list.setPk_time(mlists.get(mlists.size() - 1).getPk_time());
		LoadMorePKFinishedListTask task = new LoadMorePKFinishedListTask();
		task.setmCallBack(new AbstractTaskPostCallBack<RetError>() {
			@Override
			public void taskFinish(RetError result) {
				if (result != RetError.NONE) {
					return;
				}
				load_more_count = list.getMlists().size();
				mlists.addAll(list.getMlists());
				adapter.notifyDataSetChanged();
				isLoading = false;
				layout_foot.setVisibility(View.INVISIBLE);
			}
		});
		task.executeParallel(list);
	}

	private void getPkList() {
		GetPKFinishedListTask task = new GetPKFinishedListTask();
		task.setmCallBack(new AbstractTaskPostCallBack<RetError>() {
			@Override
			public void taskFinish(RetError result) {
				load_more_count = 10;
				mPtrFrame.refreshComplete();
				if (dialog != null) {
					dialog.dismiss();
				}
				if (result != RetError.NONE) {
					return;
				}
				mlists.clear();
				mlists.addAll(0, list.getMlists());
				adapter.notifyDataSetChanged();
			}
		});
		task.executeParallel(list);
	}

}
