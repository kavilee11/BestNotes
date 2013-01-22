package com.fanshuo.android.bestnotes.app.asynctasks;

import android.content.Context;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.fanshuo.android.bestnotes.R;
import com.fanshuo.android.bestnotes.app.activities.BestNotesAddLocationActivity;
import com.fanshuo.android.bestnotes.app.interfaces.BestNotesAsyncTaskCallBackInterface;
import com.fanshuo.android.bestnotes.app.model.LocationData;
import com.fanshuo.android.bestnotes.app.utils.ActivityUtil;
import com.fanshuo.android.bestnotes.app.utils.LBSTool;

/**
 * @author shuo
 * @date 2013-1-9 下午9:45:51
 * @version V1.0
 */
public class BestNotesGetLocationAsyncTask extends AsyncTask<Void, Void, Boolean> {

	Context context;
	BestNotesAsyncTaskCallBackInterface callBack;
	LBSTool lbs;
	LocationData location;
	
	public BestNotesGetLocationAsyncTask(Context context, BestNotesAsyncTaskCallBackInterface callBack) {
		super();
		this.context = context;
		this.callBack = callBack;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		ActivityUtil.showCenterShortToast(context, context.getResources().getString(R.string.getting_location));
	}
	
	@Override
	protected Boolean doInBackground(Void... params) {
		lbs = new LBSTool(context);
		location = lbs.getLocation(120000);
		if(!TextUtils.isEmpty(location.getAddress())){
			return true;
		}
		else{
			return false;
		}
	}

	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		if(result){
			callBack.afterDoInBackground(location.getAddress());
		}
	}

}
