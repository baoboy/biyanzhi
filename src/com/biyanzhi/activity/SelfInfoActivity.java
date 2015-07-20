package com.biyanzhi.activity;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.biyanzhi.R;
import com.biyanzhi.data.User;
import com.biyanzhi.data.UserInfo;
import com.biyanzhi.enums.RetError;
import com.biyanzhi.popwindow.SelectPicPopwindow;
import com.biyanzhi.popwindow.SelectPicPopwindow.SelectOnclick;
import com.biyanzhi.task.GetUserInfoTask;
import com.biyanzhi.task.UpLoadUserAvatarTask;
import com.biyanzhi.utils.BitmapUtils;
import com.biyanzhi.utils.Constants;
import com.biyanzhi.utils.DialogUtil;
import com.biyanzhi.utils.FileUtils;
import com.biyanzhi.utils.PhotoUtils;
import com.biyanzhi.utils.SharedUtils;
import com.biyanzhi.utils.ToastUtil;
import com.biyanzhi.utils.UniversalImageLoadTool;
import com.biyanzhi.utils.Utils;
import com.biyianzhi.interfaces.AbstractTaskPostCallBack;

public class SelfInfoActivity extends BaseActivity implements SelectOnclick {
	private RelativeLayout layout_title;
	private Button btn_info;
	private Button btn_yanzhi;
	private ViewFlipper mVfFlipper;
	private TextView txt_title;
	private ImageView img_avatar_bg;
	private int user_id;

	private UserInfo info = new UserInfo();

	private UserInfoYanZhiView yanzhi_View;
	private UserSelfInfoInfoView info_view;

	private String mTakePicturePath = "";
	private String imgPath = "";

	private SelectPicPopwindow pop;

