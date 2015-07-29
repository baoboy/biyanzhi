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
import android.widget.GridView;

import com.biyanzhi.R;
import com.biyanzhi.adapter.PictureAdapter;
import com.biyanzhi.data.Picture;
import com.biyanzhi.data.PictureList;
import com.biyanzhi.enums.RetError;
import com.biyanzhi.task.GetGirlBangPictureListTask;
import com.biyanzhi.utils.DialogUtil;
import com.biyanzhi.utils.SharedUtils;
import com.biyanzhi.utils.UniversalImageLoadTool;
import com.biyanzhi.utils.Utils;
import com.biyianzhi.interfaces.AbstractTaskPostCallBack;
import com.haarman.listviewanimations.swinginadapters.prepared.AlphaInAnimationAdapter;

public class GirlFragment extends Fragment implements OnItemClickListener {
	private Dialog dialog;
	private List<Picture> mLists = new ArrayList<Picture>();
	private PictureList list = new PictureList();
	private GridView mGridView;
	private PictureAdapter adapter;
	private PtrClassicFrameLayout mPtrFrame;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.girl_fragment_layout, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
		setValue();
		dialog = DialogUtil.createLoadingDialog(getActivity());
		dialog.show();
		getPictureList();
	}

	private void initView() {
		initFefushView();
		mGridView = (GridView) getView().findViewById(R.id.gridView1);
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
		adapter = new PictureAdapter(getActivity(), mLists);
		AlphaInAnimationAdapter alphaInAnimationAdapter = new AlphaInAnimationAdapter(
				adapter);
		alphaInAnimationAdapter.setAbsListView(mGridView);
		mGridView.setAdapter(alphaInAnimationAdapter);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		UniversalImageLoadTool.resume();
		if (SharedUtils.getIntUid() == 0) {
			startActivity(new Intent(getActivity(), LoginActivity.class));
			Utils.leftOutRightIn(getActivity());
			return;
		}
		startActivity(new Intent(getActivity(), PictureCommentActivity.class)
				.putExtra("picture", mLists.get(position)).putExtra(
						"isCanPlayScore", false));
		Utils.leftOutRightIn(getActivity());
	}

	private void getPictureList() {
		GetGirlBangPictureListTask task = new GetGirlBangPictureListTask();
		task.setmCallBack(new AbstractTaskPostCallBack<RetError>() {
			@Override
			public void taskFinish(RetError result) {
				if (dialog != null) {
					dialog.dismiss();
				}
				mPtrFrame.refreshComplete();
				mLists.clear();
				mLists.addAll(0, list.getPictureList());

				adapter.notifyDataSetChanged();

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
				getPictureList();
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
