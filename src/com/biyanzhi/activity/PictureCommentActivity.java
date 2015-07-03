package com.biyanzhi.activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qq.QQ.ShareParams;
import cn.sharesdk.tencent.qzone.QZone;

import com.biyanzhi.R;
import com.biyanzhi.adapter.CommentAdapter;
import com.biyanzhi.data.Comment;
import com.biyanzhi.data.Picture;
import com.biyanzhi.data.PictureScore;
import com.biyanzhi.data.User;
import com.biyanzhi.enums.RetError;
import com.biyanzhi.popwindow.CommentPopwindow;
import com.biyanzhi.popwindow.CommentPopwindow.OnCommentOnClick;
import com.biyanzhi.popwindow.SharePopwindow;
import com.biyanzhi.popwindow.SharePopwindow.SelectMenuOnclick;
import com.biyanzhi.showbigimage.ImagePagerActivity;
import com.biyanzhi.task.SendCommentTask;
import com.biyanzhi.task.SendPictureScoreTask;
import com.biyanzhi.task.UpdatePicturePublishTimeTask;
import com.biyanzhi.utils.Constants;
import com.biyanzhi.utils.DateUtils;
import com.biyanzhi.utils.DialogUtil;
import com.biyanzhi.utils.SharedUtils;
import com.biyanzhi.utils.ToastUtil;
import com.biyanzhi.utils.UniversalImageLoadTool;
import com.biyanzhi.utils.Utils;
import com.biyianzhi.interfaces.AbstractTaskPostCallBack;
import com.biyianzhi.interfaces.ConfirmDialog;
import com.biyianzhi.interfaces.OnAvatarClick;

import fynn.app.PromptDialog;

