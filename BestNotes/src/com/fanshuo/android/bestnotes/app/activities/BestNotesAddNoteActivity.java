package com.fanshuo.android.bestnotes.app.activities;

import java.sql.SQLException;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.fanshuo.android.bestnotes.R;
import com.fanshuo.android.bestnotes.app.model.BestNotesTextNoteModel;
import com.fanshuo.android.bestnotes.app.utils.ActivityUtil;
import com.fanshuo.android.bestnotes.db.DAO.BestNotesTextNoteDao;
import com.j256.ormlite.dao.Dao;

/**
 * @author fanshuo
 * @date 2012-12-25 上午9:49:49
 * @version V1.0
 */
public class BestNotesAddNoteActivity extends BestNotesBaseActivity {

	EditText et_title, et_content;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		getSupportActionBar().setTitle("");
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.bestnotes_activity_add_note);
		et_title = (EditText) findViewById(R.id.et_title);
		et_content = (EditText) findViewById(R.id.et_content);

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
				et_title.requestFocus();
				ActivityUtil.shakeView5Pix(this, et_title);
				ActivityUtil.showCenterShortToast(this, getResources()
						.getString(R.string.please_input_title));
			} else {
				BestNotesTextNoteModel note = new BestNotesTextNoteModel();
				note.setContent(et_content.getText().toString());
				note.setCreateTime(System.currentTimeMillis());
				note.setModificationTime(System.currentTimeMillis());
				note.setTitle(et_title.getText().toString());
				BestNotesTextNoteDao dao = new BestNotesTextNoteDao(this);
				int ret = dao.addNote(note);
				if (ret == 1) {
					ActivityUtil.showCenterShortToast(this, getResources()
							.getString(R.string.save_success));
					this.finish();
				} else {
					ActivityUtil.showCenterShortToast(this, getResources()
							.getString(R.string.save_faild));
				}

			}
			break;
		}
		return true;
	}

}
