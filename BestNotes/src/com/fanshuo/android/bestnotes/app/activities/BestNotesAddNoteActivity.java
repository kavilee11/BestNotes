package com.fanshuo.android.bestnotes.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.fanshuo.android.bestnotes.Constants;
import com.fanshuo.android.bestnotes.MainActivity;
import com.fanshuo.android.bestnotes.R;
import com.fanshuo.android.bestnotes.app.fragments.SlideLeftListFragment;
import com.fanshuo.android.bestnotes.app.model.BestNotesTextNoteModel;
import com.fanshuo.android.bestnotes.app.utils.ActivityUtil;
import com.fanshuo.android.bestnotes.db.DAO.BestNotesTextNoteDao;

/**
 * @author fanshuo
 * @date 2012-12-25 上午9:49:49
 * @version V1.0
 */
public class BestNotesAddNoteActivity extends BestNotesBaseActivity{

	EditText et_title, et_content;
	double lat, lng;
	TextView tv_location;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		getSupportActionBar().setTitle("");
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.bestnotes_activity_add_note);
		et_title = (EditText) findViewById(R.id.et_title);
		et_content = (EditText) findViewById(R.id.et_content);
		tv_location = (TextView) findViewById(R.id.tv_location);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.activity_add_note, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			this.finish();
			break;
		case R.id.menu_save:
			BestNotesTextNoteModel note = new BestNotesTextNoteModel();
			if (TextUtils.isEmpty(et_title.getText().toString())) {
				et_title.setText(getResources().getString(
						R.string.untitled_notes));
			}
			note.setContent(et_content.getText().toString());
			note.setCreateTime(System.currentTimeMillis());
			note.setModificationTime(System.currentTimeMillis());
			note.setTitle(et_title.getText().toString());
			note.setLongtitude(lng);
			note.setLatitude(lat);
			BestNotesTextNoteDao dao = new BestNotesTextNoteDao(this);
			int ret = dao.addNote(note, true);
			if (ret == 1) {
				MainActivity.handler.sendEmptyMessage(Constants.MSG_WHAT.WHAT_REFRESH_LISTVIEW);
				SlideLeftListFragment.handler.sendEmptyMessage(Constants.MSG_WHAT.WHAT_REFRESH_LISTVIEW);
				ActivityUtil.showCenterShortToast(this, getResources()
						.getString(R.string.save_success));
				this.finish();
			} else {
				ActivityUtil.showCenterShortToast(this, getResources()
						.getString(R.string.save_faild));
			}
			break;
		case R.id.menu_loc:
			Bundle bundle = new Bundle();
			bundle.putString(Constants.BundleKey.ADD_LOCATION_TITLE, et_title.getText().toString());
			startActivityForResult(BestNotesAddLocationActivity.class, bundle, Constants.RequestCodes.REQUEST_CODE_ADD_POSITION);
			break;
		}
		return true;
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		switch (arg0) {
		case Constants.RequestCodes.REQUEST_CODE_ADD_POSITION:
			if(arg1 == RESULT_OK){
				tv_location.setText(arg2.getExtras().getString(Constants.BundleKey.ADD_LOCATION_RESULT_STRING));
				lat = arg2.getDoubleExtra(Constants.BundleKey.ADD_LOCATION_RESULT_LATITUDE, 0d);
				lng = arg2.getDoubleExtra(Constants.BundleKey.ADD_LOCATION_RESULT_LONGITUDE, 0d);
			}
			break;
		}
	}
	
	

}
