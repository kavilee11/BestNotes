package com.fanshuo.android.bestnotes.app.asynctasks;

import android.content.Context;
import android.location.LocationManager;
import android.os.AsyncTask;

import com.fanshuo.android.bestnotes.R;
import com.fanshuo.android.bestnotes.app.utils.ActivityUtil;

/**
 * @author shuo
 * @date 2013-1-9 下午9:45:51
 * @version V1.0
 */
public class BestNotesGetLocationAsyncTask extends AsyncTask<Void, Void, Void> {

	Context context;
	
	public BestNotesGetLocationAsyncTask(Context context) {
		super();
		this.context = context;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		ActivityUtil.showCenterShortToast(context, context.getResources().getString(R.string.getting_location));
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);  
//		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,  
//				 0, 0, locationListener);  
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
	}

}
