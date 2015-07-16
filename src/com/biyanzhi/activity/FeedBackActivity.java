package com.biyanzhi.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.biyanzhi.R;
import com.biyanzhi.data.FeedBack;
import com.biyanzhi.enums.RetError;
import com.biyanzhi.task.AddFeedBackTask;
import com.biyanzhi.utils.ToastUtil;
import com.biyianzhi.interfaces.AbstractTaskPostCallBack;

public class FeedBackActivity extends BaseActivity {
	private TextView txt_title;
	private EditText edit_content;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feed_back);
		initView();
	}

	private void initView() {
		findViewById(R.id.back).setOnClickListener(this);
		findViewById(R.id.btn_submit).setOnClickListener(this);
		txt_title = (TextView) findViewById(R.id.title_txt);
		txt_title.setText("意见反馈");
		edit_content = (EditText) findViewById(R.id.edit_content);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finishThisActivity();
			break;
		case R.id.btn_submit:
			String content = edit_content.getText().toString().trim();
			if ("".equals(content)) {
				ToastUtil.showToast("稍微给点意见吧");
				return;
			}
			addFeedBack(content);
			break;
		default:
			break;
		}
	}

	private void addFeedBack(String content) {
		showDialog();
		AddFeedBackTask task = new AddFeedBackTask();
		task.setmCallBack(new AbstractTaskPostCallBack<RetError>() {
			@Override
			public void taskFinish(RetError result) {
				if (result == RetError.NONE) {
					ToastUtil.showToast("提交成功");
					finishThisActivity();
				}
			}
		});
		FeedBack back = new FeedBack();
		back.setFeedback_content(content);
		task.executeParallel(back);
	}
}
