package com.biyanzhi.activity;

import java.util.List;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.biyanzhi.R;
import com.biyanzhi.utils.DialogUtil;
import com.biyianzhi.interfaces.ConfirmDialog;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMConversation;
import com.easemob.chat.EMMessage;
import com.easemob.chat.TextMessageBody;

import fynn.app.PromptDialog;

public class PKPromptActivity extends BaseActivity {
	private TextView txt_title;
	private String user_chat_id = "";
	private EMConversation conversation;
	private EMMessage lastMessage;
	private TextMessageBody txtBody;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pkprompt);
		initView();
		getDataByMessage();
		pkPrompt();
	}

	private void initView() {
		txt_title = (TextView) findViewById(R.id.title_txt);
		txt_title.setText("PK通知");
		findViewById(R.id.back).setOnClickListener(this);
	}

	private void getDataByMessage() {
		user_chat_id = getIntent().getStringExtra("user_chat_id");
		conversation = EMChatManager.getInstance()
				.getConversation(user_chat_id);
		lastMessage = conversation.getLastMessage();
		txtBody = (TextMessageBody) lastMessage.getBody();

	}

	private void pkPrompt() {
		PromptDialog.Builder dialog = DialogUtil.confirmDialog(this,
				txtBody.getMessage(), "确定", null, new ConfirmDialog() {

					@Override
					public void onOKClick() {
						List<EMMessage> messages = conversation
								.getAllMessages();
						for (int i = messages.size() - 1; i >= 0; i--) {
							conversation.removeMessage(messages.get(i)
									.getMsgId());
						}
						conversation.resetUnsetMsgCount();
						finishThisActivity();
					}

					public void onCancleClick() {

					}
				});
		dialog.show();
	}

	@Override
	public void onClick(View v) {
		finishThisActivity();
	}
}
