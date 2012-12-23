package com.fanshuo.android.bestnotes.app.fragments;

import com.fanshuo.android.bestnotes.R;
import com.fanshuo.android.bestnotes.app.adapters.SlideLeftFragmentAdapter;
import com.fanshuo.android.bestnotes.app.model.SlideLeftListItem;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author fanshuo
 * @date 2012-12-23
 */
public class SlideLeftFragment extends ListFragment{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_slide_left, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		SlideLeftFragmentAdapter adapter = new SlideLeftFragmentAdapter(getActivity());
		adapter.add(new SlideLeftListItem("个人笔记", R.drawable.ic_launcher));
		adapter.add(new SlideLeftListItem("关于", R.drawable.ic_launcher));
		setListAdapter(adapter);
	}
	

}