public class PictureCommentActivity extends BaseActivity implements
		OnClickListener, TextWatcher, OnItemClickListener, OnCommentOnClick,
		SelectMenuOnclick {
	private ImageView img_avatar;
	private TextView txt_user_name;
	private TextView txt_time;
	private TextView txt_context;
	private ImageView img;
	private ImageView back;
	private Button btnComment;
	private EditText edit_comment;
	private Dialog dialog;
	private List<Comment> comments = new ArrayList<Comment>();

	private boolean isReplaySomeOne = false;

	private int replaySomeOneID = 0;
	private String replaySomeOneName = "";

	private LinearLayout comment_layout;

	private RelativeLayout layout_title;
	private ListView mListView;

	private Picture picture;

	private RatingBar ratingBar;
	private TextView txt_score;
	private TextView txt_share;

	private boolean autoChange;
	private CommentAdapter adapter;
	private CommentPopwindow pop;
	private SharePopwindow share_pop;
	private View line_ratingbar;
	private int position = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comment);
		picture = (Picture) getIntent().getSerializableExtra("picture");
		position = getIntent().getIntExtra("position", -1);
		initView();
		setValue();
		viewLineVisible();
		ShareSDK.initSDK(this);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("BypassApproval", false);
		ShareSDK.setPlatformDevInfo("Wechat", map);
		ShareSDK.setPlatformDevInfo("WechatMoments", map);
	}

	private void initView() {
		line_ratingbar = (View) findViewById(R.id.line_ratingbar);
		txt_share = (TextView) findViewById(R.id.btn_share);
		layout_title = (RelativeLayout) findViewById(R.id.layout_title);
		back = (ImageView) findViewById(R.id.back);
		img = (ImageView) findViewById(R.id.img);
		txt_context = (TextView) findViewById(R.id.txt_content);
		txt_time = (TextView) findViewById(R.id.txt_time);
		txt_user_name = (TextView) findViewById(R.id.txt_user_name);
		img_avatar = (ImageView) findViewById(R.id.img_avatar);
		btnComment = (Button) findViewById(R.id.btnComment);
		edit_comment = (EditText) findViewById(R.id.edit_content);
		comment_layout = (LinearLayout) findViewById(R.id.layout_comment);
		ratingBar = (RatingBar) findViewById(R.id.ratingbar);
		if (SharedUtils.getIntUid() != picture.getPublisher_id()) {
			ratingBar.setVisibility(View.VISIBLE);
			line_ratingbar.setVisibility(View.VISIBLE);
		}
		txt_score = (TextView) findViewById(R.id.txt_score);
		LayoutParams layoutParams = img.getLayoutParams();
		layoutParams.width = Utils.getSecreenWidth(this) - 100;
		img.setLayoutParams(layoutParams);
		mListView = (ListView) findViewById(R.id.comment_listView);
		setListener();
	}

	private void setListener() {
		txt_share.setOnClickListener(this);
		img.setOnClickListener(this);
		back.setOnClickListener(this);
		btnComment.setOnClickListener(this);
		edit_comment.addTextChangedListener(this);
		Utils.getFocus(layout_title);
		ratingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
			@Override
			public void onRatingChanged(RatingBar arg0, float arg1, boolean arg2) {
				if (!autoChange) {
					autoChange = true;
					return;
				}
				// txt_score.setText((int) (arg1 * 20) + "(分)");
				showScore((int) (arg1 * 20));
			}
		});
		mListView.setOnItemClickListener(this);
		img_avatar.setOnClickListener(new OnAvatarClick(picture
				.getPublisher_id(), this));
		txt_score.setOnClickListener(this);
	}

	private void setValue() {
		comments = picture.getComments();
		adapter = new CommentAdapter(this, comments);
		mListView.setAdapter(adapter);
		String content = picture.getContent();
		if ("".equals(content)) {
			txt_context.setVisibility(View.GONE);
		} else {
			txt_context.setText(picture.getContent());
		}
		txt_time.setText(DateUtils.convertShowTIme(picture.getPublish_time()));
		UniversalImageLoadTool.disPlay(picture.getPicture_image_url(), img,
				R.drawable.default_avatar);
		if (picture.getAverage_score() != 0) {
			autoChange = false;
			ratingBar.setRating((float) picture.getAverage_score() / 20);
			// txt_score.setText(picture.getAverage_score() + "(分)");
		} else {
			autoChange = true;
		}
		txt_user_name.setText(picture.getPublisher_name());
		UniversalImageLoadTool.disPlay(picture.getPublisher_avatar(),
				img_avatar, R.drawable.default_avatar);
	}

	private void viewLineVisible() {
		if (comments.size() > 0) {
			comment_layout.setVisibility(View.VISIBLE);
		} else {
			comment_layout.setVisibility(View.GONE);
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finishThisActivity();
			break;
		case R.id.btnComment:
			String content = edit_comment.getText().toString().trim();
			if (content.length() == 0) {
				return;
			}
			sendComment(content.replace("@" + replaySomeOneName, ""));
			break;
		case R.id.img:
			List<String> imgUrl = new ArrayList<String>();
			imgUrl.add(picture.getPicture_image_url());
			Intent intent = new Intent(this, ImagePagerActivity.class);
			Bundle bundle = new Bundle();
			bundle.putSerializable(Constants.EXTRA_IMAGE_URLS,
					(Serializable) imgUrl);
			intent.putExtras(bundle);
			intent.putExtra(Constants.EXTRA_IMAGE_INDEX, 1);
			startActivity(intent);
			break;
		case R.id.btn_share:
			share_pop = new SharePopwindow(this, v);
			share_pop.setmSelectOnclick(this);
			share_pop.show();
			break;
		case R.id.txt_score:
			startActivity(new Intent(this, LookPlayScoreActivity.class)
					.putExtra("picture_id", picture.getPicture_id()));
			Utils.leftOutRightIn(this);
			break;
		default:
			break;
		}
	}

	private void sendComment(String content) {
		dialog = DialogUtil.createLoadingDialog(this, "请稍候");
		dialog.show();
		final Comment comment = new Comment();
		comment.setComment_content(content);
		if (isReplaySomeOne) {
			comment.setReply_someone_name(replaySomeOneName);
			comment.setReply_someone_id(replaySomeOneID);
		}
		comment.setPicture_id(picture.getPicture_id());
		comment.setComment_time(DateUtils.getShowTime());
		comment.setPublisher_id(SharedUtils.getIntUid());
		comment.setPublisher_avatar(SharedUtils.getAPPUserAvatar());
		comment.setPublisher_name(SharedUtils.getAPPUserName());
		SendCommentTask task = new SendCommentTask(picture.getPublisher_id());
		task.setmCallBack(new AbstractTaskPostCallBack<RetError>() {
			@Override
			public void taskFinish(RetError result) {
				if (dialog != null) {
					dialog.dismiss();
				}
				if (result != RetError.NONE) {
					return;
				}
				edit_comment.setText("");
				ToastUtil.showToast("回复成功", Toast.LENGTH_SHORT);
				comments.add(0, comment);
				adapter.notifyDataSetChanged();
				viewLineVisible();

			}
		});
		task.executeParallel(comment);
	}

	private void delReplaySomeOne() {
		isReplaySomeOne = false;
		replaySomeOneID = 0;
		replaySomeOneName = "";
	}

	@Override
	public void afterTextChanged(Editable str) {
		if (isReplaySomeOne) {
			if (replaySomeOneName.equals(str.toString().replace("@", ""))) {
				edit_comment.setText("");
				delReplaySomeOne();
			}
		}
	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) {

	}

	@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

	}

	private void showScore(final int score) {
		String str = "";
		if (score >= 80) {
			str = score + "分 你给TA打的分数很高哦,我猜TA是个美女(帅哥)";
		} else {
			str = score + "分 看来TA不是你的菜,分数不够高哦";

		}
		PromptDialog.Builder dialog = DialogUtil.confirmDialog(this, str, "确定",
				null, new ConfirmDialog() {

					@Override
					public void onOKClick() {
						sendScore(score);
					}

					public void onCancleClick() {

					}
				});
		dialog.show();
	}

	private void sendScore(final int score) {
		SendPictureScoreTask task = new SendPictureScoreTask();
		task.setmCallBack(new AbstractTaskPostCallBack<RetError>() {
			@Override
			public void taskFinish(RetError result) {
				if (result != RetError.NONE) {
					return;
				}
				sendBroadcast(new Intent(Constants.PLAY_SCORE).putExtra(
						"position", position).putExtra("score", score));
				// updatePicturePublishTime();
			}
		});
		PictureScore picscore = new PictureScore();
		picscore.setPicture_id(picture.getPicture_id());
		picscore.setPicture_score(score);
		User user = new User();
		user.setUser_id(picture.getPublisher_id());
		picscore.setUser(user);
		task.executeParallel(picscore);
	}

	private void updatePicturePublishTime() {
		UpdatePicturePublishTimeTask task = new UpdatePicturePublishTimeTask();
		task.setmCallBack(new AbstractTaskPostCallBack<RetError>() {
			@Override
			public void taskFinish(RetError result) {
			}
		});
		task.executeParallel(picture);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int position,
			long arg3) {
		pop = new CommentPopwindow(this, view, position,
				picture.getPublisher_id());
		pop.setmCallBack(this);
		pop.show();
	}

	@Override
	public void onClick(int position, int id) {
		switch (id) {
		case R.id.txt_reply:
			reply(position);

			break;
		case R.id.txt_del:
			break;
		default:
			break;
		}
	}

	private void reply(int position) {
		Comment comment = comments.get(position);
		edit_comment.setText(Html.fromHtml("<font color=#F06617>@"
				+ comment.getPublisher_name() + "</font> "));
		edit_comment.setSelection(edit_comment.getText().toString().length());
		Utils.getFocus(edit_comment);
		isReplaySomeOne = true;
		replaySomeOneID = comment.getPublisher_id();
		replaySomeOneName = comment.getPublisher_name();
	}

	@Override
	public void onClickPosition(int position) {
		switch (position) {
		case 0:
			shareQQ();
			break;
		case 1:
			shareQQZone();
			break;
		case 2:
			shareWechat();
			break;
		case 3:
			shareWechatMoments();
		default:
			break;
		}
	}

	private void shareQQ() {
		Platform plat = ShareSDK.getPlatform(QQ.NAME);
		ShareParams sp = new ShareParams();
		sp.setTitle("比颜值");
		sp.setTitleUrl("http://123.56.46.254/biyanzhi/biyanzhi.html"); // 标题的超链接
		sp.setText("总共有 " + picture.getScore_number() + " 个人给我评分 ,平均分 "
				+ picture.getAverage_score() + " 分,快来测测你的颜值能得少分吧");
		sp.setSite("比颜值");
		sp.setSiteUrl("http://123.56.46.254/biyanzhi/biyanzhi.html");
		sp.setImageUrl(picture.getPicture_image_url());
		sp.setShareType(Platform.SHARE_WEBPAGE);
		plat.share(sp);
	}

	private void shareQQZone() {
		Platform plat = ShareSDK.getPlatform(QZone.NAME);
		ShareParams sp = new ShareParams();
		sp.setTitle("比颜值");
		sp.setTitleUrl("http://123.56.46.254/biyanzhi/biyanzhi.html"); // 标题的超链接
		sp.setText("总共有 " + picture.getScore_number() + " 个人给我评分 ,平均分 "
				+ picture.getAverage_score() + " 分,快来测测你的颜值能得少分吧");
		sp.setImageUrl(picture.getPicture_image_url());
		sp.setSite("比颜值");
		sp.setSiteUrl("http://123.56.46.254/biyanzhi/biyanzhi.html");
		sp.setShareType(Platform.SHARE_WEBPAGE);
		plat.share(sp);
	}

	private void shareWechat() {
		Platform plat = ShareSDK.getPlatform("Wechat");
		ShareParams sp = new ShareParams();
		sp.setTitle("比颜值");
		sp.setText("总共有 " + picture.getScore_number() + " 个人给我评分 ,平均分 "
				+ picture.getAverage_score() + " 分,快来测测你的颜值能得少分吧");
		sp.setShareType(Platform.SHARE_WEBPAGE);
		sp.setUrl("http://123.56.46.254/biyanzhi/biyanzhi.html");
		sp.setImageUrl(picture.getPicture_image_url());
		plat.share(sp);

	}

	private void shareWechatMoments() {
		Platform plat = ShareSDK.getPlatform("WechatMoments");
		ShareParams sp = new ShareParams();
		sp.setTitle("总共有 " + picture.getScore_number() + " 个人给我评分 ,平均分 "
				+ picture.getAverage_score() + " 分,快来测测你的颜值能得少分吧");
		sp.setText("总共有 " + picture.getScore_number() + " 个人给我评分 ,平均分 "
				+ picture.getAverage_score() + " 分,快来测测你的颜值能得少分吧");
		sp.setShareType(Platform.SHARE_WEBPAGE);
		sp.setUrl("http://123.56.46.254/biyanzhi/biyanzhi.html");
		sp.setImageUrl(picture.getPicture_image_url());
		plat.share(sp);

	}

}
