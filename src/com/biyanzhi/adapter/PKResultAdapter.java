package com.biyanzhi.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.biyanzhi.R;
import com.biyanzhi.data.PKResult;
import com.biyanzhi.utils.UniversalImageLoadTool;
import com.biyanzhi.utils.Utils;
import com.biyanzhi.view.RoundAnglePictureImageView;

public class PKResultAdapter extends BaseAdapter {
	private List<PKResult> mLists;
	private Context mContext;
	private int width;

	public PKResultAdapter(Context context, List<PKResult> mLists) {
		this.mLists = mLists;
		this.mContext = context;
		width = Utils.getSecreenWidth(context) / 2 - 18;// 24 marginÖµ
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		PKResult result = mLists.get(position);
		if (convertView == null) {
			LayoutInflater layoutInflator = LayoutInflater.from(mContext);
			convertView = layoutInflator.inflate(
					R.layout.pk_reslult_item_layout, null);
			holder = new ViewHolder();
			holder.imageView = (RoundAnglePictureImageView) convertView
					.findViewById(R.id.img);
			holder.txt_pk_result = (TextView) convertView
					.findViewById(R.id.txt_pk_result);
			LayoutParams layoutParams = holder.imageView.getLayoutParams();
			layoutParams.width = width;
			layoutParams.height = width;
			holder.imageView.setLayoutParams(layoutParams);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.txt_pk_result.setText("×Ü   " + result.getUser_win_count() + "Ê¤/"
				+ result.getUser_fail_count() + "°Ü");
		if ("Å®".equals(result.getUser().getUser_gender())) {
			holder.txt_pk_result.setTextColor(mContext.getResources().getColor(
					R.color.girl));
		} else {
			holder.txt_pk_result.setTextColor(mContext.getResources().getColor(
					R.color.boy));
		}
		String path = result.getUser().getUser_avatar();
		if (!path.startsWith("http")) {
			path = "file://" + path;
		}
		UniversalImageLoadTool.disPlay(path, holder.imageView,
				R.drawable.picture_default_head);
		return convertView;
	}

	@Override
	public int getCount() {
		return mLists.size();
	}

	@Override
	public Object getItem(int arg0) {
		return mLists.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	class ViewHolder {
		RoundAnglePictureImageView imageView;
		TextView txt_pk_result;
	}

}