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
import com.biyanzhi.adapter.GuanZhuListAdapter;
import com.biyanzhi.data.GuanZhuUserList;
import com.biyanzhi.data.User;
import com.biyanzhi.enums.RetError;
import com.biyanzhi.task.GetMyGuanZhuListTask;
import com.biyanzhi.utils.DialogUtil;
import com.biyanzhi.utils.SharedUtils;
import com.biyanzhi.utils.Utils;
import com.biyianzhi.interfaces.AbstractTaskPostCallBack;

public class MyGuanZhuActivity extends BaseActivity implements
		OnItemClickListener {
	private ImageView img_back;
	private TextView txt_title;
	private ListView mListView;

	private Dialog dialog;

	private GuanZhuUserList list = new GuanZhuUserList();
	private List<User> lists = new ArrayList<User>();
	private GuanZhuListAdapter adapter;
	private int user_id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guan_zhu);
		user_id = getIntent().getIntExtra("user_id", 0);
		list.setGuanzhu_user_id(user_id);
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
		if (user_id == SharedUtils.getIntUid()) {
			txt_title.setText("我关注的");
		} else {
			txt_title.setText("TA关注的");
		}
		adapter = new GuanZhuListAdapter(this, lists);
		mListView.setAdapter(adapter);
	}

	private void getLists() {
		dialog = DialogUtil.createLoadingDialog(this);
		dialog.show();
		GetMyGuanZhuListTask task = new GetMyGuanZhuListTask();
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
				lists.get(position).getUser_id());
		startActivity(intent);
		Utils.leftOutRightIn(this);
	}
}
