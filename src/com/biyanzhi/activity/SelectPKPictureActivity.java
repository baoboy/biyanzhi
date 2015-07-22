package com.biyanzhi.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.GridView;
import android.widget.TextView;

import com.biyanzhi.R;
import com.biyanzhi.adapter.SelectPKPictureAdapter;
import com.biyanzhi.data.Picture;
import com.biyanzhi.data.PictureList;
import com.biyanzhi.enums.RetError;
import com.biyanzhi.task.GetPictureListByUserIDTask;
import com.biyanzhi.task.GetPictureListMoreByUserIDTask;
import com.biyanzhi.utils.SharedUtils;
import com.biyianzhi.interfaces.AbstractTaskPostCallBack;

public class SelectPKPictureActivity extends BaseActivity {
	private GridView mGridView;
	private SelectPKPictureAdapter adapter;
	private List<Picture> mLists = new ArrayList<Picture>();
	// private LinearLayout layout_footView;
	private boolean isLoading = false;
	private PictureList list = new PictureList();
	private TextView txt_title;
	private int load_more_count = 10;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_pkpicture);
		initView();
		setValue();
		showDialog();
		getPictureList();
	}

	private void initView() {
		txt_title = (TextView) findViewById(R.id.title_txt);
		txt_title.setText("选择PK照片");
		mGridView = (GridView) findViewById(R.id.gridView1);
		// layout_footView = (LinearLayout) findViewById(R.id.footview);
		setListener();
	}

	private void setListener() {
		findViewById(R.id.back).setOnClickListener(this);
		mGridView.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// 滚动到底,请求下一页数据
				if (view.getLastVisiblePosition() == (view.getCount() - 1)) {
					if (isLoading) {
						return;
					}
					if (load_more_count < 9) {
						return;
					}
					loadMorePictureList();
				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {

			}
		});
	}

	private void setValue() {
		adapter = new SelectPKPictureAdapter(this, mLists);
		mGridView.setAdapter(adapter);
	}

	private void getPictureList() {
		GetPictureListByUserIDTask task = new GetPictureListByUserIDTask(
				SharedUtils.getIntUid());
		task.setmCallBack(new AbstractTaskPostCallBack<RetError>() {
			@Override
			public void taskFinish(RetError result) {
				dismissDialog();
				mLists.addAll(list.getPictureList());
				adapter.notifyDataSetChanged();
			}
		});
		task.executeParallel(list);
	}

	private void loadMorePictureList() {
		if (isLoading) {
			return;
		}
		if (mLists.size() == 0) {
			return;
		}
		isLoading = true;
		list.setPublish_time(mLists.get(mLists.size() - 1).getPublish_time());
		// layout_footView.setVisibility(View.VISIBLE);

		GetPictureListMoreByUserIDTask task = new GetPictureListMoreByUserIDTask(
				SharedUtils.getIntUid());
		task.setmCallBack(new AbstractTaskPostCallBack<RetError>() {
			@Override
			public void taskFinish(RetError result) {
				isLoading = false;
				load_more_count = list.getPictureList().size();
				// layout_footView.setVisibility(View.GONE);
				mLists.addAll(list.getPictureList());
				adapter.notifyDataSetChanged();

			}
		});
		task.executeParallel(list);
	}

	@Override
	public void onClick(View v) {
		finishThisActivity();

	}
}
