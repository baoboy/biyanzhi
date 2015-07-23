package com.biyanzhi.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.biyanzhi.R;
import com.biyanzhi.adapter.SelectPKPictureAdapter;
import com.biyanzhi.data.PK1;
import com.biyanzhi.data.PK2;
import com.biyanzhi.data.PKData;
import com.biyanzhi.data.Picture;
import com.biyanzhi.data.PictureList;
import com.biyanzhi.enums.RetError;
import com.biyanzhi.popwindow.MenuPopwindow;
import com.biyanzhi.popwindow.MenuPopwindow.OnMenuListOnclick;
import com.biyanzhi.task.AddPKTask;
import com.biyanzhi.task.GetPictureListByUserIDTask;
import com.biyanzhi.task.GetPictureListMoreByUserIDTask;
import com.biyanzhi.utils.Constants;
import com.biyanzhi.utils.DialogUtil;
import com.biyanzhi.utils.GridViewWithHeaderAndFooter;
import com.biyanzhi.utils.SharedUtils;
import com.biyanzhi.utils.ToastUtil;
import com.biyanzhi.utils.Utils;
import com.biyianzhi.interfaces.AbstractTaskPostCallBack;
import com.biyianzhi.interfaces.ConfirmDialog;

import fynn.app.PromptDialog;

public class SelectPKPictureActivity extends BaseActivity {
	private GridViewWithHeaderAndFooter mGridView;
	private SelectPKPictureAdapter adapter;
	private List<Picture> mLists = new ArrayList<Picture>();
	private boolean isLoading = false;
	private PictureList list = new PictureList();
	private TextView txt_title;
	private int load_more_count = 10;
	private MenuPopwindow pop;
	private PK2 pk2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_pkpicture);
		pk2 = (PK2) getIntent().getSerializableExtra("pk");
		initView();
		setValue();
		showDialog();
		getPictureList();
	}

	private void initView() {
		txt_title = (TextView) findViewById(R.id.title_txt);
		txt_title.setText("选择你要PK的照片");
		mGridView = (GridViewWithHeaderAndFooter) findViewById(R.id.gridView1);
		LayoutInflater layoutInflater = LayoutInflater.from(this);
		View footerView = layoutInflater
				.inflate(R.layout.pulldown_footer, null);
		mGridView.addFooterView(footerView);
		mGridView.hideFootView();
		setListener();
	}

	private void setListener() {
		findViewById(R.id.back).setOnClickListener(this);
		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v,
					final int item_position, long arg3) {
				pop = new MenuPopwindow(getApplicationContext(), v,
						new String[] { "查看", "选择为PK照片" });
				pop.setCallback(new OnMenuListOnclick() {

					@Override
					public void onclick(int position) {
						switch (position) {
						case 0:
							Utils.showBigPicture(mLists.get(item_position)
									.getPicture_image_url(),
									SelectPKPictureActivity.this);
							break;
						case 1:
							pkPrompt(item_position);
							break;
						default:
							break;
						}
					}
				});
				pop.show();
			}
		});
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

	private void pkPrompt(final int position) {
		PromptDialog.Builder dialog = DialogUtil.confirmDialog(this, "是否要进行PK",
				"是", "否", new ConfirmDialog() {

					@Override
					public void onOKClick() {
						PK1 pk1 = new PK1();
						pk1.setPk1_user_id(mLists.get(position)
								.getPublisher_id());
						pk1.setPk1_user_picture(mLists.get(position)
								.getPicture_image_url());
						addPK(pk1);
					}

					public void onCancleClick() {

					}
				});
		dialog.show();
	}

	private void addPK(PK1 pk1) {
		showDialog();
		AddPKTask task = new AddPKTask();
		task.setmCallBack(new AbstractTaskPostCallBack<RetError>() {
			@Override
			public void taskFinish(RetError result) {
				dismissDialog();
				if (result != RetError.NONE) {
					return;
				}
				ToastUtil.showToast("添加PK成功,请到PK大厅查看PK结果");
				sendBroadcast(new Intent(Constants.SEND_PK));
				finishThisActivity();

			}
		});
		PKData pk = new PKData();
		pk.setPk1(pk1);
		pk.setPk2(pk2);
		task.executeParallel(pk);
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
		mGridView.showFootView();

		GetPictureListMoreByUserIDTask task = new GetPictureListMoreByUserIDTask(
				SharedUtils.getIntUid());
		task.setmCallBack(new AbstractTaskPostCallBack<RetError>() {
			@Override
			public void taskFinish(RetError result) {
				isLoading = false;
				load_more_count = list.getPictureList().size();
				mLists.addAll(list.getPictureList());
				adapter.notifyDataSetChanged();
				mGridView.hideFootView();

			}
		});
		task.executeParallel(list);
	}

	@Override
	public void onClick(View v) {
		finishThisActivity();

	}
}
