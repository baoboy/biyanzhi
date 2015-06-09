package com.biyanzhi.popwindow;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.biyanzhi.R;

public class SharePopwindow implements OnClickListener, OnItemClickListener {
	private PopupWindow popupWindow;
	private Context mContext;
	private View v;

	private Button btnCancle;
	private View view;
	private String fileName = "";
	private SelectMenuOnclick mSelectOnclick;

	private RelativeLayout layout_parent;

	private LinearLayout layout_qq_haoyou;
	private LinearLayout layout_qq_kongjian;
	private LinearLayout layout_weixin_haoyou;
	private LinearLayout layout_weixin_pengyouquan;

	public void setmSelectOnclick(SelectMenuOnclick mSelectOnclick) {
		this.mSelectOnclick = mSelectOnclick;
	}

	public SharePopwindow(Context context, View v) {
		this.mContext = context;
		this.v = v;
		LayoutInflater inflater = LayoutInflater.from(mContext);
		view = inflater.inflate(R.layout.share_popwindow_layout, null);
		initView();
		initPopwindow();

	}

	private void initView() {
		layout_qq_haoyou = (LinearLayout) view
				.findViewById(R.id.layout_qq_haoyou);
		layout_qq_kongjian = (LinearLayout) view
				.findViewById(R.id.layout_qq_kongjian);
		layout_weixin_haoyou = (LinearLayout) view
				.findViewById(R.id.layout_weixin_haoyou);
		layout_weixin_pengyouquan = (LinearLayout) view
				.findViewById(R.id.layout_weixin_pengyouquan);
		layout_qq_haoyou.setOnClickListener(this);
		layout_qq_kongjian.setOnClickListener(this);
		layout_weixin_haoyou.setOnClickListener(this);
		layout_weixin_pengyouquan.setOnClickListener(this);
		layout_parent = (RelativeLayout) view.findViewById(R.id.layout_parent);
		layout_parent.getBackground().setAlpha(150);
		btnCancle = (Button) view.findViewById(R.id.btn_cancel);
		btnCancle.setOnClickListener(this);
		layout_parent.setOnClickListener(this);
	}

	/**
	 * 初始化popwindow
	 */
	private void initPopwindow() {
		popupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		// 这个是为了点击�?返回Back”也能使其消失，并且并不会影响你的背景（很神奇的�?
		// popupWindow.setBackgroundDrawable(new BitmapDrawable());
		// popupWindow.setAnimationStyle(R.style.AnimBottom);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());

	}

	/**
	 * popwindow的显�?
	 */
	public void show() {
		popupWindow.showAtLocation(v, Gravity.BOTTOM
				| Gravity.CENTER_HORIZONTAL, 0, 0);
		// 使其聚集
		popupWindow.setFocusable(true);
		// 设置允许在外点击消失
		popupWindow.setOutsideTouchable(true);
		// 刷新状�?
		popupWindow.update();
	}

	// 隐藏
	public void dismiss() {
		popupWindow.dismiss();
	}

	/**
	 * 返回拍照之后保存路径
	 */
	public String getTakePhotoPath() {
		return fileName;
	}

	@Override
	public void onClick(View v) {
		dismiss();
		switch (v.getId()) {
		case R.id.btn_cancel:
			dismiss();
			break;
		case R.id.layout_qq_haoyou:
			mSelectOnclick.onClickPosition(0);
			break;
		case R.id.layout_qq_kongjian:
			mSelectOnclick.onClickPosition(1);
			break;
		case R.id.layout_weixin_haoyou:
			mSelectOnclick.onClickPosition(2);
			break;
		case R.id.layout_weixin_pengyouquan:
			mSelectOnclick.onClickPosition(3);
			break;
		default:
			break;
		}

	}

	public interface SelectMenuOnclick {
		void onClickPosition(int position);

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		dismiss();
		if (mSelectOnclick == null)
			return;
		mSelectOnclick.onClickPosition(arg2);
	}

	class ViewHolder {
		TextView btn_menu;
	}
}
