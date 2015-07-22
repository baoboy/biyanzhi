package com.biyanzhi.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.biyanzhi.R;
import com.biyanzhi.activity.SelectPKPictureActivity;
import com.biyanzhi.data.Picture;
import com.biyanzhi.utils.DialogUtil;
import com.biyanzhi.utils.SharedUtils;
import com.biyanzhi.utils.UniversalImageLoadTool;
import com.biyanzhi.utils.Utils;
import com.biyanzhi.view.RoundAnglePictureImageView;
import com.biyianzhi.interfaces.ConfirmDialog;

import fynn.app.PromptDialog;

public class PictureAdapter extends BaseAdapter {
	private List<Picture> mLists;
	private Context mContext;
	private int width;

	public PictureAdapter(Context context, List<Picture> mLists) {
		this.mLists = mLists;
		this.mContext = context;
		width = Utils.getSecreenWidth(context) / 2 - 18;// 24 margin值
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		Picture picture = mLists.get(position);
		if (convertView == null) {
			LayoutInflater layoutInflator = LayoutInflater.from(mContext);
			convertView = layoutInflator.inflate(R.layout.grid_view_item_1,
					null);
			holder = new ViewHolder();
			holder.imageView = (RoundAnglePictureImageView) convertView
					.findViewById(R.id.img);
			holder.txt_score = (TextView) convertView
					.findViewById(R.id.txt_score);
			LayoutParams layoutParams = holder.imageView.getLayoutParams();
			layoutParams.width = width;
			layoutParams.height = width;
			holder.imageView.setLayoutParams(layoutParams);
			holder.img_pk = (ImageView) convertView.findViewById(R.id.img_pk);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.txt_score.setText(Html.fromHtml("<font color=#F06617>"
				+ picture.getScore_number() + "人</font> 评分 平均颜值"
				+ "<font color=#F06617>(" + picture.getAverage_score()
				+ "分)</font>"));

		String path = picture.getPicture_image_url();
		if (!path.startsWith("http")) {
			path = "file://" + path;
		}
		UniversalImageLoadTool.disPlay(path, holder.imageView,
				R.drawable.picture_default_head);
		if (picture.getUser() != null) {
			if (SharedUtils.getAPPUserGender().equals(
					picture.getUser().getUser_gender())
					&& picture.getPublisher_id() != SharedUtils.getIntUid()) {
				holder.img_pk.setVisibility(View.VISIBLE);
			} else {
				holder.img_pk.setVisibility(View.GONE);
			}
		}
		holder.img_pk.setOnClickListener(new OnClick());
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
		TextView timeView;
		TextView txt_score;
		ImageView img_pk;
	}

	class OnClick implements OnClickListener {
		public OnClick() {

		}

		@Override
		public void onClick(View v) {
			pkPrompt();
		}

	}

	private void pkPrompt() {
		PromptDialog.Builder dialog = DialogUtil.confirmDialog(mContext,
				"是否要和TA进行PK", "是", "否", new ConfirmDialog() {

					@Override
					public void onOKClick() {
						mContext.startActivity(new Intent(mContext,
								SelectPKPictureActivity.class));
						Utils.leftOutRightIn(mContext);
					}

					public void onCancleClick() {

					}
				});
		dialog.show();
	}
}