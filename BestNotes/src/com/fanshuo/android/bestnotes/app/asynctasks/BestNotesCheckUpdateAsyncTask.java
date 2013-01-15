package com.fanshuo.android.bestnotes.app.asynctasks;

import com.fanshuo.android.bestnotes.Constants;
import com.fanshuo.android.bestnotes.R;
import com.fanshuo.android.bestnotes.api.BestNotesNetUtil;
import com.fanshuo.android.bestnotes.app.fragments.dialog.BestNotesUpdateInfoDialogFragment;
import com.fanshuo.android.bestnotes.app.model.BestNotesVersionInfoModel;
import com.fanshuo.android.bestnotes.app.utils.APPUtil;
import com.fanshuo.android.bestnotes.app.utils.ActivityUtil;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

/**
 * @author shuo
 * @date 2013-1-14 下午5:27:24
 * @version V1.0
 */
public class BestNotesCheckUpdateAsyncTask extends AsyncTask<Void, Void, Boolean> {

	ProgressDialog dialog;
	Context context;
	FragmentManager manager;
	BestNotesVersionInfoModel info;
	
	
	public BestNotesCheckUpdateAsyncTask(Context context, FragmentManager manager) {
		super();
		this.context = context;
		this.manager = manager;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		dialog = ProgressDialog.show(context, context.getResources().getString(R.string.please_wait), context.getResources().getString(R.string.check_update), true, true);
	}

	@Override
	protected Boolean doInBackground(Void... params) {
		BestNotesNetUtil netUtil = new BestNotesNetUtil();
		info = netUtil.checkUpdate();
		int curVersionCode = APPUtil.getVersionCode(context);
		if(curVersionCode < info.getVersionCode()){
			return true;
		}
		else{
			return false;
		}
	}

	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		dialog.dismiss();
		if(result){
			BestNotesUpdateInfoDialogFragment dialogFragment = new BestNotesUpdateInfoDialogFragment();
			Bundle bundle = new Bundle();
			bundle.putString(Constants.BundleKey.CHECK_UPDATE_LINK, info.getLink());
			bundle.putString(Constants.BundleKey.CHECK_UPDATE_VERSION_INFO, info.getUpdateInfo());
			dialogFragment.setArguments(bundle);
			dialogFragment.show(manager, "");
		}
		else{
			ActivityUtil.showCenterShortToast(context, R.string.no_new_version);
		}
	}

}
