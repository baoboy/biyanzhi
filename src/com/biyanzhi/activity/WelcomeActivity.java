package com.biyanzhi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.biyanzhi.R;
import com.biyanzhi.utils.SharedUtils;

public class WelcomeActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_welcome);
		new Thread() {
			public void run() {
				try {
					sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				startActivity(new Intent(WelcomeActivity.this,
						MainActivity.class));
				// if (SharedUtils.getIntUid() == 0) {
				// startActivity(new Intent(WelcomeActivity.this,
				// LoginActivity.class));
				// } else {
				// startActivity(new Intent(WelcomeActivity.this,
				// MainActivity.class));
				// }
				finish();
			}
		}.start();
	}
}
