package com.biyanzhi.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;

import com.biyanzhi.R;
import com.biyanzhi.activity.LoginActivity;
import com.biyanzhi.activity.SelectPK2PictureActivity;
import com.biyanzhi.activity.UserInfoActivity;
import com.biyanzhi.data.PK1;
import com.biyanzhi.data.PK2;
import com.biyanzhi.data.PKData;
import com.biyanzhi.data.Share;
import com.biyanzhi.enums.RetError;
import com.biyanzhi.popwindow.MenuPopwindow;
import com.biyanzhi.popwindow.MenuPopwindow.OnMenuListOnclick;
import com.biyanzhi.popwindow.SharePopwindow;
import com.biyanzhi.popwindow.SharePopwindow.SelectMenuOnclick;
import com.biyanzhi.task.UpDatePK1TicketCountTask;
import com.biyanzhi.task.UpDatePK2TicketCountTask;
import com.biyanzhi.utils.Constants;
import com.biyanzhi.utils.DialogUtil;
import com.biyanzhi.utils.ShareUtil;
import com.biyanzhi.utils.SharedUtils;
import com.biyanzhi.utils.UniversalImageLoadTool;
import com.biyanzhi.utils.Utils;
import com.biyanzhi.view.RoundAngleImageView;
import com.biyianzhi.interfaces.AbstractTaskPostCallBack;
import com.biyianzhi.interfaces.ConfirmDialog;

import fynn.app.PromptDialog;

