package com.biyanzhi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.biyanzhi.R;
import com.biyanzhi.utils.SharedUtils;
import com.biyanzhi.utils.Utils;

public class PKFragment extends Fragment implements OnClickListener {
	private FragmentTransaction fraTra = null;
	private FragmentManager manager;
	private PKIngFrament pk_zhong_fragment;
	private PKFinishedFramgent pk_jieguo_fragment;
	private Button btn_pk_zhong;
	private Button btn_pk_jieguo;
	private ImageView img_pk;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.pk_layout, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
		initFragment();
	}

	private void initView() {
		img_pk = (ImageView) getView().findViewById(R.id.img_pk);
		btn_pk_zhong = (Button) getView().findViewById(R.id.btn_pk_zhong);
		btn_pk_jieguo = (Button) getView().findViewById(R.id.btn_pk_jieguo);
		setListener();
	}

	private void setListener() {
		btn_pk_zhong.setOnClickListener(this);
		btn_pk_jieguo.setOnClickListener(this);
		img_pk.setOnClickListener(this);

	}

	private void initFragment() {
		manager = getChildFragmentManager();
		fraTra = manager.beginTransaction();
		pk_zhong_fragment = new PKIngFrament();
		fraTra.add(R.id.main_layout, pk_zhong_fragment);
		fraTra.commit();
	}

	@Override
	public void onClick(View v) {
		fraTra = getChildFragmentManager().beginTransaction();
		switch (v.getId()) {
		case R.id.btn_pk_zhong:
			btn_pk_zhong.setTextColor(getResources().getColor(
					R.color.pciture_blue));
			btn_pk_jieguo.setTextColor(getResources().getColor(
					R.color.pciture_text));
			if (pk_jieguo_fragment != null) {
				fraTra.hide(pk_jieguo_fragment);
			}
			fraTra.show(pk_zhong_fragment);
			break;
		case R.id.btn_pk_jieguo:
			btn_pk_jieguo.setTextColor(getResources().getColor(
					R.color.pciture_blue));
			btn_pk_zhong.setTextColor(getResources().getColor(
					R.color.pciture_text));
			if (pk_jieguo_fragment == null) {
				pk_jieguo_fragment = new PKFinishedFramgent();
				fraTra.add(R.id.main_layout, pk_jieguo_fragment);
			} else {
				pk_zhong_fragment.onResume();
			}
			if (pk_zhong_fragment != null) {
				fraTra.hide(pk_zhong_fragment);
			}
			fraTra.show(pk_jieguo_fragment);
			break;
		case R.id.img_pk:
			if (SharedUtils.getIntUid() == 0) {
				startActivity(new Intent(getActivity(), LoginActivity.class));
				Utils.leftOutRightIn(getActivity());
				return;
			}
			startActivity(new Intent(getActivity(),
					SelectPKPictureActivity.class));
			Utils.leftOutRightIn(getActivity());
			break;
		default:
			break;
		}
		fraTra.commit();

	}

}
