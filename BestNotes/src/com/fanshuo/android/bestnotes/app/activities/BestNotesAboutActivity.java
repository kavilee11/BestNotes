package com.fanshuo.android.bestnotes.app.activities;

import com.fanshuo.android.bestnotes.R;
import com.fanshuo.android.bestnotes.app.utils.APPUtil;
import com.fanshuo.android.bestnotes.inapp.InAppUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * @author shuo
 * @date 2013-1-14 下午11:12:18
 * @version V1.0
 */
public class BestNotesAboutActivity extends BestNotesBaseActivity {

	InAppUtil util;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_about);
		TextView tv_version = (TextView) findViewById(R.id.tv_version);
		tv_version.setText(APPUtil.getVersionName(this));
		util = new InAppUtil(this, InAppUtil.checkPointsProduct.getProductId());
	}
	
	public void feedMe(View v){
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
	
}
