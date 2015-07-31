package com.biyanzhi.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import cn.sharesdk.framework.ShareSDK;

import com.biyanzhi.R;
import com.biyanzhi.data.Share;
import com.biyanzhi.popwindow.SharePopwindow;
import com.biyanzhi.popwindow.SharePopwindow.SelectMenuOnclick;
import com.biyanzhi.utils.ShareUtil;
import com.biyanzhi.utils.UniversalImageLoadTool;
import com.biyanzhi.utils.Utils;
import com.biyanzhi.view.RoundAngleImageView;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMConversation;
import com.easemob.chat.EMMessage;
import com.easemob.chat.TextMessageBody;

public class PKWinActivity extends BaseActivity {
	private TextView txt_title;
	private RoundAngleImageView img_pk1;
	private RoundAngleImageView img_pk2;
	private ImageView img_vs;
	private TextView txt;
	private Button btn_share;
	private int width;
	private String user_chat_id = "";
	private EMConversation conversation;
	private EMMessage lastMessage;
	private TextMessageBody txtBody;
	private int pk1_user_id;
	private String pk1_user_gender;
	private String pk1_user_picture;
	private String pk2_user_picture;
	private ImageView img_pk1_win;
	private ImageView img_pk2_win;
	private int pk_winer_user_id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pkwin);
		width = Utils.getSecreenWidth(this) / 2 - 80;// 24 margin值
		initView();
		getDataByMessage();
		setValue();
		ShareSDK.initSDK(this);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("BypassApproval", false);
		ShareSDK.setPlatformDevInfo("Wechat", map);
		ShareSDK.setPlatformDevInfo("WechatMoments", map);
	}

	private void initView() {
		img_pk1_win = (ImageView) findViewById(R.id.img_pk1_win);
		img_pk2_win = (ImageView) findViewById(R.id.img_pk2_win);
		txt_title = (TextView) findViewById(R.id.title_txt);
		txt_title.setText("PK成功");
		img_pk1 = (RoundAngleImageView) findViewById(R.id.img_pk1);
		img_pk2 = (RoundAngleImageView) findViewById(R.id.img_pk2);
		img_pk2.setLayoutParams(getLayoutParams(img_pk2));
		img_pk1.setLayoutParams(getLayoutParams(img_pk1));
		img_vs = (ImageView) findViewById(R.id.img_tz);
		txt = (TextView) findViewById(R.id.txt);
		btn_share = (Button) findViewById(R.id.btn_share);
		setListener();
	}

	private void setListener() {
		findViewById(R.id.back).setOnClickListener(this);
		btn_share.setOnClickListener(this);
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
			img_vs.setImageResource(R.drawable.girl_vs);
			btn_share.setBackgroundResource(R.drawable.pk_girl_btn);
		} else {
			img_vs.setImageResource(R.drawable.boy_vs);
			btn_share.setBackgroundResource(R.drawable.pk_boy_btn);
		}
		if (pk_winer_user_id == pk1_user_id) {
			img_pk1_win.setVisibility(View.VISIBLE);
		} else {
			img_pk2_win.setVisibility(View.VISIBLE);
		}
	}

	private void getDataByMessage() {
		user_chat_id = getIntent().getStringExtra("user_chat_id");
		conversation = EMChatManager.getInstance()
				.getConversation(user_chat_id);
		lastMessage = conversation.getLastMessage();
		txtBody = (TextMessageBody) lastMessage.getBody();
		try {
			String pk = lastMessage.getStringAttribute("pk");
			JSONObject json = new JSONObject(pk);
			pk1_user_id = json.getInt("pk1_user_id");
			pk1_user_gender = json.getString("pk1_user_gender");
			pk1_user_picture = json.getString("pk1_user_picture");
			pk2_user_picture = json.getString("pk2_user_picture");
			pk_winer_user_id = json.getInt("pk_winer_user_id");
		} catch (Exception e) {
			e.printStackTrace();
		}
		conversation.removeMessage(lastMessage.getMsgId());
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
			Utils.showBigPicture(pk1_user_picture, PKWinActivity.this);
			break;
		case R.id.img_pk2:
			Utils.showBigPicture(pk2_user_picture, PKWinActivity.this);
			break;
		case R.id.btn_share:
			if (pk_winer_user_id == pk1_user_id) {
				xuanYaoShare(v, pk1_user_picture);
			} else {
				xuanYaoShare(v, pk2_user_picture);
			}
			break;
		default:
			break;
		}
	}

	private void xuanYaoShare(View v, final String img_path) {
		List<Share> lists = new ArrayList<Share>();
		lists.add(new Share("微信朋友圈", R.drawable.share_wx_pyq));
		lists.add(new Share("微信好友", R.drawable.share_wx_py));
		lists.add(new Share("QQ好友", R.drawable.share_qq));
		lists.add(new Share("QQ空间", R.drawable.share_qzone));
		lists.add(new Share("新浪微博", R.drawable.share_sina));
		SharePopwindow share_pop = new SharePopwindow(this, v, lists);
		share_pop.setmSelectOnclick(new SelectMenuOnclick() {

			@Override
			public void onClickPosition(int position) {
				switch (position) {
				case 0:
					ShareUtil
							.shareWechatMoments(
									"我在  比颜值  APP的PK大赛中成功PK掉了对手,获取了胜利。你们也快来PK吧",
									"我在  比颜值  APP的PK大赛中成功PK掉了对手,获取了胜利。你们也快来PK吧http://123.56.46.254/biyanzhi/biyanzhi.html",
									img_path);
					break;
				case 1:
					ShareUtil
							.shareWechat(
									"我在  比颜值  APP的PK大赛中成功PK掉了对手,获取了胜利。你们也快来PK吧http://123.56.46.254/biyanzhi/biyanzhi.html",
									img_path);
					break;
				case 2:
					ShareUtil
							.shareQQ(
									"我在  比颜值  APP的PK大赛中成功PK掉了对手,获取了胜利。你们也快来PK吧http://123.56.46.254/biyanzhi/biyanzhi.html",
									img_path);
					break;
				case 3:
					ShareUtil
							.shareQQZone(
									"我在  比颜值  APP的PK大赛中成功PK掉了对手,获取了胜利。你们也快来PK吧http://123.56.46.254/biyanzhi/biyanzhi.html",
									img_path);
					break;
				case 4:
					ShareUtil
							.shareSinaWeiBo(
									"我在  比颜值  APP的PK大赛中成功PK掉了对手,获取了胜利。你们也快来PK吧",
									"我在  比颜值  APP的PK大赛中成功PK掉了对手,获取了胜利。你们也快来PK吧http://123.56.46.254/biyanzhi/biyanzhi.html",
									img_path);
					break;
				default:
					break;
				}
			}
		});
		share_pop.show();
	}
}
