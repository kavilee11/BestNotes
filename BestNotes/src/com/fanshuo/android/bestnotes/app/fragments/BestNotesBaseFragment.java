package com.fanshuo.android.bestnotes.app.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;
import com.fanshuo.android.bestnotes.R;

/**
 * @author fanshuo
 * @date 2012-12-25 下午5:14:00
 * @version V1.0
 */
public class BestNotesBaseFragment extends SherlockFragment{

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	protected void startActivity(Class<?> cls, Bundle bundle) {
		Intent intent = new Intent(getActivity(), cls);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		startActivity(intent);
		getActivity().overridePendingTransition(R.anim.start_activity_enter, R.anim.start_activity_exit);
	}
	
	protected void startActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
		Intent intent = new Intent(getActivity(), cls);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		startActivityForResult(intent, requestCode);
		getActivity().overridePendingTransition(R.anim.start_activity_enter, R.anim.start_activity_exit);
	}
	
}
