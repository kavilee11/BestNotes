package com.fanshuo.android.bestnotes.app.activities;

import android.content.Intent;
import android.os.Bundle;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.fanshuo.android.bestnotes.R;
import com.umeng.analytics.MobclickAgent;

public class BestNotesBaseActivity extends SherlockFragmentActivity{

	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}
	
	protected void startActivity(Class<?> cls, Bundle bundle) {
		Intent intent = new Intent(this, cls);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		startActivity(intent);
		this.overridePendingTransition(R.anim.start_activity_enter, R.anim.start_activity_exit);
	}
	
	protected void startActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
		Intent intent = new Intent(this, cls);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		startActivityForResult(intent, requestCode);
		this.overridePendingTransition(R.anim.start_activity_enter, R.anim.start_activity_exit);
	}

	@Override
	public void finish() {
		super.finish();
		this.overridePendingTransition(R.anim.finish_activity_enter, R.anim.finish_activity_exit);
	}

	@Override
	public void onBackPressed() {
		finish();
	}
}
