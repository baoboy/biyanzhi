package com.biyanzhi.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.biyanzhi.R;
import com.biyanzhi.adapter.PictureAdapter;
import com.biyanzhi.data.Picture;
import com.biyanzhi.utils.Utils;
import com.biyanzhi.view.ExpandGridView;

public class UserInfoYanZhiView {
	private Context mActivity;
	private View mContentRootView;
	private ExpandGridView mGridView;
	private PictureAdapter adapter;
	private List<Picture> mLists = new ArrayList<Picture>();

	public UserInfoYanZhiView(Context activity, View contentRootView) {
		this.mActivity = activity;
		this.mContentRootView = contentRootView;
		initView();
	}

	private void initView() {
		mGridView = (ExpandGridView) mContentRootView
				.findViewById(R.id.gridView1);
		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				mActivity.startActivity(new Intent(mActivity,
						PictureCommentActivity.class).putExtra("picture",
						mLists.get(position)));
				Utils.leftOutRightIn(mActivity);
			}
		});
	}

	public void setValue(List<Picture> lists) {
		mLists.addAll(lists);
		adapter = new PictureAdapter(mActivity, mLists);
		mGridView.setAdapter(adapter);
	}
}
