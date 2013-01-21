package com.fanshuo.android.bestnotes.app.activities;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.fanshuo.android.bestnotes.Constants;
import com.fanshuo.android.bestnotes.R;
import com.fanshuo.android.bestnotes.app.model.BestNotesTextNoteModel;
import com.fanshuo.android.bestnotes.app.utils.ActivityUtil;
import com.fanshuo.android.bestnotes.db.DAO.BestNotesTextNoteDao;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

/**
 * @author shuo
 * @date 2013-1-15 下午5:56:54
 * @version V1.0
 */
public class BestNotesEditNoteActivity extends BestNotesBaseActivity {

	EditText et_title, et_content;
	TextView tv_location;
	private BestNotesTextNoteDao dao = null;
	private BestNotesTextNoteModel note;
	double lat, lng;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		getSupportActionBar().setTitle("");
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.activity_edit_note);
		et_title = (EditText) findViewById(R.id.et_title);
		et_content = (EditText) findViewById(R.id.et_content);
		tv_location = (TextView) findViewById(R.id.tv_location);
		int noteID = getIntent().getExtras().getInt(
				Constants.BundleKey.NOTE_ID);
		dao = new BestNotesTextNoteDao(this);
		note = dao.getTextNoteById(noteID);
		
		et_title.setText(note.getTitle());
		et_content.setText(note.getContent());
		tv_location.setText(note.getLatitude() + note.getLongtitude() + "");
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
			int ret = dao.updateNote(note, true);
			if (ret == 1) {
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
