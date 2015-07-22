package com.biyanzhi.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;

import com.biyanzhi.R;
import com.biyanzhi.data.Picture;
import com.biyanzhi.utils.UniversalImageLoadTool;
import com.biyanzhi.utils.Utils;
import com.biyanzhi.view.RoundAngleImageView;

public class SelectPKPictureAdapter extends BaseAdapter {
	private List<Picture> mLists;
	private Context mContext;
	private int width;

	public SelectPKPictureAdapter(Context context, List<Picture> mLists) {
		this.mLists = mLists;
		this.mContext = context;
		width = Utils.getSecreenWidth(context) / 2 - 18;// 24 marginֵ
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		Picture picture = mLists.get(position);
		if (convertView == null) {
			LayoutInflater layoutInflator = LayoutInflater.from(mContext);
			convertView = layoutInflator.inflate(R.layout.pk_gridview_item,
					null);
			holder = new ViewHolder();
			holder.imageView = (RoundAngleImageView) convertView
					.findViewById(R.id.img);
			LayoutParams layoutParams = holder.imageView.getLayoutParams();
			layoutParams.width = width;
			layoutParams.height = width;
			holder.imageView.setLayoutParams(layoutParams);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		String path = picture.getPicture_image_url();
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
		RoundAngleImageView imageView;

	}

}