package com.biyanzhi.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.biyanzhi.R;
import com.biyanzhi.data.PictureScore;
import com.biyanzhi.utils.UniversalImageLoadTool;
import com.biyanzhi.view.RoundAngleImageView;

public class PictureScoreUserAdapter extends BaseAdapter {
	private List<PictureScore> list = new ArrayList<PictureScore>();
	private Context mContext;

	public PictureScoreUserAdapter(Context context, List<PictureScore> list) {
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
					R.layout.playscore_user_item, null);
			holder = new ViewHolder();
			holder.img_user_avatar = (RoundAngleImageView) contentView
					.findViewById(R.id.img_avatar);
			holder.txt_user_name = (TextView) contentView
					.findViewById(R.id.txt_user_name);
			holder.txt_score = (TextView) contentView
					.findViewById(R.id.txt_play_score);
			holder.ratingBar = (RatingBar) contentView
					.findViewById(R.id.ratingbar);
			contentView.setTag(holder);
		} else {
			holder = (ViewHolder) contentView.getTag();
		}
		if ("Å®".equals(list.get(position).getUser().getUser_gender())) {
			holder.txt_user_name.setText(Html.fromHtml("<font color=#FF9a9a>"
					+ list.get(position).getUser().getUser_name() + "</font>"));
		} else {
			holder.txt_user_name.setText(Html.fromHtml("<font color=#19b5ee>"
					+ list.get(position).getUser().getUser_name() + "</font>"));
		}
		holder.ratingBar.setRating((float) list.get(position)
				.getPicture_score() / 20);
		holder.txt_score.setText(list.get(position).getPicture_score() + "(·Ö)");
		UniversalImageLoadTool.disPlay(list.get(position).getUser()
				.getUser_avatar(), holder.img_user_avatar,
				R.drawable.default_avatar);
		return contentView;
	}

	static class ViewHolder {
		private RoundAngleImageView img_user_avatar;
		private TextView txt_user_name;
		private TextView txt_score;
		private RatingBar ratingBar;

	}
}