	private Dialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_self_info);
		user_id = getIntent().getIntExtra("user_id", 0);
		info.setUser_id(user_id);
		initView();
		getValue();
		registerBoradcastReceiver();
	}

	private void initView() {
		img_avatar_bg = (ImageView) findViewById(R.id.img_avatar_bg);
		// view = (DampView) findViewById(R.id.scrollView1);
		// view.setImageView(img_avatar_bg);
		txt_title = (TextView) findViewById(R.id.title_txt);
		Utils.getFocus(txt_title);
		mVfFlipper = (ViewFlipper) findViewById(R.id.viewflipper);
		mVfFlipper.setDisplayedChild(0);
		layout_title = (RelativeLayout) findViewById(R.id.title);
		layout_title.setBackgroundResource(R.color.black);
		layout_title.getBackground().setAlpha(60);
		btn_info = (Button) findViewById(R.id.btn_info);
		btn_yanzhi = (Button) findViewById(R.id.btn_yanzhi);
		yanzhi_View = new UserInfoYanZhiView(this, mVfFlipper.getChildAt(1));
		info_view = new UserSelfInfoInfoView(this, mVfFlipper.getChildAt(0));
		txt_title.setText(SharedUtils.getAPPUserName());
		UniversalImageLoadTool.disPlay(SharedUtils.getAPPUserAvatar(),
				img_avatar_bg, R.drawable.default_avatar);
		img_avatar_bg.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
		setListener();

	}

	private void setListener() {
		btn_info.setOnClickListener(this);
		btn_yanzhi.setOnClickListener(this);
		img_avatar_bg.setOnClickListener(this);
		findViewById(R.id.back).setOnClickListener(this);

	}

	private void getValue() {
		GetUserInfoTask task = new GetUserInfoTask();
		task.setmCallBack(new AbstractTaskPostCallBack<RetError>() {
			@Override
			public void taskFinish(RetError result) {
				if (result != RetError.NONE) {
					return;
				}
				info_view.setGuanZhuCount(info.getUser().getGuanzhu_count());
				yanzhi_View.setValue(info.getPictureList());
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
		case R.id.img_avatar_bg:
			pop = new SelectPicPopwindow(this, v, "拍照", "从相册选择");
			pop.setmSelectOnclick(this);
			pop.show();
			break;
		case R.id.back:
			finishThisActivity();
			break;
		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (data == null) {
			return;
		}
		switch (requestCode) {
		case PhotoUtils.INTENT_REQUEST_CODE_ALBUM:

			if (resultCode == RESULT_OK) {
				if (data.getData() == null) {
					return;
				}
				if (!FileUtils.isSdcardExist()) {
					ToastUtil.showToast("SD卡不可用,请检查", Toast.LENGTH_SHORT);
					return;
				}
				Uri uri = data.getData();
				String[] proj = { MediaStore.Images.Media.DATA };
				Cursor cursor = managedQuery(uri, proj, null, null, null);
				if (cursor != null) {
					int column_index = cursor
							.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
					if (cursor.getCount() > 0 && cursor.moveToFirst()) {
						String path = cursor.getString(column_index);
						// Bitmap bitmap = BitmapFactory.decodeFile(path);
						Bitmap bitmap = BitmapUtils.FitSizeImg(path);
						if (PhotoUtils.bitmapIsLarge(bitmap)) {
							PhotoUtils.cropPhoto(this, this, path);
						} else {
							setAvatar(bitmap, path);
						}
					}
				}
			}
			break;

		case PhotoUtils.INTENT_REQUEST_CODE_CAMERA:

			if (resultCode == RESULT_OK) {

				String path = mTakePicturePath;
				// Bitmap bitmap = BitmapFactory.decodeFile(path);
				Bitmap bitmap = BitmapUtils.FitSizeImg(path);
				if (PhotoUtils.bitmapIsLarge(bitmap)) {
					PhotoUtils.cropPhoto(this, this, path);
				} else {
					setAvatar(bitmap, path);

				}
			}
			break;

		case PhotoUtils.INTENT_REQUEST_CODE_CROP:
			if (resultCode == RESULT_OK) {
				String path = data.getStringExtra("path");
				if (path != null) {
					Bitmap bitmap = BitmapFactory.decodeFile(path);
					if (bitmap != null) {
						setAvatar(bitmap, path);
					}
				}
			}
			break;
		default:
			break;
		}
	}

	private void setAvatar(Bitmap bitmap, String path) {
		if (bitmap != null) {
			img_avatar_bg.setImageBitmap(bitmap);
			imgPath = path;
			upLoadAvatar();
		}
	}

	private User user;

	private void upLoadAvatar() {
		dialog = DialogUtil.createLoadingDialog(this, "请稍候");
		dialog.show();
		user = new User();
		user.setUser_avatar(imgPath);
		UpLoadUserAvatarTask task = new UpLoadUserAvatarTask();
		task.setmCallBack(new AbstractTaskPostCallBack<RetError>() {
			@Override
			public void taskFinish(RetError result) {
				if (dialog != null) {
					dialog.dismiss();
				}
				if (result != RetError.NONE) {
					return;
				}
				ToastUtil.showToast("上传成功", Toast.LENGTH_SHORT);
				SharedUtils.setAPPUserAvatar(user.getUser_avatar());
				sendBroadcast(new Intent(Constants.UPDATE_USER_AVATAR));
			}
		});
		task.executeParallel(user);
	}

	@Override
	public void menu1_select() {
		mTakePicturePath = PhotoUtils.takePicture(this);

	}

	@Override
	public void menu2_select() {
		PhotoUtils.selectPhoto(this);

	}

	/**
	 * 注册该广播
	 */
	public void registerBoradcastReceiver() {
		IntentFilter myIntentFilter = new IntentFilter();
		myIntentFilter.addAction(Constants.UPDATE_USER_NAME);
		myIntentFilter.addAction(Constants.DEL_PICTURE);

		// 注册广播
		registerReceiver(mBroadcastReceiver, myIntentFilter);
	}

	/**
	 * 定义广播
	 */
	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(Constants.UPDATE_USER_NAME)) {
				info_view.setNickName();

			} else if (action.equals(Constants.DEL_PICTURE)) {
				int picture_id = intent.getIntExtra("picture_id", -1);
				yanzhi_View.delPicture(picture_id);
			}
		}
	};

	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mBroadcastReceiver);
	};

}
