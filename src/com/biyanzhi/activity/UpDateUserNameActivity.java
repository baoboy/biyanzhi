package com.biyanzhi.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.biyanzhi.R;
import com.biyanzhi.data.User;
import com.biyanzhi.enums.RetError;
import com.biyanzhi.task.UpDateUserNameTask;
import com.biyanzhi.utils.Constants;
import com.biyanzhi.utils.DialogUtil;
import com.biyanzhi.utils.SharedUtils;
import com.biyanzhi.utils.ToastUtil;
import com.biyianzhi.interfaces.AbstractTaskPostCallBack;

public class UpDateUserNameActivity extends BaseActivity {
	private TextView txt_title;
	private EditText edit_content;
	private Button btn_save;
	private ImageView back;

	private Dialog dialog;
	private User user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_up_date_user_name);
		initView();
		setValue();
		user = new User();
	}

	private void initView() {
		back = (ImageView) findViewById(R.id.back);
		txt_title = (TextView) findViewById(R.id.title_txt);
		edit_content = (EditText) findViewById(R.id.txt_content);
		btn_save = (Button) findViewById(R.id.btn_save);
		setListener();
	}

	private void setListener() {
		back.setOnClickListener(this);
		btn_save.setOnClickListener(this);
	}

	private void setValue() {
		edit_content.setText(SharedUtils.getAPPUserName());
		edit_content.setSelection(edit_content.getText().toString().length());
		txt_title.setText("昵称");
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_save:
			String value = edit_content.getText().toString().trim();
			if (value.length() == 0) {
				ToastUtil.showToast("昵称不能为空", Toast.LENGTH_SHORT);
				return;
			}
			if (value.equals(SharedUtils.getAPPUserName())) {
				finishThisActivity();
				return;
			}
			upDate(value);
			break;
		case R.id.back:
			finishThisActivity();
			break;
		default:
			break;
		}
	}

	private void upDate(final String value) {
		dialog = DialogUtil.createLoadingDialog(this, "请稍候");
		dialog.show();
		user.setUser_name(value);
		UpDateUserNameTask task = new UpDateUserNameTask();
		task.setmCallBack(new AbstractTaskPostCallBack<RetError>() {
			@Override
			public void taskFinish(RetError result) {
				if (dialog != null) {
					dialog.dismiss();
				}
				if (result != RetError.NONE) {
					return;
				}
				ToastUtil.showToast("操作成功", Toast.LENGTH_SHORT);
				SharedUtils.setAPPUserName(value);
				sendBroadcast(new Intent(Constants.UPDATE_USER_NAME));
				finishThisActivity();
			}
		});
		task.executeParallel(user);

	}

}
