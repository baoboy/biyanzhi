package com.biyanzhi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.biyanzhi.R;
import com.biyanzhi.data.PK1;
import com.biyanzhi.data.PK2;
import com.biyanzhi.data.PKData;
import com.biyanzhi.enums.RetError;
import com.biyanzhi.popwindow.MenuPopwindow;
import com.biyanzhi.popwindow.MenuPopwindow.OnMenuListOnclick;
import com.biyanzhi.task.ReceivePkTask;
import com.biyanzhi.task.RefusePKTask;
import com.biyanzhi.utils.SharedUtils;
import com.biyanzhi.utils.ToastUtil;
import com.biyanzhi.utils.UniversalImageLoadTool;
import com.biyanzhi.utils.Utils;
import com.biyanzhi.view.RoundAngleImageView;
import com.biyianzhi.interfaces.AbstractTaskPostCallBack;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMConversation;
import com.easemob.chat.EMMessage;
import com.easemob.chat.TextMessageBody;
import com.easemob.exceptions.EaseMobException;

public class TiaoZhanPromptActivity extends BaseActivity {
	private TextView txt_title;
	private RoundAngleImageView img_pk1;
	private RoundAngleImageView img_pk2;
	private ImageView img_tz;
	private TextView txt;
	private Button btn_ok;
	private Button btn_cancle;
	private int width;
	private String user_chat_id = "";
	private EMConversation conversation;
	private EMMessage lastMessage;
	private TextMessageBody txtBody;
	private int pk1_user_id;
	private String pk1_user_gender;
	private String pk1_user_picture;
	private String pk2_user_picture;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tiao_zhan_prompt);
		width = Utils.getSecreenWidth(this) / 2 - 80;// 24 margin值
		initView();
		getDataByMessage();
		setValue();
	}

	private void initView() {
		txt_title = (TextView) findViewById(R.id.title_txt);
		txt_title.setText("PK挑战");
		img_pk1 = (RoundAngleImageView) findViewById(R.id.img_pk1);
		img_pk2 = (RoundAngleImageView) findViewById(R.id.img_pk2);
		img_pk2.setLayoutParams(getLayoutParams(img_pk2));
		img_pk1.setLayoutParams(getLayoutParams(img_pk1));
		img_tz = (ImageView) findViewById(R.id.img_tz);
		txt = (TextView) findViewById(R.id.txt);
		btn_cancle = (Button) findViewById(R.id.btn_cancle);
		btn_ok = (Button) findViewById(R.id.btn_ok);
		setListener();
	}

	private void setListener() {
		findViewById(R.id.back).setOnClickListener(this);
		btn_ok.setOnClickListener(this);
		btn_cancle.setOnClickListener(this);
		img_pk2.setOnClickListener(this);
		img_pk1.setOnClickListener(this);
	}

	private void setValue() {
		UniversalImageLoadTool.disPlay(pk1_user_picture, img_pk1,
				R.drawable.picture_default_head);
		UniversalImageLoadTool.disPlay(pk2_user_picture, img_pk2,
				R.drawable.picture_default_head);
		txt.setText(txtBody.getMessage());
		if ("女".equals(pk1_user_gender)) {
			img_tz.setImageResource(R.drawable.girl_tiaozhan);
			btn_cancle.setBackgroundResource(R.drawable.pk_girl_btn);
			btn_ok.setBackgroundResource(R.drawable.pk_girl_btn);
		} else {
			img_tz.setImageResource(R.drawable.boy_tianzhan);
			btn_cancle.setBackgroundResource(R.drawable.pk_boy_btn);
			btn_ok.setBackgroundResource(R.drawable.pk_boy_btn);
		}
	}

	private void getDataByMessage() {
		user_chat_id = getIntent().getStringExtra("user_chat_id");
		conversation = EMChatManager.getInstance()
				.getConversation(user_chat_id);
		lastMessage = conversation.getLastMessage();
		txtBody = (TextMessageBody) lastMessage.getBody();
		try {

			pk1_user_id = lastMessage.getIntAttribute("pk1_user_id");
			pk1_user_gender = lastMessage.getStringAttribute("pk1_user_gender");
			pk1_user_picture = lastMessage
					.getStringAttribute("pk1_user_picture");
			pk2_user_picture = lastMessage
					.getStringAttribute("pk2_user_picture");
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (EaseMobException e) {
			e.printStackTrace();
		}
	}

	private LayoutParams getLayoutParams(RoundAngleImageView img) {
		LayoutParams layoutParams = img.getLayoutParams();
		layoutParams.width = width;
		layoutParams.height = width;
		return layoutParams;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finishThisActivity();
			break;
		case R.id.img_pk1:
			showMenu(v);
			break;
		case R.id.img_pk2:
			Utils.showBigPicture(pk2_user_picture, TiaoZhanPromptActivity.this);
			break;
		case R.id.btn_ok:
			receivePK();
			break;
		case R.id.btn_cancle:
			refusePK();
			break;
		default:
			break;
		}
	}

	private void receivePK() {
		showDialog();
		ReceivePkTask task = new ReceivePkTask();
		task.setmCallBack(new AbstractTaskPostCallBack<RetError>() {
			@Override
			public void taskFinish(RetError result) {
				dismissDialog();
				if (result != RetError.NONE) {
					return;
				}
				ToastUtil.showToast("快去PK大厅查看吧");
				conversation.removeMessage(lastMessage.getMsgId());
				finishThisActivity();
			}
		});
		PKData pk = new PKData();
		PK1 pk1 = new PK1();
		pk1.setPk1_user_gender(pk1_user_gender);
		pk1.setPk1_user_id(pk1_user_id);
		pk1.setPk1_user_picture(pk1_user_picture);
		pk.setPk1(pk1);
		PK2 pk2 = new PK2();
		pk2.setPk2_user_id(SharedUtils.getIntUid());
		pk2.setPk2_user_picture(pk2_user_picture);
		pk.setPk2(pk2);
		task.executeParallel(pk);
	}

	private void refusePK() {
		showDialog();
		RefusePKTask task = new RefusePKTask();
		task.setmCallBack(new AbstractTaskPostCallBack<RetError>() {
			@Override
			public void taskFinish(RetError result) {
				dismissDialog();
				if (result != RetError.NONE) {
					return;
				}
				ToastUtil.showToast("操作成功");
				conversation.removeMessage(lastMessage.getMsgId());
				finishThisActivity();
			}
		});
		PKData pk = new PKData();
		PK1 pk1 = new PK1();
		pk1.setPk1_user_id(pk1_user_id);
		pk.setPk1(pk1);
		PK2 pk2 = new PK2();
		pk2.setPk2_user_id(SharedUtils.getIntUid());
		pk.setPk2(pk2);
		task.executeParallel(pk);
	}

	private void showMenu(View v) {
		MenuPopwindow pop = new MenuPopwindow(this, v, new String[] { "看大图",
				"查看挑战者信息" });
		pop.setCallback(new OnMenuListOnclick() {

			@Override
			public void onclick(int position) {
				switch (position) {
				case 0:
					Utils.showBigPicture(pk1_user_picture,
							TiaoZhanPromptActivity.this);
					break;
				case 1:
					startActivity(new Intent(TiaoZhanPromptActivity.this,
							UserInfoActivity.class).putExtra("user_id",
							pk1_user_id));
					Utils.leftOutRightIn(TiaoZhanPromptActivity.this);
					break;
				default:
					break;
				}
			}
		});
		pop.show();
	}
}
