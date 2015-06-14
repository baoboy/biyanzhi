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

public class MessageCopyPopWindow implements OnItemClickListener {
	private PopupWindow popupWindow;
	private Context mContext;
	private View v;
	private View v_parent;
	private View view;
	private ListView listview;
	private MyAdapter adapter;
	private OnlistOnclick callback;
	private String[] listdata;

	public MessageCopyPopWindow(Context context, View v, View v_parent,
			String[] listdata) {
		this.mContext = context;
		this.v = v;
		this.v_parent = v_parent;
		LayoutInflater inflater = LayoutInflater.from(mContext);
		view = inflater.inflate(R.layout.message_prompt, null);
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
		int[] location1 = new int[2];
		v.getLocationOnScreen(location1);
		v_parent.getLocationOnScreen(location);
		popupWindow.showAtLocation(v, Gravity.NO_GRAVITY, location1[0],
				location[1] - v_parent.getHeight()); // ʹ��ۼ�
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

	public void setOnlistOnclick(OnlistOnclick callback) {
		this.callback = callback;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {
		callback.onclick(position);
		dismiss();
	}

	public interface OnlistOnclick {
		void onclick(int position);
	}
}
