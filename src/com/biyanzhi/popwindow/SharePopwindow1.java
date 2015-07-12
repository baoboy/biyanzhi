package com.biyanzhi.popwindow;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

import com.biyanzhi.R;
import com.biyanzhi.utils.Utils;

public class SharePopwindow1 implements OnClickListener, OnItemClickListener {
	private PopupWindow popupWindow;
	private Context mContext;
	private View v;
	private List<Share> lists = new ArrayList<Share>();
	private View view;
	private String fileName = "";
	private SelectMenuOnclick mSelectOnclick;

	private LinearLayout layout_parent;

	private GridView mGridView;

	public void setmSelectOnclick(SelectMenuOnclick mSelectOnclick) {
		this.mSelectOnclick = mSelectOnclick;
	}

	public SharePopwindow1(Context context, View v) {
		this.mContext = context;
		this.v = v;
		LayoutInflater inflater = LayoutInflater.from(mContext);
		view = inflater.inflate(R.layout.share_layout, null);
		initData();
		initView();
		initPopwindow();

	}

	private void initView() {
		layout_parent = (LinearLayout) view.findViewById(R.id.layout_parent);
		// layout_parent.getBackground().setAlpha(150);
		layout_parent.setOnClickListener(this);
		mGridView = (GridView) view.findViewById(R.id.gridView1);
		mGridView.setAdapter(new MyAdapter());
		mGridView.setOnItemClickListener(this);
	}

	private void initData() {
		lists.add(new Share("微信朋友圈", R.drawable.share_wx_pyq));
		lists.add(new Share("微信好友", R.drawable.share_wx_py));
		lists.add(new Share("QQ好友", R.drawable.share_qq));
		lists.add(new Share("QQ空间", R.drawable.share_qzone));
		lists.add(new Share("新浪微博", R.drawable.share_sina));

	}

	class Share {
		private String share_name;
		private int share_img_id;

		public Share(String share_name, int share_img_id) {
			this.share_img_id = share_img_id;
			this.share_name = share_name;
		}
	}

	/**
	 * 初始化popwindow
	 */
	private void initPopwindow() {
		popupWindow = new PopupWindow(view,
				Utils.getSecreenWidth(mContext) - 100,
				LayoutParams.WRAP_CONTENT);
		// 这个是为了点击�?返回Back”也能使其消失，并且并不会影响你的背景（很神奇的�?
		// popupWindow.setAnimationStyle(R.style.AnimBottom);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		backgroundAlpha(0.4f);
		popupWindow.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				backgroundAlpha(1f);

			}
		});

	}

	/**
	 * 设置添加屏幕的背景透明度
	 * 
	 * @param bgAlpha
	 */
	public void backgroundAlpha(float bgAlpha) {
		WindowManager.LayoutParams lp = ((Activity) mContext).getWindow()
				.getAttributes();
		lp.alpha = bgAlpha; // 0.0-1.0
		((Activity) mContext).getWindow().setAttributes(lp);
	}

	/**
	 * popwindow的显�?
	 */
	public void show() {
		popupWindow.showAtLocation(v, Gravity.CENTER
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
		backgroundAlpha(1f);
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

	class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return lists.size();
		}

		@Override
		public Object getItem(int arg0) {
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup arg2) {
			ViewHolder holder;

			if (convertView == null) {
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.share_item_layout, null);
				holder = new ViewHolder();
				holder.share_img = (ImageView) convertView
						.findViewById(R.id.share_img);
				holder.share_name = (TextView) convertView
						.findViewById(R.id.share_txt);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.share_img.setImageResource(lists.get(position).share_img_id);
			holder.share_name.setText(lists.get(position).share_name);
			return convertView;
		}

	}

	class ViewHolder {
		TextView share_name;
		ImageView share_img;
	}
}
