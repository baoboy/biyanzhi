package com.biyanzhi.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;

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
	private LinearLayout layout_footView;

	public UserInfoYanZhiView(Context activity, View contentRootView) {
		this.mActivity = activity;
		this.mContentRootView = contentRootView;
		initView();
	}

	public List<Picture> getmLists() {
		return mLists;
	}

	private void initView() {
		mGridView = (ExpandGridView) mContentRootView
				.findViewById(R.id.gridView1);
		layout_footView = (LinearLayout) mContentRootView
				.findViewById(R.id.footview);
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

	public void setVisibileFootView(boolean isVisibile) {
		if (isVisibile) {
			layout_footView.setVisibility(View.VISIBLE);
		} else {
			layout_footView.setVisibility(View.GONE);
		}
	}

	public void setValue(List<Picture> lists) {
		mLists.addAll(lists);
		adapter = new PictureAdapter(mActivity, mLists);
		mGridView.setAdapter(adapter);
	}

	public void addPictureList(List<Picture> lists) {
		mLists.addAll(lists);
		adapter.notifyDataSetChanged();
	}

	public void delPicture(int picture_id) {
		for (int i = 0; i < mLists.size(); i++) {
			if (picture_id == mLists.get(i).getPicture_id()) {
				mLists.remove(i);
				adapter.notifyDataSetChanged();
				break;
			}
		}
	}
}
