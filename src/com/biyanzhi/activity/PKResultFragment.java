package com.biyanzhi.activity;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.StoreHouseHeader;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

import com.biyanzhi.R;
import com.biyanzhi.adapter.PKResultAdapter;
import com.biyanzhi.data.PKResult;
import com.biyanzhi.data.PKResultList;
import com.biyanzhi.enums.RetError;
import com.biyanzhi.task.GetPkResultTask;
import com.biyanzhi.utils.DialogUtil;
import com.biyanzhi.utils.GridViewWithHeaderAndFooter;
import com.biyanzhi.utils.UniversalImageLoadTool;
import com.biyanzhi.utils.Utils;
import com.biyianzhi.interfaces.AbstractTaskPostCallBack;

public class PKResultFragment extends Fragment implements OnItemClickListener {
	private Dialog dialog;
	private List<PKResult> mLists = new ArrayList<PKResult>();

	private PKResultList list = new PKResultList();
	private GridViewWithHeaderAndFooter mGridView;
	private PKResultAdapter adapter;
	private PtrClassicFrameLayout mPtrFrame;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.pk_result_fragment_layout, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
		setValue();
		dialog = DialogUtil.createLoadingDialog(getActivity());
		dialog.show();
		getPKResultList();
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
					UniversalImageLoadTool.resume();
					adapter.notifyDataSetChanged();
				} else {
					UniversalImageLoadTool.pause();

				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {

			}
		});
	}

	private void setValue() {
		adapter = new PKResultAdapter(getActivity(), mLists);
		mGridView.setAdapter(adapter);

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		UniversalImageLoadTool.resume();
		Intent intent = new Intent();
		intent.setClass(getActivity(), UserInfoActivity.class).putExtra(
				"user_id", mLists.get(position).getUser().getUser_id());
		startActivity(intent);
		Utils.leftOutRightIn(getActivity());
	}

	private void getPKResultList() {
		GetPkResultTask task = new GetPkResultTask();
		task.setmCallBack(new AbstractTaskPostCallBack<RetError>() {
			@Override
			public void taskFinish(RetError result) {
				if (dialog != null) {
					dialog.dismiss();
				}
				mLists.clear();
				mPtrFrame.refreshComplete();
				mLists.addAll(0, list.getLists());
				adapter.notifyDataSetChanged();
				// bangAdapter.notifyDataSetChanged();
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
				getPKResultList();
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

}
