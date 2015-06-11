package com.biyanzhi.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.biyanzhi.R;
import com.biyanzhi.adapter.PictureAdapter;
import com.biyanzhi.data.Picture;
import com.biyanzhi.data.PictureList;
import com.biyanzhi.enums.RetError;
import com.biyanzhi.popwindow.SelectPicPopwindow.SelectOnclick;
import com.biyanzhi.task.GetPictureListTask;
import com.biyanzhi.utils.Constants;
import com.biyanzhi.utils.DialogUtil;
import com.biyanzhi.utils.FileUtils;
import com.biyanzhi.utils.SharedUtils;
import com.biyanzhi.utils.UniversalImageLoadTool;
import com.biyanzhi.utils.Utils;
import com.biyanzhi.view.CircularImage;
import com.biyianzhi.interfaces.AbstractTaskPostCallBack;

public class MainActivity extends BaseActivity implements SelectOnclick {
	// private StaggeredAdapter mAdapter = null;
	private ImageView img_select;
	private String cameraPath = "";
	private Dialog dialog;
	private List<Picture> mLists = new ArrayList<Picture>();
	private PictureList list = new PictureList();
	// private StaggeredGridView mGridView;
	private GridView mGridView;
	private PictureAdapter adapter;
	private TextView txt_title;
	private CircularImage img_avatar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		setValue();
		getPictureList();
	}

	private void initView() {
		img_avatar = (CircularImage) findViewById(R.id.img_avatar);
		UniversalImageLoadTool.disPlay(SharedUtils.getAPPUserAvatar(),
				img_avatar, R.drawable.default_avatar);
		txt_title = (TextView) findViewById(R.id.title_txt);
		img_select = (ImageView) findViewById(R.id.img_create);
		img_select.setOnClickListener(this);
		// mGridView = (StaggeredGridView) findViewById(R.id.grid_view);
		mGridView = (GridView) findViewById(R.id.gridView1);
		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				startActivity(new Intent(MainActivity.this,
						PictureCommentActivity.class).putExtra("picture",
						mLists.get(position)));
				Utils.leftOutRightIn(MainActivity.this);
			}
		});
		img_avatar.setOnClickListener(this);
	}

	private void setValue() {
		// mAdapter = new StaggeredAdapter(this, mLists);
		// mGridView.setAdapter(mAdapter);
		txt_title.setText("����ֵ");
		adapter = new PictureAdapter(this, mLists);
		mGridView.setAdapter(adapter);

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.img_create:
			startActivity(new Intent(this, PublicshPictureActivity.class));
			Utils.leftOutRightIn(this);
			break;
		case R.id.img_avatar:
			startActivity(new Intent(this, PersonalCenterActivity.class));
			Utils.leftOutRightIn(this);
			break;
		default:
			break;
		}
	}

	@Override
	public void menu1_select() {
		String name = FileUtils.getFileName() + ".jpg";
		cameraPath = FileUtils.getCameraPath() + File.separator + name;
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// �������ָ������������պ����Ƭ�洢��·��
		intent.putExtra(MediaStore.EXTRA_OUTPUT,
				Uri.fromFile(new File(cameraPath)));
		startActivityForResult(intent, Constants.REQUEST_CODE_GETIMAGE_BYCAMERA);
	}

	@Override
	public void menu2_select() {
		Intent intent = new Intent(Intent.ACTION_PICK, null);
		intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				"image/*");
		startActivityForResult(intent, Constants.REQUEST_CODE_GETIMAGE_BYSDCARD);

	}

	private void getPictureList() {
		dialog = DialogUtil.createLoadingDialog(this);
		dialog.show();
		GetPictureListTask task = new GetPictureListTask();
		task.setmCallBack(new AbstractTaskPostCallBack<RetError>() {
			@Override
			public void taskFinish(RetError result) {
				if (dialog != null) {
					dialog.dismiss();
				}
				mLists.addAll(list.getPictureList());
				adapter.notifyDataSetChanged();
			}
		});
		task.executeParallel(list);
	}
}

// end of class
// public class MainActivity extends BaseActivity {
// private PtrClassicFrameLayout mPtrFrame;
//
// @Override
// protected void onCreate(Bundle savedInstanceState) {
// super.onCreate(savedInstanceState);
// setContentView(R.layout.activity_main);
// initView();
// }
//
// private void initView() {
// initFefushView();
// }
//
// private void initFefushView() {
// mPtrFrame = (PtrClassicFrameLayout)
// findViewById(R.id.rotate_header_grid_view_frame);
// mPtrFrame.setLastUpdateTimeRelateObject(this);
// mPtrFrame.setPtrHandler(new PtrHandler() {
// @Override
// public void onRefreshBegin(PtrFrameLayout frame) {
// mPtrFrame.refreshComplete();
// }
//
// @Override
// public boolean checkCanDoRefresh(PtrFrameLayout frame,
// View content, View header) {
// return PtrDefaultHandler.checkContentCanBePulledDown(frame,
// content, header);
// }
// });
// // the following are default settings
// mPtrFrame.setResistance(1.7f);
// mPtrFrame.setRatioOfHeaderHeightToRefresh(1.2f);
// mPtrFrame.setDurationToClose(200);
// mPtrFrame.setDurationToCloseHeader(1000);
// // default is false
// mPtrFrame.setPullToRefresh(false);
// // default is true
// mPtrFrame.setKeepHeaderWhenRefresh(true);
// mPtrFrame.postDelayed(new Runnable() {
// @Override
// public void run() {
// // mPtrFrame.autoRefresh();
// }
// }, 100);
// }
// }
