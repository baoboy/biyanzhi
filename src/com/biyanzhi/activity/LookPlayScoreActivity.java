package com.biyanzhi.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.biyanzhi.R;
import com.biyanzhi.adapter.PictureScoreUserAdapter;
import com.biyanzhi.data.PictureScore;
import com.biyanzhi.data.PictureScoreUserList;
import com.biyanzhi.enums.RetError;
import com.biyanzhi.task.GetPlayScoreUserListTask;
import com.biyanzhi.utils.DialogUtil;
import com.biyanzhi.utils.UniversalImageLoadTool;
import com.biyanzhi.utils.Utils;
import com.biyianzhi.interfaces.AbstractTaskPostCallBack;

public class LookPlayScoreActivity extends BaseActivity implements
		OnItemClickListener {
	private ImageView img_back;
	private TextView txt_title;
	private ListView mListView;

	private Dialog dialog;

	private PictureScoreUserList list = new PictureScoreUserList();
	private List<PictureScore> lists = new ArrayList<PictureScore>();
	private PictureScoreUserAdapter adapter;
	private int page = 1;
	private boolean isLoading = false;
	private View foot_view;
	private LinearLayout layout_foot;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_look_play_score);
		list.setPicture_id(getIntent().getIntExtra("picture_id", 0));
		initView();
		setValue();
		dialog = DialogUtil.createLoadingDialog(this);
		dialog.show();
		getLists(page);
	}

	private void initView() {
		img_back = (ImageView) findViewById(R.id.back);
		txt_title = (TextView) findViewById(R.id.title_txt);
		mListView = (ListView) findViewById(R.id.listView1);
		foot_view = LayoutInflater.from(this).inflate(R.layout.pk_foot_view,
				null);
		layout_foot = (LinearLayout) foot_view
				.findViewById(R.id.layout_footview);
		mListView.addFooterView(foot_view, null, true);
		layout_foot.setVisibility(View.GONE);
		mListView.setFooterDividersEnabled(false);
		setListener();
	}

	private void setListener() {
		img_back.setOnClickListener(this);
		mListView.setOnItemClickListener(this);
		mListView.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
					// 滚动到底,请求下一页数据
					if (view.getLastVisiblePosition() == (view.getCount() - 1)) {
						if (isLoading) {
							return;
						}
						if (list.getLoad_more_count() < 9) {
							return;
						}
						layout_foot.setVisibility(View.VISIBLE);
						isLoading = true;
						getLists(++page);
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
	}

	private void setValue() {
		txt_title.setText("评分详情");
		adapter = new PictureScoreUserAdapter(this, lists);
		mListView.setAdapter(adapter);
	}

	private void getLists(int page) {

		GetPlayScoreUserListTask task = new GetPlayScoreUserListTask(page);
		task.setmCallBack(new AbstractTaskPostCallBack<RetError>() {
			@Override
			public void taskFinish(RetError result) {
				if (dialog != null) {
					dialog.dismiss();
				}
				if (result != RetError.NONE) {
					return;
				}
				isLoading = false;
				layout_foot.setVisibility(View.INVISIBLE);
				lists.addAll(list.getLists());
				adapter.notifyDataSetChanged();
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

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		Intent intent = new Intent();
		intent.setClass(this, UserInfoActivity.class).putExtra("user_id",
				lists.get(position).getUser().getUser_id());
		startActivity(intent);
		Utils.leftOutRightIn(this);
	}
}
