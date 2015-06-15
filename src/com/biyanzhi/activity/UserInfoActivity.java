package com.biyanzhi.activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
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
import com.biyanzhi.data.GuanZhu;
import com.biyanzhi.data.User;
import com.biyanzhi.data.UserInfo;
import com.biyanzhi.enums.RetError;
import com.biyanzhi.showbigimage.ImagePagerActivity;
import com.biyanzhi.task.AddGuanZhuTask;
import com.biyanzhi.task.GetUserInfoTask;
import com.biyanzhi.utils.Constants;
import com.biyanzhi.utils.DialogUtil;
import com.biyanzhi.utils.ToastUtil;
import com.biyanzhi.utils.UniversalImageLoadTool;
import com.biyanzhi.utils.Utils;
import com.biyianzhi.interfaces.AbstractTaskPostCallBack;

public class UserInfoActivity extends BaseActivity {
	private RelativeLayout layout_title;
	private Button btn_info;
	private Button btn_yanzhi;
	private View line1, line2, line3, line4;
	private ViewFlipper mVfFlipper;
	private TextView txt_title;
	private ImageView img_avatar_bg;
	private int user_id;
	private UserInfo info = new UserInfo();
	private User user;
	private Dialog dialog;

	private UserInfoInfoView info_View;
	private UserInfoYanZhiView yanzhi_View;

	private ScrollView scrollView;
	private LinearLayout layout_bottom;
	private View bottom_line;
	private Button btn_add_guanzhu;
	private Button btn_send_message;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_info);
		user_id = getIntent().getIntExtra("user_id", 0);
		info.setUser_id(user_id);
		initView();
		getValue();
	}

	private void initView() {
		btn_send_message = (Button) findViewById(R.id.btn_send_message);
		btn_add_guanzhu = (Button) findViewById(R.id.btn_add_guanzhu);
		layout_bottom = (LinearLayout) findViewById(R.id.layout_bottom);
		bottom_line = (View) findViewById(R.id.line_bottom);
		scrollView = (ScrollView) findViewById(R.id.scrollView1);
		scrollView.setVisibility(View.GONE);
		img_avatar_bg = (ImageView) findViewById(R.id.img_avatar_bg);
		txt_title = (TextView) findViewById(R.id.title_txt);
		Utils.getFocus(txt_title);
		mVfFlipper = (ViewFlipper) findViewById(R.id.viewflipper);
		mVfFlipper.setDisplayedChild(1);
		layout_title = (RelativeLayout) findViewById(R.id.title);
		layout_title.setBackgroundResource(R.color.black);
		layout_title.getBackground().setAlpha(60);
		btn_info = (Button) findViewById(R.id.btn_info);
		btn_yanzhi = (Button) findViewById(R.id.btn_yanzhi);
		line1 = (View) findViewById(R.id.line1);
		line2 = (View) findViewById(R.id.line2);
		line3 = (View) findViewById(R.id.line3);
		line4 = (View) findViewById(R.id.line4);
		line2.getBackground().setAlpha(120);
		line1.getBackground().setAlpha(120);
		line3.getBackground().setAlpha(120);
		line4.getBackground().setAlpha(120);
		info_View = new UserInfoInfoView(this, mVfFlipper.getChildAt(0));
		yanzhi_View = new UserInfoYanZhiView(this, mVfFlipper.getChildAt(1));
		setListener();
	}

	private void setListener() {
		btn_info.setOnClickListener(this);
		btn_yanzhi.setOnClickListener(this);
		btn_add_guanzhu.setOnClickListener(this);
		img_avatar_bg.setOnClickListener(this);
		btn_send_message.setOnClickListener(this);
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
						user.getUser_gender(), user.getUser_birthday(),
						user.getGuanzhu_count());
				txt_title.setText(user.getUser_name());
				UniversalImageLoadTool.disPlay(user.getUser_avatar(),
						img_avatar_bg, R.drawable.default_avatar);
				img_avatar_bg.setColorFilter(Color.GRAY,
						PorterDuff.Mode.MULTIPLY);
				yanzhi_View.setValue(info.getPictureList());
				scrollView.setVisibility(View.VISIBLE);
				layout_bottom.setVisibility(View.VISIBLE);
				bottom_line.setVisibility(View.VISIBLE);

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
		case R.id.btn_add_guanzhu:
			addGuanZhu();
			break;
		case R.id.img_avatar_bg:
			if (user == null) {
				return;
			}
			List<String> imgUrl = new ArrayList<String>();
			imgUrl.add(user.getUser_avatar());
			Intent intent = new Intent(this, ImagePagerActivity.class);
			Bundle bundle = new Bundle();
			bundle.putSerializable(Constants.EXTRA_IMAGE_URLS,
					(Serializable) imgUrl);
			intent.putExtras(bundle);
			intent.putExtra(Constants.EXTRA_IMAGE_INDEX, 1);
			startActivity(intent);
			break;
		case R.id.btn_send_message:
			if (user == null) {
				return;
			}
			if (!user.isGuanZhu()) {
				ToastUtil.showToast("关注以后才能发私信");
				return;
			}
			startActivity(new Intent(this, ChatActivity.class)
					.putExtra("user_chat_id", user.getUser_chat_id())
					.putExtra("user_id", user.getUser_id())
					.putExtra("user_name", user.getUser_name())
					.putExtra("user_avatar", user.getUser_avatar()));
			Utils.leftOutRightIn(this);
			break;
		default:
			break;
		}
	}

	private void addGuanZhu() {
		dialog = DialogUtil.createLoadingDialog(this);
		dialog.show();
		GuanZhu guanzhu = new GuanZhu();
		guanzhu.setGuanzhu_user_id(user.getUser_id());
		AddGuanZhuTask task = new AddGuanZhuTask();
		task.setmCallBack(new AbstractTaskPostCallBack<RetError>() {
			@Override
			public void taskFinish(RetError result) {
				if (dialog != null) {
					dialog.dismiss();
				}
				if (result != RetError.NONE) {
					return;
				}
				ToastUtil.showToast("关注成功");
				user.setGuanZhu(true);
			}
		});
		task.executeParallel(guanzhu);
	}

	public int getUserID() {
		return user_id;

	}
}
