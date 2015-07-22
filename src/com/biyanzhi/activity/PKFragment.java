package com.biyanzhi.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.biyanzhi.R;
import com.biyanzhi.adapter.PKAdapter;

public class PKFragment extends Fragment {
	private ListView mListView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.pk_layout, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
	}

	private void initView() {
		mListView = (ListView) getView().findViewById(R.id.listView1);
		mListView.setAdapter(new PKAdapter(getActivity()));
	}
}
