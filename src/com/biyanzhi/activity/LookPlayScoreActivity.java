package com.biyanzhi.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.biyanzhi.R;
import com.biyanzhi.adapter.PictureScoreUserAdapter;
import com.biyanzhi.data.PictureScore;
import com.biyanzhi.data.PictureScoreUserList;
import com.biyanzhi.enums.RetError;
import com.biyanzhi.task.GetPlayScoreUserListTask;
import com.biyanzhi.utils.DialogUtil;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_look_play_score);
		list.setPicture_id(getIntent().getIntExtra("picture_id", 0));
		initView();
		setValue();
		getLists();
	}

	private void initView() {
		img_back = (ImageView) findViewById(R.id.back);
		txt_title = (TextView) findViewById(R.id.title_txt);
		mListView = (ListView) findViewById(R.id.listView1);
		setListener();
	}

	private void setListener() {
		img_back.setOnClickListener(this);
		mListView.setOnItemClickListener(this);
	}

	private void setValue() {
		txt_title.setText("∆¿∑÷œÍ«È");
		adapter = new PictureScoreUserAdapter(this, lists);
		mListView.setAdapter(adapter);
	}

	private void getLists() {
		dialog = DialogUtil.createLoadingDialog(this);
		dialog.show();
		GetPlayScoreUserListTask task = new GetPlayScoreUserListTask();
		task.setmCallBack(new AbstractTaskPostCallBack<RetError>() {
			@Override
			public void taskFinish(RetError result) {
				if (dialog != null) {
					dialog.dismiss();
				}
				if (result != RetError.NONE) {
					return;
				}
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
