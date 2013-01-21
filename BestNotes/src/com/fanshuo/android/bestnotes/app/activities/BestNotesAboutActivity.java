package com.fanshuo.android.bestnotes.app.activities;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.fanshuo.android.bestnotes.Constants;
import com.fanshuo.android.bestnotes.R;
import com.fanshuo.android.bestnotes.app.utils.APPUtil;
import com.fanshuo.android.bestnotes.app.utils.ActivityUtil;
import com.fanshuo.android.bestnotes.inapp.InAppUtil;
import com.umeng.analytics.MobclickAgent;

/**
 * @author shuo
 * @date 2013-1-14 下午11:12:18
 * @version V1.0
 */
public class BestNotesAboutActivity extends BestNotesBaseActivity implements OnClickListener{

	InAppUtil util;
	LinearLayout layout_version;
	Button bt_donate;
	ImageView iv_feedme;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_about);
		TextView tv_version = (TextView) findViewById(R.id.tv_version);
		String version = String.format(getResources().getString(R.string.version), APPUtil.getVersionName(this));
		tv_version.setText(version);
		layout_version = (LinearLayout) findViewById(R.id.layout_version);
		layout_version.setOnClickListener(this);
		bt_donate = (Button) findViewById(R.id.bt_donate);
		iv_feedme = (ImageView) findViewById(R.id.iv_feedme);
		util = new InAppUtil(this, InAppUtil.checkPointsProduct.getProductId());
		getSupportActionBar().setTitle(getResources().getString(R.string.menu_about));
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	public void feedMe(View v){
		MobclickAgent.onEvent(this, Constants.UMENG_EVENT.DONATE);
		util.purchaseCheckPoints();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == InAppUtil.REQUEST_CODE_PURCHASE){
			// Pass on the activity result to the helper for handling
			if (!util.handleResult(requestCode, resultCode, data)) {
				// not handled, so handle it ourselves (here's where you'd
				// perform any handling of activity results not related to in-app
				// billing...
				super.onActivityResult(requestCode, resultCode, data);
			} else {
				//TODO
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			this.finish();
			break;
		}
		return true;
	}

	int clickNum = 0;
//	Timer timer = new Timer();
//	TimerTask task = new TimerTask() {
//		@Override
//		public void run() {
//			clickNum = 0;
//		}
//	};
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.layout_version:
			MobclickAgent.onEvent(this, Constants.UMENG_EVENT.VERSION);
			clickNum ++;
			if(clickNum < 7){
//				ActivityUtil.showCenterShortToast(this, "请继续点击" + (7-clickNum) + "次");
//				timer.schedule(task, 3000);
			}else{
				ActivityUtil.shakeView5Pix(this, bt_donate);
				ActivityUtil.showCenterShortToast(this, R.string.feed_me);
				iv_feedme.setVisibility(View.VISIBLE);
				ActivityUtil.shakeView5Pix(this, iv_feedme);
				clickNum = 0;
			}
			break;
		}
	}
	
	
	
}
