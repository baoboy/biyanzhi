package com.biyanzhi.adapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.biyanzhi.R;
import com.biyanzhi.showbigimage.ImagePagerActivity;
import com.biyanzhi.utils.Constants;
import com.biyanzhi.utils.UniversalImageLoadTool;
import com.biyanzhi.utils.Utils;
import com.biyanzhi.view.RoundAngleImageView;

public class PKAdapter extends BaseAdapter {
	private Context mContext;
	private int width;

	public PKAdapter(Context mContext) {
		this.mContext = mContext;
		width = Utils.getSecreenWidth(mContext) / 2 - 80;// 24 marginֵ

	}

	@Override
	public int getCount() {
		return 10;
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
	public View getView(int arg0, View convertView, ViewGroup arg2) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.pk_item, null);
			holder = new ViewHolder();
			holder.img_pk1 = (RoundAngleImageView) convertView
					.findViewById(R.id.img_pk1);
			holder.img_pk2 = (RoundAngleImageView) convertView
					.findViewById(R.id.img_pk2);
			holder.btn_pk1 = (Button) convertView.findViewById(R.id.btn_pk1);
			holder.btn_pk2 = (Button) convertView.findViewById(R.id.btn_pk2);
			holder.btn_pk1
					.setLayoutParams(getButtonLayoutParams(holder.btn_pk1));
			holder.btn_pk2
					.setLayoutParams(getButtonLayoutParams(holder.btn_pk2));
			holder.img_pk1.setLayoutParams(getLayoutParams(holder.img_pk1));
			holder.img_pk2.setLayoutParams(getLayoutParams(holder.img_pk2));
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		UniversalImageLoadTool.disPlay(
				"http://cdn.7kk.com//201407/08/1747414993488.jpg",
				holder.img_pk1, R.drawable.picture_default_head);
		UniversalImageLoadTool
				.disPlay(
						"http://www.sinaimg.cn/dy/slidenews/21_img/2011_37/2236_511531_466668.jpg",
						holder.img_pk2, R.drawable.picture_default_head);
		holder.img_pk1.setOnClickListener(new OnClick(
				"http://cdn.7kk.com//201407/08/1747414993488.jpg"));
		holder.img_pk2
				.setOnClickListener(new OnClick(
						"http://www.sinaimg.cn/dy/slidenews/21_img/2011_37/2236_511531_466668.jpg"));
		return convertView;
	}

	class ViewHolder {
		RoundAngleImageView img_pk1;
		RoundAngleImageView img_pk2;
		Button btn_pk1;
		Button btn_pk2;

	}

	class OnClick implements OnClickListener {
		private String path = "";

		public OnClick(String path) {
			this.path = path;
		}

		@Override
		public void onClick(View v) {
			intent(path);
		}

	}

	private void intent(String path) {
		List<String> imgUrl = new ArrayList<String>();
		imgUrl.add(path);
		Intent intent = new Intent(mContext, ImagePagerActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable(Constants.EXTRA_IMAGE_URLS,
				(Serializable) imgUrl);
		intent.putExtras(bundle);
		intent.putExtra(Constants.EXTRA_IMAGE_INDEX, 1);
		mContext.startActivity(intent);
	}

	private LayoutParams getLayoutParams(RoundAngleImageView img) {
		LayoutParams layoutParams = img.getLayoutParams();
		layoutParams.width = width;
		layoutParams.height = width;
		return layoutParams;
	}

	private LayoutParams getButtonLayoutParams(Button btn) {
		LayoutParams layoutParams = btn.getLayoutParams();
		layoutParams.width = width;
		return layoutParams;
	}
}
