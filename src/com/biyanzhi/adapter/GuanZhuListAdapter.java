package com.biyanzhi.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.biyanzhi.R;
import com.biyanzhi.data.User;
import com.biyanzhi.utils.UniversalImageLoadTool;
import com.biyanzhi.view.RoundAngleImageView;

public class GuanZhuListAdapter extends BaseAdapter {
	private List<User> list = new ArrayList<User>();
	private Context mContext;

	public GuanZhuListAdapter(Context context, List<User> list) {
		this.mContext = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int position, View contentView, ViewGroup arg2) {
		ViewHolder holder = null;
		if (contentView == null) {
			contentView = LayoutInflater.from(mContext).inflate(
					R.layout.guanzhu_listview_item, null);
			holder = new ViewHolder();
			holder.img_user_avatar = (RoundAngleImageView) contentView
					.findViewById(R.id.img_avatar);
			holder.txt_user_name = (TextView) contentView
					.findViewById(R.id.txt_user_name);
			holder.txt_address = (TextView) contentView
					.findViewById(R.id.txt_address);
			contentView.setTag(holder);
		} else {
			holder = (ViewHolder) contentView.getTag();
		}
		if ("Ů".equals(list.get(position).getUser_gender())) {
			holder.txt_user_name.setText(Html.fromHtml("<font color=#FF9a9a>"
					+ list.get(position).getUser_name() + "</font>"));
		} else {
			holder.txt_user_name.setText(Html.fromHtml("<font color=#19b5ee>"
					+ list.get(position).getUser_name() + "</font>"));
		}
		holder.txt_address.setText(list.get(position).getUser_address());
		UniversalImageLoadTool.disPlay(list.get(position).getUser_avatar(),
				holder.img_user_avatar, R.drawable.default_avatar);
		return contentView;
	}

	static class ViewHolder {
		private RoundAngleImageView img_user_avatar;
		private TextView txt_user_name;
		private TextView txt_address;

		TextView alpha;

	}
}
