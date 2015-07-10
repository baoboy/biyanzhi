package com.biyanzhi.findpassword;

import android.app.Dialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.biyanzhi.R;
import com.biyanzhi.data.User;
import com.biyanzhi.enums.RetError;
import com.biyanzhi.task.VerifyFindPasswordCellPhoneTask;
import com.biyanzhi.utils.DialogUtil;
import com.biyanzhi.utils.ToastUtil;
import com.biyanzhi.utils.Utils;
import com.biyanzhi.view.MyEditTextDeleteImg;
import com.biyianzhi.interfaces.AbstractTaskPostCallBack;
import com.biyianzhi.interfaces.MyEditTextWatcher;
import com.biyianzhi.interfaces.MyEditTextWatcher.OnTextLengthChange;
import com.biyianzhi.interfaces.OnEditFocusChangeListener;

public class FindPasswordGetVerifyCode extends FindPasswordStep implements
		OnClickListener, OnTextLengthChange {
	private MyEditTextDeleteImg edit_phone;
	private Button btn_next;

	private Dialog dialog;

	public FindPasswordGetVerifyCode(FindPasswordActivity activity,
			View contentRootView) {
		super(activity, contentRootView);
	}

	@Override
	public void initView() {
		edit_phone = (MyEditTextDeleteImg) findViewById(R.id.edit_telephone);
		edit_phone.setTag("phone_num");
		btn_next = (Button) findViewById(R.id.btnNext);
	}

	@Override
	public void setListener() {
		btn_next.setOnClickListener(this);
		edit_phone.setOnFocusChangeListener(new OnEditFocusChangeListener(
				edit_phone, mContext));
		edit_phone.addTextChangedListener(new MyEditTextWatcher(edit_phone,
				mContext, this));
	}

	private void getVerifyCode(final String phone) {
		User re = new User();
		re.setUser_cellphone(phone);
		VerifyFindPasswordCellPhoneTask task = new VerifyFindPasswordCellPhoneTask();
		task.setmCallBack(new AbstractTaskPostCallBack<RetError>() {
			@Override
			public void taskFinish(RetError result) {
				dialog.dismiss();
				if (result != RetError.NONE) {
					return;
				}
				mOnNextListener.next();
				mActivity.setCell_phone(phone);
			}
		});
		task.executeParallel(re);
	}

	@Override
	public void onClick(View v) {
		String phone = edit_phone.getText().toString().replaceAll(" ", "");
		if (!Utils.isPhoneNum(phone)) {
			ToastUtil.showToast("手机号格式不正确", Toast.LENGTH_SHORT);
			return;
		}
		dialog = DialogUtil.createLoadingDialog(mContext, "请稍候");
		dialog.show();
		getVerifyCode(phone);
	}

	@Override
	public void onTextLengthChanged(boolean isBlank) {
		if (!isBlank) {
			if (edit_phone.getText().toString().length() != 0) {
				btn_next.setEnabled(true);
				btn_next.setBackgroundResource(R.drawable.btn_selector);
				return;
			}
		}
		btn_next.setEnabled(false);
		btn_next.setBackgroundResource(R.drawable.btn_disenable_bg);
	}
}
