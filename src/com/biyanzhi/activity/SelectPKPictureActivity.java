package com.biyanzhi.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.biyanzhi.R;
import com.biyanzhi.adapter.PictureAdapter;
import com.biyanzhi.data.Picture;
import com.biyanzhi.data.PictureList;
import com.biyanzhi.enums.RetError;
import com.biyanzhi.task.GetPictureListMoreByUserIDTask;
import com.biyanzhi.utils.SharedUtils;
import com.biyianzhi.interfaces.AbstractTaskPostCallBack;

public class SelectPKPictureActivity extends BaseActivity {
	private GridView mGridView;
	private PictureAdapter adapter;
	private List<Picture> mLists = new ArrayList<Picture>();
	private LinearLayout layout_footView;
	private boolean isLoading = false;
	private PictureList list = new PictureList();
	private TextView txt_title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_pkpicture);
		initView();
		setValue();
		loadMorePictureList();
		showDialog();
	}

	private void initView() {
		txt_title = (TextView) findViewById(R.id.title_txt);
		txt_title.setText("—°‘ÒPK’’∆¨");
		mGridView = (GridView) findViewById(R.id.gridView1);
		layout_footView = (LinearLayout) findViewById(R.id.footview);
		setListener();
	}

	private void setListener() {
		findViewById(R.id.back).setOnClickListener(this);

	}

	private void setValue() {
		adapter = new PictureAdapter(this, mLists);
		mGridView.setAdapter(adapter);
	}

	private void loadMorePictureList() {
		if (isLoading) {
			return;
		}

		isLoading = true;
		if (mLists.size() != 0) {
			list.setPublish_time(mLists.get(mLists.size() - 1)
					.getPublish_time());
			layout_footView.setVisibility(View.VISIBLE);
		}
		GetPictureListMoreByUserIDTask task = new GetPictureListMoreByUserIDTask(
				SharedUtils.getIntUid());
		task.setmCallBack(new AbstractTaskPostCallBack<RetError>() {
			@Override
			public void taskFinish(RetError result) {
				isLoading = false;
				layout_footView.setVisibility(View.GONE);
				mLists.addAll(list.getPictureList());
				dismissDialog();
			}
		});
		task.executeParallel(list);
	}

	@Override
	public void onClick(View v) {
		finishThisActivity();

	}
}
