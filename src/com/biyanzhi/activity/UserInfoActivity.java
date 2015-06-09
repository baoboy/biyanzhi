package com.biyanzhi.activity;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.biyanzhi.R;
import com.biyanzhi.data.User;
import com.biyanzhi.data.UserInfo;
import com.biyanzhi.enums.RetError;
import com.biyanzhi.task.GetUserInfoTask;
import com.biyanzhi.utils.DialogUtil;
import com.biyanzhi.utils.FastBlur;
import com.biyanzhi.utils.UniversalImageLoadTool;
import com.biyanzhi.utils.Utils;
import com.biyanzhi.view.CircularImage;
import com.biyianzhi.interfaces.AbstractTaskPostCallBack;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;

public class UserInfoActivity extends BaseActivity {
	private RelativeLayout layout_title;
	private Button btn_info;
	private Button btn_yanzhi;
	private View line1, line2, line3;
	private ViewFlipper mVfFlipper;
	private TextView txt_title;
	private ImageView img_avatar_bg;
	private CircularImage img_avatar;
	private int user_id;

	private UserInfo info = new UserInfo();

	private User user;
	private Dialog dialog;

	private UserInfoInfoView info_View;
	private UserInfoYanZhiView yanzhi_View;

	private ScrollView scrollView;
	private LinearLayout layout_bottom;
	private View bottom_line;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_info);
		user_id = getIntent().getIntExtra("user_id", 0);
		info.setUser_id(user_id);
		initView();
		getValue();
	}

	@SuppressWarnings("deprecation")
	private void initView() {
		layout_bottom = (LinearLayout) findViewById(R.id.layout_bottom);
		bottom_line = (View) findViewById(R.id.line_bottom);
		scrollView = (ScrollView) findViewById(R.id.scrollView1);
		scrollView.setVisibility(View.GONE);
		img_avatar = (CircularImage) findViewById(R.id.img_avatar);
		img_avatar_bg = (ImageView) findViewById(R.id.img_avatar_bg);
		img_avatar_bg.setAlpha(100);
		txt_title = (TextView) findViewById(R.id.title_txt);
		Utils.getFocus(txt_title);
		mVfFlipper = (ViewFlipper) findViewById(R.id.viewflipper);
		mVfFlipper.setDisplayedChild(1);
		layout_title = (RelativeLayout) findViewById(R.id.title);
		layout_title.setBackgroundResource(R.color.black);
		layout_title.getBackground().setAlpha(100);
		btn_info = (Button) findViewById(R.id.btn_info);
		btn_yanzhi = (Button) findViewById(R.id.btn_yanzhi);
		line1 = (View) findViewById(R.id.line1);
		line2 = (View) findViewById(R.id.line2);
		line3 = (View) findViewById(R.id.line3);
		line2.getBackground().setAlpha(120);
		line1.getBackground().setAlpha(120);
		line3.getBackground().setAlpha(120);
		info_View = new UserInfoInfoView(this, mVfFlipper.getChildAt(0));
		yanzhi_View = new UserInfoYanZhiView(this, mVfFlipper.getChildAt(1));
		setListener();
	}

	private void setListener() {
		btn_info.setOnClickListener(this);
		btn_yanzhi.setOnClickListener(this);
	}

	private void getValue() {
		dialog = DialogUtil.createLoadingDialog(this);
		dialog.show();
		GetUserInfoTask task = new GetUserInfoTask();
		task.setmCallBack(new AbstractTaskPostCallBack<RetError>() {
			@Override
			public void taskFinish(RetError result) {
				if (dialog != null) {
					dialog.dismiss();
				}
				if (result != RetError.NONE) {
					return;
				}
				user = info.getUser();
				info_View.setValue(user.getUser_address(),
						user.getUser_gender(), user.getUser_birthday());
				txt_title.setText(user.getUser_name());
				UniversalImageLoadTool.disPlay(user.getUser_avatar(),
						img_avatar, R.drawable.default_avatar);
				yanzhi_View.setValue(info.getPictureList());
				scrollView.setVisibility(View.VISIBLE);
				layout_bottom.setVisibility(View.VISIBLE);
				bottom_line.setVisibility(View.VISIBLE);
				UniversalImageLoadTool.disPlayListener(user.getUser_avatar(),
						img_avatar_bg, R.drawable.default_avatar,
						new ImageLoadingListener() {

							@Override
							public void onLoadingStarted(String arg0, View arg1) {

							}

							@Override
							public void onLoadingFailed(String arg0, View arg1,
									FailReason arg2) {

							}

							@Override
							public void onLoadingComplete(String arg0,
									View arg1, Bitmap bitmap) {
								blur(bitmap);
							}

							@Override
							public void onLoadingCancelled(String arg0,
									View arg1) {
								// TODO Auto-generated method stub

							}
						});

			}
		});
		task.executeParallel(info);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_info:
			btn_info.setTextColor(getResources().getColor(R.color.pciture_blue));
			btn_yanzhi.setTextColor(getResources().getColor(
					R.color.pciture_text));
			mVfFlipper.setDisplayedChild(0);

			break;
		case R.id.btn_yanzhi:
			btn_yanzhi.setTextColor(getResources().getColor(
					R.color.pciture_blue));
			btn_info.setTextColor(getResources().getColor(R.color.pciture_text));
			mVfFlipper.setDisplayedChild(1);
			break;
		default:
			break;
		}
	}

	private void blur(Bitmap bkg) {
		float radius = 2;
		float scaleFactor = 8;
		int width = (int) (img_avatar_bg.getMeasuredWidth() / scaleFactor);
		int height = (int) (img_avatar_bg.getMeasuredHeight() / scaleFactor);
		if (width <= 0 && height <= 0) {
			return;
		}
		Bitmap overlay = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(overlay);
		canvas.translate(-img_avatar_bg.getLeft() / scaleFactor,
				-img_avatar_bg.getTop() / scaleFactor);
		canvas.scale(1 / scaleFactor, 1 / scaleFactor);
		Paint paint = new Paint();
		paint.setFlags(Paint.FILTER_BITMAP_FLAG);
		canvas.drawBitmap(bkg, 0, 0, paint);
		overlay = FastBlur.doBlur(overlay, (int) radius, true);
		img_avatar_bg.setImageBitmap(overlay);
		// img_avatar_bg
		// .setBackground(new BitmapDrawable(getResources(), overlay));
	}
}
