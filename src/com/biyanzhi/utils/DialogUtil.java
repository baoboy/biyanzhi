package com.biyanzhi.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.biyanzhi.R;
import com.biyianzhi.interfaces.ConfirmDialog;

import fynn.app.PromptDialog;

public class DialogUtil {
	public static Dialog createLoadingDialog(Context context, String msg) {

		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.loading_dialog, null);// �õ�����view
		LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// ���ز���
		// main.xml�е�ImageView
		ImageView spaceshipImage = (ImageView) v.findViewById(R.id.img);
		TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);// ��ʾ����
		// ���ض���
		Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
				context, R.anim.load_animation);
		// ʹ��ImageView��ʾ����
		spaceshipImage.startAnimation(hyperspaceJumpAnimation);
		tipTextView.setText(msg);// ���ü�����Ϣ

		Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);// �����Զ�����ʽdialog

		// loadingDialog.setCancelable(false);// �������á����ؼ���ȡ��
		loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
				Utils.getSecreenWidth(context) - 130,
				LinearLayout.LayoutParams.MATCH_PARENT));// ���ò���

		return loadingDialog;

	}

	public static Dialog createLoadingDialog(Context context) {
		return createLoadingDialog(context, "���Ժ�...");
	}

	/**
	 * ȷ�϶Ի���
	 * 
	 * @param context
	 * @param title
	 * @param content
	 */
	public static PromptDialog.Builder confirmDialog(Context context,
			String content, String txtOk, String txtCancle,
			final ConfirmDialog callBack) {

		PromptDialog.Builder dialog = new PromptDialog.Builder(context);
		dialog.setTitle("��ʾ");
		dialog.setViewStyle(PromptDialog.VIEW_STYLE_TITLEBAR);
		dialog.setMessage(content);
		dialog.setButton1(txtOk, new PromptDialog.OnClickListener() {

			@Override
			public void onClick(Dialog dialog, int which) {
				dialog.dismiss();
				callBack.onOKClick();

			}
		});
		dialog.setButton2(txtCancle, new PromptDialog.OnClickListener() {

			@Override
			public void onClick(Dialog dialog, int which) {
				dialog.dismiss();
				callBack.onCancleClick();

			}
		});

		return dialog;

	}

}