public class PKAdapter extends BaseAdapter {
	private Context mContext;
	private int width;
	private List<PKData> mlists;
	private Dialog dialog;

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
			holder.img_pk1_win = (ImageView) convertView
					.findViewById(R.id.img_pk1_win);
			holder.img_pk2_win = (ImageView) convertView
					.findViewById(R.id.img_pk2_win);
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
		PK1 pk1 = mlists.get(position).getPk1();
		PK2 pk2 = mlists.get(position).getPk2();
		String pk1_picture = pk1.getPk1_user_picture();
		String pk2_picture = pk2.getPk2_user_picture();
		UniversalImageLoadTool.disPlay(pk1_picture, holder.img_pk1,
				R.drawable.picture_default_head);
		int pk1_ticket_count = pk1.getPk1_ticket_count();
		int pk2_ticket_count = pk2.getPk2_ticket_count();
		int pk1_user_id = pk1.getPk1_user_id();
		int pk2_user_id = pk2.getPk2_user_id();
		if ("女".equals(mlists.get(position).getPk1().getPk1_user_gender())) {
			if (pk1_ticket_count == 0) {
				holder.btn_pk1.setText("她美");
			} else {
				holder.btn_pk1.setText("(+" + pk1_ticket_count + ")  她美");

			}
			if (pk2_ticket_count == 0) {
				holder.btn_pk2.setText("她美");
			} else {
				holder.btn_pk2.setText("(+" + pk2_ticket_count + ")  她美");

			}

			int sdk = android.os.Build.VERSION.SDK_INT;
			if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
				holder.btn_pk1.setBackgroundDrawable(mContext.getResources()
						.getDrawable(R.drawable.pk_girl_btn));
				holder.btn_pk2.setBackgroundDrawable(mContext.getResources()
						.getDrawable(R.drawable.pk_girl_btn));
			} else {
				holder.btn_pk1.setBackground(mContext.getResources()
						.getDrawable(R.drawable.pk_girl_btn));
				holder.btn_pk2.setBackground(mContext.getResources()
						.getDrawable(R.drawable.pk_girl_btn));
			}
			holder.img_vs.setImageResource(R.drawable.girl_vs);
			if (mlists.get(position).getPk2().getPk2_user_id() == 0) {
				UniversalImageLoadTool.disPlay(pk2_picture, holder.img_pk2,
						R.drawable.girl_pk_default1);
			} else {
				UniversalImageLoadTool.disPlay(pk2_picture, holder.img_pk2,
						R.drawable.picture_default_head);
			}
		} else {
			if (pk1_ticket_count == 0) {
				holder.btn_pk1.setText("他帅");
			} else {
				holder.btn_pk1.setText("(+" + pk1_ticket_count + ")  他帅");

			}
			if (pk2_ticket_count == 0) {
				holder.btn_pk2.setText("他帅");
			} else {
				holder.btn_pk2.setText("(+" + pk2_ticket_count + ")  他帅");
			}
			int sdk = android.os.Build.VERSION.SDK_INT;
			if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
				holder.btn_pk1.setBackgroundDrawable(mContext.getResources()
						.getDrawable(R.drawable.pk_boy_btn));
				holder.btn_pk2.setBackgroundDrawable(mContext.getResources()
						.getDrawable(R.drawable.pk_boy_btn));
			} else {
				holder.btn_pk1.setBackground(mContext.getResources()
						.getDrawable(R.drawable.pk_boy_btn));
				holder.btn_pk2.setBackground(mContext.getResources()
						.getDrawable(R.drawable.pk_boy_btn));
			}
			holder.img_vs.setImageResource(R.drawable.boy_vs);
			if (pk2.getPk2_user_id() == 0) {
				UniversalImageLoadTool.disPlay(pk2_picture, holder.img_pk2,
						R.drawable.boy_pk_default1);
			} else {
				UniversalImageLoadTool.disPlay(pk2_picture, holder.img_pk2,
						R.drawable.picture_default_head);
			}
		}
		if (pk2.getPk2_user_id() == 0) {
			holder.btn_pk1.setVisibility(View.GONE);
			holder.btn_pk2.setVisibility(View.GONE);
		} else {
			holder.btn_pk1.setVisibility(View.VISIBLE);
			holder.btn_pk2.setVisibility(View.VISIBLE);
		}
		if (SharedUtils.getIntUid() == pk1_user_id) {
			if (pk1_ticket_count == 0) {
				holder.btn_pk1.setText("拉一下票");
			} else {
				holder.btn_pk1.setText("(+" + pk1_ticket_count + ")" + "拉一下票");
			}

		}
		if (SharedUtils.getIntUid() == pk2_user_id) {
			if (pk2_ticket_count == 0) {
				holder.btn_pk2.setText("拉一下票");
			} else {
				holder.btn_pk2.setText("(+" + pk2_ticket_count + ")" + "拉一下票");
			}

		}

		if (mlists.get(position).getPk_state() == 1) {

			if (mlists.get(position).getPk_winer_user_id() == pk1_user_id) {
				if (pk1_user_id == SharedUtils.getIntUid()) {
					holder.btn_pk1.setText("炫耀一下");
					holder.btn_pk2.setText("失败");
				} else {
					holder.btn_pk1.setText("PK成功");
					if (pk2_user_id == SharedUtils.getIntUid()) {
						holder.btn_pk2.setText("别灰心");
					} else {
						holder.btn_pk2.setText("失败");
					}
				}
				holder.img_pk1_win.setVisibility(View.VISIBLE);
				holder.img_pk2_win.setVisibility(View.GONE);

			} else {
				if (pk2_user_id == SharedUtils.getIntUid()) {
					holder.btn_pk2.setText("炫耀一下");
					holder.btn_pk1.setText("失败");
				} else {
					holder.btn_pk2.setText("PK成功");
					if (pk1_user_id == SharedUtils.getIntUid()) {
						holder.btn_pk1.setText("别灰心");
					} else {
						holder.btn_pk1.setText("失败");
					}
				}
				holder.img_pk2_win.setVisibility(View.VISIBLE);
				holder.img_pk1_win.setVisibility(View.GONE);
			}
		} else {
			holder.img_pk2_win.setVisibility(View.GONE);
			holder.img_pk1_win.setVisibility(View.GONE);
		}
		holder.img_pk1.setOnClickListener(new OnClick(position));
		holder.img_pk2.setOnClickListener(new OnClick(position));
		holder.btn_pk1.setOnClickListener(new OnClick(position));
		holder.btn_pk2.setOnClickListener(new OnClick(position));
		return convertView;
	}

	class ViewHolder {
		RoundAngleImageView img_pk1;
		RoundAngleImageView img_pk2;
		Button btn_pk1;
		Button btn_pk2;
		ImageView img_vs;
		ImageView img_pk1_win;
		ImageView img_pk2_win;

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
				if (SharedUtils.getIntUid() == 0) {
					mContext.startActivity(new Intent(mContext,
							LoginActivity.class));
					Utils.leftOutRightIn(mContext);
					return;
				}
				if (SharedUtils.getIntUid() == mlists.get(position).getPk1()
						.getPk1_user_id()) {
					Utils.showBigPicture(mlists.get(position).getPk1()
							.getPk1_user_picture(), mContext);
				} else {
					showMenu(v, mlists.get(position).getPk1().getPk1_user_id(),
							mlists.get(position).getPk1().getPk1_user_picture());
				}

				break;
			case R.id.img_pk2:
				if (SharedUtils.getIntUid() == 0) {
					mContext.startActivity(new Intent(mContext,
							LoginActivity.class));
					Utils.leftOutRightIn(mContext);
					return;
				}
				if (mlists.get(position).getPk_state() == 1) {
					if (SharedUtils.getIntUid() == mlists.get(position)
							.getPk2().getPk2_user_id()) {
						Utils.showBigPicture(mlists.get(position).getPk2()
								.getPk2_user_picture(), mContext);
					} else {
						showMenu(v, mlists.get(position).getPk2()
								.getPk2_user_id(), mlists.get(position)
								.getPk2().getPk2_user_picture());
					}

					return;
				}
				if (SharedUtils.getIntUid() == mlists.get(position).getPk1()
						.getPk1_user_id()
						&& mlists.get(position).getPk2().getPk2_user_id() == 0) {
					pkPrompt();
					return;
				}
				if (mlists.get(position).getPk2().getPk2_user_id() == 0) {
					if (!mlists.get(position).getPk1().getPk1_user_gender()
							.equals(SharedUtils.getAPPUserGender())) {
						genderPrompt();
						return;
					}
					mContext.startActivity(new Intent(mContext,
							SelectPK2PictureActivity.class).putExtra("pk_id",
							mlists.get(position).getPk_id()).putExtra(
							"pk1_user_id",
							mlists.get(position).getPk1().getPk1_user_id()));
					Utils.leftOutRightIn(mContext);
				} else {
					if (SharedUtils.getIntUid() == mlists.get(position)
							.getPk2().getPk2_user_id()) {
						Utils.showBigPicture(mlists.get(position).getPk2()
								.getPk2_user_picture(), mContext);
					} else {
						showMenu(v, mlists.get(position).getPk2()
								.getPk2_user_id(), mlists.get(position)
								.getPk2().getPk2_user_picture());
					}

				}
				break;
			case R.id.btn_pk1:
				if (SharedUtils.getIntUid() == 0) {
					mContext.startActivity(new Intent(mContext,
							LoginActivity.class));
					Utils.leftOutRightIn(mContext);
					return;
				}
				if (mlists.get(position).getPk_state() == 1) {
					if (SharedUtils.getIntUid() == mlists.get(position)
							.getPk1().getPk1_user_id()
							&& mlists.get(position).getPk_winer_user_id() == mlists
									.get(position).getPk1().getPk1_user_id()) {
						xuanYaoShare(v, mlists.get(position).getPk1()
								.getPk1_user_picture());
					}
					return;
				}
				if (SharedUtils.getIntUid() == mlists.get(position).getPk1()
						.getPk1_user_id()) {
					laPiaoShare(v, mlists.get(position).getPk1()
							.getPk1_user_picture());
					return;
				}
				if (mlists.get(position).isIs_voted()) {
					votePrompt();
					return;
				}

				upDatePK1Ticket(position);
				break;
			case R.id.btn_pk2:
				if (SharedUtils.getIntUid() == 0) {
					mContext.startActivity(new Intent(mContext,
							LoginActivity.class));
					Utils.leftOutRightIn(mContext);
					return;
				}
				if (mlists.get(position).getPk_state() == 1) {
					if (SharedUtils.getIntUid() == mlists.get(position)
							.getPk2().getPk2_user_id()
							&& mlists.get(position).getPk_winer_user_id() == mlists
									.get(position).getPk2().getPk2_user_id()) {
						xuanYaoShare(v, mlists.get(position).getPk2()
								.getPk2_user_picture());
					}
					return;
				}
				if (SharedUtils.getIntUid() == mlists.get(position).getPk2()
						.getPk2_user_id()) {
					laPiaoShare(v, mlists.get(position).getPk2()
							.getPk2_user_picture());
					return;
				}
				if (mlists.get(position).isIs_voted()) {
					votePrompt();
					return;
				}
				upDatePK2Ticket(position);
				break;
			default:
				break;
			}
		}
	}

	private void laPiaoShare(View v, final String img_path) {
		List<Share> lists = new ArrayList<Share>();
		lists.add(new Share("微信朋友圈", R.drawable.share_wx_pyq));
		lists.add(new Share("微信好友", R.drawable.share_wx_py));
		lists.add(new Share("QQ好友", R.drawable.share_qq));
		lists.add(new Share("QQ空间", R.drawable.share_qzone));
		lists.add(new Share("新浪微博", R.drawable.share_sina));
		SharePopwindow share_pop = new SharePopwindow(mContext, v, lists);
		share_pop.setmSelectOnclick(new SelectMenuOnclick() {

			@Override
			public void onClickPosition(int position) {
				switch (position) {
				case 0:
					ShareUtil
							.shareWechatMoments(
									"我正在  比颜值  中和对手PK,小伙伴快来给我投票支持我吧",
									"我正在  比颜值  中和对手PK,小伙伴快来给我投票支持我吧http://123.56.46.254/biyanzhi/biyanzhi.html",
									img_path);
					break;
				case 1:
					ShareUtil
							.shareWechat(
									"我正在  比颜值  中和对手PK,小伙伴快来给我投票支持我吧http://123.56.46.254/biyanzhi/biyanzhi.html",
									img_path);
					break;
				case 2:
					ShareUtil
							.shareQQ(
									"我正在  比颜值  中和对手PK,小伙伴快来给我投票支持我吧http://123.56.46.254/biyanzhi/biyanzhi.html",
									img_path);
					break;
				case 3:
					ShareUtil
							.shareQQZone(
									"我正在  比颜值  中和对手PK,小伙伴快来给我投票支持我吧http://123.56.46.254/biyanzhi/biyanzhi.html",
									img_path);
					break;
				case 4:
					ShareUtil
							.shareSinaWeiBo(
									"我正在  比颜值  中和对手PK,小伙伴快来给我投票支持我吧",
									"我正在  比颜值  中和对手PK,小伙伴快来给我投票支持我吧http://123.56.46.254/biyanzhi/biyanzhi.html",
									img_path);
					break;
				default:
					break;
				}
			}
		});
		share_pop.show();
	}

	private void xuanYaoShare(View v, final String img_path) {
		List<Share> lists = new ArrayList<Share>();
		lists.add(new Share("微信朋友圈", R.drawable.share_wx_pyq));
		lists.add(new Share("微信好友", R.drawable.share_wx_py));
		lists.add(new Share("QQ好友", R.drawable.share_qq));
		lists.add(new Share("QQ空间", R.drawable.share_qzone));
		lists.add(new Share("新浪微博", R.drawable.share_sina));
		SharePopwindow share_pop = new SharePopwindow(mContext, v, lists);
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

	private void pkPrompt() {
		PromptDialog.Builder dialog = DialogUtil.confirmDialog(mContext,
				"不能与自己PK哦", "确定", null, new ConfirmDialog() {

					@Override
					public void onOKClick() {

					}

					public void onCancleClick() {

					}
				});
		dialog.show();
	}

	private void genderPrompt() {
		PromptDialog.Builder dialog = DialogUtil.confirmDialog(mContext,
				"性别一样才能PK哦", "确定", null, new ConfirmDialog() {

					@Override
					public void onOKClick() {

					}

					public void onCancleClick() {

					}
				});
		dialog.show();
	}

	private void winPrompt() {
		PromptDialog.Builder dialog = DialogUtil.confirmDialog(mContext,
				"PK已经结束了哦", "确定", null, new ConfirmDialog() {

					@Override
					public void onOKClick() {

					}

					public void onCancleClick() {

					}
				});
		dialog.show();
	}

	private void votePrompt() {
		PromptDialog.Builder dialog = DialogUtil.confirmDialog(mContext,
				"该组PK你已经投过票了哦", "确定", null, new ConfirmDialog() {

					@Override
					public void onOKClick() {

					}

					public void onCancleClick() {

					}
				});
		dialog.show();
	}

	private void upDatePK1Ticket(final int position) {
		showDialow();
		final int pk1_ticket_count = mlists.get(position).getPk1()
				.getPk1_ticket_count();
		UpDatePK1TicketCountTask task = new UpDatePK1TicketCountTask();
		task.setmCallBack(new AbstractTaskPostCallBack<RetError>() {
			@Override
			public void taskFinish(RetError result) {
				dismissDialog();
				if (result != RetError.NONE) {
					return;
				}
				mlists.get(position).getPk1()
						.setPk1_ticket_count(pk1_ticket_count + 1);
				mlists.get(position).setIs_voted(true);
				if (mlists.get(position).getPk1().getPk1_ticket_count() >= Constants.pk_count) {
					mlists.get(position).setPk_state(1);
					mlists.get(position).setPk_winer_user_id(
							mlists.get(position).getPk1().getPk1_user_id());
				}
				notifyDataSetChanged();
			}
		});
		PKData pk = new PKData();
		pk.setPk_id(mlists.get(position).getPk_id());
		PK1 pk1 = new PK1();
		pk1.setPk1_ticket_count(pk1_ticket_count + 1);
		pk1.setPk1_user_id(mlists.get(position).getPk1().getPk1_user_id());
		pk.setPk1(pk1);
		PK2 pk2 = new PK2();
		pk2.setPk2_user_id(mlists.get(position).getPk2().getPk2_user_id());
		pk.setPk2(pk2);
		task.executeParallel(pk);
	}

	private void upDatePK2Ticket(final int position) {
		showDialow();
		final int pk2_ticket_count = mlists.get(position).getPk2()
				.getPk2_ticket_count();
		UpDatePK2TicketCountTask task = new UpDatePK2TicketCountTask();
		task.setmCallBack(new AbstractTaskPostCallBack<RetError>() {
			@Override
			public void taskFinish(RetError result) {
				dismissDialog();
				if (result != RetError.NONE) {
					return;
				}
				mlists.get(position).getPk2()
						.setPk2_ticket_count(pk2_ticket_count + 1);
				mlists.get(position).setIs_voted(true);
				if (mlists.get(position).getPk2().getPk2_ticket_count() >= Constants.pk_count) {
					mlists.get(position).setPk_state(1);
					mlists.get(position).setPk_winer_user_id(
							mlists.get(position).getPk2().getPk2_user_id());
				}
				notifyDataSetChanged();
			}
		});
		PKData pk = new PKData();
		pk.setPk_id(mlists.get(position).getPk_id());
		PK2 pk2 = new PK2();
		pk2.setPk2_ticket_count(pk2_ticket_count + 1);
		pk2.setPk2_user_id(mlists.get(position).getPk2().getPk2_user_id());
		pk.setPk2(pk2);
		PK1 pk1 = new PK1();
		pk1.setPk1_user_id(mlists.get(position).getPk1().getPk1_user_id());
		pk.setPk1(pk1);
		task.executeParallel(pk);
	}

	private void showMenu(View v, final int user_id, final String picture_url) {
		MenuPopwindow pop = new MenuPopwindow(mContext, v, new String[] {
				"看大图", "查看PK者信息" });
		pop.setCallback(new OnMenuListOnclick() {

			@Override
			public void onclick(int position) {
				switch (position) {
				case 0:
					Utils.showBigPicture(picture_url, mContext);
					break;
				case 1:
					mContext.startActivity(new Intent(mContext,
							UserInfoActivity.class)
							.putExtra("user_id", user_id));
					Utils.leftOutRightIn(mContext);
					break;
				default:
					break;
				}
			}
		});
		pop.show();
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

	private void showDialow() {
		dialog = DialogUtil.createLoadingDialog(mContext, "投票中...");
		dialog.show();
	}

	private void dismissDialog() {
		if (dialog != null) {
			dialog.dismiss();
		}
	}
}
