package com.biyanzhi.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.biyanzhi.R;
import com.biyanzhi.adapter.PictureAdapter;
import com.biyanzhi.data.Picture;
import com.biyanzhi.utils.ScrollViewGridViewWithHeaderAndFooter;
import com.biyanzhi.utils.Utils;

public class UserInfoYanZhiView {
	private Context mActivity;
	private View mContentRootView;
	private ScrollViewGridViewWithHeaderAndFooter mGridView;
	private PictureAdapter adapter;
	private List<Picture> mLists = new ArrayList<Picture>();

	public UserInfoYanZhiView(Context activity, View contentRootView) {
		this.mActivity = activity;
		this.mContentRootView = contentRootView;
		initView();
	}

	public List<Picture> getmLists() {
		return mLists;
	}

	private void initView() {
		mGridView = (ScrollViewGridViewWithHeaderAndFooter) mContentRootView
				.findViewById(R.id.gridView1);
		LayoutInflater layoutInflater = LayoutInflater.from(mActivity);
		View footerView = layoutInflater
				.inflate(R.layout.pulldown_footer, null);
		mGridView.addFooterView(footerView);
		mGridView.hideFootView();
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
			mGridView.showFootView();
		} else {
			mGridView.hideFootView();
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
