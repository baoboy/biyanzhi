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
import android.widget.ImageView;

import com.biyanzhi.R;
import com.biyanzhi.activity.SelectPK2PictureActivity;
import com.biyanzhi.data.PKData;
import com.biyanzhi.showbigimage.ImagePagerActivity;
import com.biyanzhi.utils.Constants;
import com.biyanzhi.utils.UniversalImageLoadTool;
import com.biyanzhi.utils.Utils;
import com.biyanzhi.view.RoundAngleImageView;

public class PKAdapter extends BaseAdapter {
	private Context mContext;
	private int width;
	private List<PKData> mlists;

	public PKAdapter(Context mContext, List<PKData> mlists) {
		this.mContext = mContext;
		width = Utils.getSecreenWidth(mContext) / 2 - 80;// 24 margin值
		this.mlists = mlists;
	}

	@Override
	public int getCount() {
		return mlists.size();
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
	public View getView(int position, View convertView, ViewGroup arg2) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.pk_item, null);
			holder = new ViewHolder();
			holder.img_vs = (ImageView) convertView.findViewById(R.id.img_vs);
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
		String pk1_picture = mlists.get(position).getPk1()
				.getPk1_user_picture();
		String pk2_picture = mlists.get(position).getPk2()
				.getPk2_user_picture();
		UniversalImageLoadTool.disPlay(pk1_picture, holder.img_pk1,
				R.drawable.picture_default_head);
		if ("女".equals(mlists.get(position).getPk1().getPk1_user_gender())) {
			holder.btn_pk1.setText("她美");
			holder.btn_pk2.setText("她美");
			holder.btn_pk1.setBackground(mContext.getResources().getDrawable(
					R.drawable.pk_girl_btn));
			holder.btn_pk2.setBackground(mContext.getResources().getDrawable(
					R.drawable.pk_girl_btn));
			holder.img_vs.setImageResource(R.drawable.girl_vs);
			if (mlists.get(position).getPk2().getPk2_user_id() == 0) {
				UniversalImageLoadTool.disPlay(pk2_picture, holder.img_pk2,
						R.drawable.girl_pk_default1);
			} else {
				UniversalImageLoadTool.disPlay(pk2_picture, holder.img_pk2,
						R.drawable.picture_default_head);
			}
		} else {
			holder.btn_pk1.setText("他帅");
			holder.btn_pk2.setText("他帅");
			holder.btn_pk1.setBackground(mContext.getResources().getDrawable(
					R.drawable.pk_boy_btn));
			holder.btn_pk2.setBackground(mContext.getResources().getDrawable(
					R.drawable.pk_boy_btn));
			holder.img_vs.setImageResource(R.drawable.boy_vs);
			if (mlists.get(position).getPk2().getPk2_user_id() == 0) {
				UniversalImageLoadTool.disPlay(pk2_picture, holder.img_pk2,
						R.drawable.boy_pk_default1);
			} else {
				UniversalImageLoadTool.disPlay(pk2_picture, holder.img_pk2,
						R.drawable.picture_default_head);
			}
		}
		if (mlists.get(position).getPk2().getPk2_user_id() == 0) {
			holder.btn_pk1.setVisibility(View.GONE);
			holder.btn_pk2.setVisibility(View.GONE);
		} else {
			holder.btn_pk1.setVisibility(View.VISIBLE);
			holder.btn_pk2.setVisibility(View.VISIBLE);
		}
		holder.img_pk1.setOnClickListener(new OnClick(position));
		holder.img_pk2.setOnClickListener(new OnClick(position));
		return convertView;
	}

	class ViewHolder {
		RoundAngleImageView img_pk1;
		RoundAngleImageView img_pk2;
		Button btn_pk1;
		Button btn_pk2;
		ImageView img_vs;

	}

	class OnClick implements OnClickListener {
		private int position;

		public OnClick(int position) {
			this.position = position;
		}

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.img_pk1:
				intent(mlists.get(position).getPk1().getPk1_user_picture());
				break;
			case R.id.img_pk2:
				if (mlists.get(position).getPk2().getPk2_user_id() == 0) {
					mContext.startActivity(new Intent(mContext,
							SelectPK2PictureActivity.class).putExtra("pk_id",
							mlists.get(position).getPk_id()));
					Utils.leftOutRightIn(mContext);
				} else {
					intent(mlists.get(position).getPk2().getPk2_user_picture());

				}
				break;
			default:
				break;
			}
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
