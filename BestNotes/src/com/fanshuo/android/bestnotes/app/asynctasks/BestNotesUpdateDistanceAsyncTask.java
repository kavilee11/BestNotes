package com.fanshuo.android.bestnotes.app.asynctasks;

import java.util.List;

import com.fanshuo.android.bestnotes.R;
import com.fanshuo.android.bestnotes.RuntimeObject;
import com.fanshuo.android.bestnotes.app.interfaces.BestNotesAsyncTaskCallBackInterface;
import com.fanshuo.android.bestnotes.app.model.BestNotesTextNoteModel;
import com.fanshuo.android.bestnotes.app.model.DistanceData;
import com.fanshuo.android.bestnotes.app.utils.WebServiceAPIUtil;
import com.fanshuo.android.bestnotes.db.DAO.BestNotesTextNoteDao;

import android.content.Context;
import android.os.AsyncTask;

/**
 * @author shuo
 * @date 2013-1-21 下午12:09:08
 * @version V1.0
 */
public class BestNotesUpdateDistanceAsyncTask extends
		AsyncTask<Void, Void, Void> {

	Context context;
	BestNotesAsyncTaskCallBackInterface callBack;
	
	public BestNotesUpdateDistanceAsyncTask(Context context, BestNotesAsyncTaskCallBackInterface callBack) {
		super();
		this.context = context;
		this.callBack = callBack;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected Void doInBackground(Void... params) {
		BestNotesTextNoteDao dao = new BestNotesTextNoteDao(context);
		List<BestNotesTextNoteModel> list = dao.getAllNotes(BestNotesTextNoteModel.MODIFY_TIME, false);
		for (BestNotesTextNoteModel item : list) {
			DistanceData data = WebServiceAPIUtil.getDistance(RuntimeObject.myCurrentLat+","+RuntimeObject.myCurrentLng, item.getLatitude()+","+item.getLongtitude());
			if(data != null){
				if(Long.parseLong(data.getDistanceValue()) < 1000){
					item.setDistance(data.getDistanceValue() + context.getResources().getString(R.string.meters));
				}
				else{
					item.setDistance(data.getDistanceText());
				}
				item.setDistanceNum(Long.parseLong(data.getDistanceValue()));
			}
			else{
				item.setDistance(context.getResources().getString(R.string.unknown));
			}
			dao.updateNote(item, false);
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		callBack.afterDoInBackground();
	}

}
