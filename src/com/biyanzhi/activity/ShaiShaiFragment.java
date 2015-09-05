package com.biyanzhi.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.biyanzhi.R;
import com.biyanzhi.data.ShuoShuo;
import com.biyanzhi.enums.RetError;
import com.biyanzhi.task.UpLoadShuoShuoTask;
import com.biyanzhi.utils.DialogUtil;
import com.biyanzhi.utils.SharedUtils;
import com.biyanzhi.utils.ToastUtil;
import com.biyanzhi.utils.Utils;
import com.biyianzhi.interfaces.AbstractTaskPostCallBack;

public class ShaiShaiFragment extends Fragment implements OnClickListener {
	private Dialog dialog;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.shaishai_fragment_layout, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
	}

	private void initView() {
		setListener();
	}

	private void setListener() {
	}

	public void addShuoShuo() {
		startActivityForResult(new Intent(getActivity(),
				PublicshShuoShuoActivity.class), 200);
		Utils.leftOutRightIn(getActivity());
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		default:
			break;
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (data == null) {
			return;
		}
		if (requestCode == 200) {
			ShuoShuo shuo = (ShuoShuo) data.getSerializableExtra("shuoshuo");
			shuo.setPublisher_id(SharedUtils.getIntUid());
			shuo.setPublisher_avatar(SharedUtils.getAPPUserAvatar());
			shuo.setPublisher_name(SharedUtils.getAPPUserName());
			upLoadShuoShuo(shuo);
		}
	}

	private void upLoadShuoShuo(ShuoShuo shuo) {
		dialog = DialogUtil.createLoadingDialog(getActivity());
		dialog.show();
		UpLoadShuoShuoTask task = new UpLoadShuoShuoTask();
		task.setmCallBack(new AbstractTaskPostCallBack<RetError>() {
			@Override
			public void taskFinish(RetError result) {
				if (dialog != null) {
					dialog.dismiss();
				}
				if (result != RetError.NONE) {
					return;
				}
				ToastUtil.showToast("发布成功");
			}
		});
		task.executeParallel(shuo);
	}
}
