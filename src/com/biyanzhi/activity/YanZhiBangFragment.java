package com.biyanzhi.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.biyanzhi.R;

public class YanZhiBangFragment extends Fragment implements OnClickListener {
	private FragmentTransaction fraTra = null;
	private FragmentManager manager;
	private GirlFragment gril_fragment;
	private BoyFragment boy_fragment;
	private Button btn_girl;
	private Button btn_boy;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.yanzhibang_layout, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
		initFragment();
	}

	private void initView() {
		btn_girl = (Button) getView().findViewById(R.id.btn_girl);
		btn_boy = (Button) getView().findViewById(R.id.btn_boy);
		setListener();
	}

	private void setListener() {
		btn_girl.setOnClickListener(this);
		btn_boy.setOnClickListener(this);
	}

	private void initFragment() {
		manager = getChildFragmentManager();
		fraTra = manager.beginTransaction();
		gril_fragment = new GirlFragment();
		fraTra.add(R.id.main_layout, gril_fragment);
		fraTra.commit();
	}

	@Override
	public void onClick(View v) {
		fraTra = getChildFragmentManager().beginTransaction();
		switch (v.getId()) {
		case R.id.btn_girl:
			btn_girl.setTextColor(getResources().getColor(R.color.pciture_blue));
			btn_boy.setTextColor(getResources().getColor(R.color.pciture_text));
			if (boy_fragment != null) {
				fraTra.hide(boy_fragment);
			}
			fraTra.show(gril_fragment);
			break;
		case R.id.btn_boy:
			btn_boy.setTextColor(getResources().getColor(R.color.pciture_blue));
			btn_girl.setTextColor(getResources().getColor(R.color.pciture_text));
			if (boy_fragment == null) {
				boy_fragment = new BoyFragment();
				fraTra.add(R.id.main_layout, boy_fragment);
			} else {
				gril_fragment.onResume();
			}
			if (gril_fragment != null) {
				fraTra.hide(gril_fragment);
			}
			fraTra.show(boy_fragment);
			break;
		default:
			break;
		}
		fraTra.commit();

	}
}
