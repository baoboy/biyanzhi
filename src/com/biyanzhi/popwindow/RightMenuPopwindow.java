package com.biyanzhi.popwindow;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.biyanzhi.R;

public class RightMenuPopwindow implements OnItemClickListener {
	private PopupWindow popupWindow;
	private Context mContext;
	private View v;
	private View view;
	private ListView listview;
	private MyAdapter adapter;
	private OnMenuListOnclick callback;
	private String[] listdata;

	public RightMenuPopwindow(Context context, View v, String[] listdata) {
		this.mContext = context;
		this.v = v;
		LayoutInflater inflater = LayoutInflater.from(mContext);
		view = inflater.inflate(R.layout.right_menu_popwindow, null);
		this.listdata = listdata;
		initView();
		initPopwindow();
	}

	private void initView() {
		listview = (ListView) view.findViewById(R.id.listView1);
		listview.setOnItemClickListener(this);
		adapter = new MyAdapter();
		listview.setAdapter(adapter);
	}

	/**
	 * ��ʼ��popwindow
	 */
	@SuppressWarnings("deprecation")
	private void initPopwindow() {
		popupWindow = new PopupWindow(view, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		// �����Ϊ�˵��������Back��Ҳ��ʹ����ʧ�����Ҳ�����Ӱ����ı�����������ģ�
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
	}

	/**
	 * popwindow����ʾ
	 */
	public void show() {
		int[] location = new int[2];
		v.getLocationOnScreen(location);
		popupWindow.showAtLocation(v, Gravity.NO_GRAVITY,
				location[0] + v.getWidth(), location[1] + v.getHeight()); // ʹ��ۼ�
		popupWindow.setFocusable(true);
		// ����������������ʧ
		popupWindow.setOutsideTouchable(true);
		// ˢ��״̬
		popupWindow.update();
	}

	// ����
	public void dismiss() {
		popupWindow.dismiss();
	}

	class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return listdata.length;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.right_menu_item, null);
				holder = new ViewHolder();
				holder.laybg = (LinearLayout) convertView
						.findViewById(R.id.laybg);
				holder.text = (TextView) convertView.findViewById(R.id.text);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.text.setText(listdata[position]);
			return convertView;
		}
	}

	class ViewHolder {
		TextView text;
		LinearLayout laybg;
	}

	public void setOnlistOnclick(OnMenuListOnclick callback) {
		this.callback = callback;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {
		callback.onclick(position);
		dismiss();
	}

	public interface OnMenuListOnclick {
		void onclick(int position);
	}
}
