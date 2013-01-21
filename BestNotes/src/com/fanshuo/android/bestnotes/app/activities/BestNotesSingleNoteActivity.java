package com.fanshuo.android.bestnotes.app.activities;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.fanshuo.android.bestnotes.Constants;
import com.fanshuo.android.bestnotes.R;
import com.fanshuo.android.bestnotes.app.fragments.BestNotesInnerPageFragment;

import android.content.Intent;
import android.os.Bundle;

/**
 * @author shuo
 * @date 2013-1-14 下午12:26:34
 * @version V1.0
 */
public class BestNotesSingleNoteActivity extends BestNotesBaseActivity {

	int noteID = 0;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_single_note);
		noteID = getIntent().getExtras().getInt(
				Constants.BundleKey.NOTE_ID);
		BestNotesInnerPageFragment fragment = BestNotesInnerPageFragment
				.getInstance(noteID);
		getSupportFragmentManager().beginTransaction()
				.add(R.id.fragment, fragment).commit();
		getSupportActionBar().setTitle(
				getIntent().getExtras().getString(
						Constants.BundleKey.NOTE_TITLE));
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.activity_view_note, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			this.finish();
			break;
		case R.id.menu_edit:
			Bundle bundle = new Bundle();
			bundle.putInt(Constants.BundleKey.NOTE_ID, noteID);
			startActivity(BestNotesEditNoteActivity.class, bundle);
			break;
		}
		return true;
	}

}
