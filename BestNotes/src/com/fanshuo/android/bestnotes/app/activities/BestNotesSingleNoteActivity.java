package com.fanshuo.android.bestnotes.app.activities;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.fanshuo.android.bestnotes.Constants;
import com.fanshuo.android.bestnotes.R;
import com.fanshuo.android.bestnotes.app.fragments.BestNotesInnerPageFragment;

import android.os.Bundle;

/**
 * @author shuo
 * @date 2013-1-14 下午12:26:34
 * @version V1.0
 */
public class BestNotesSingleNoteActivity extends BestNotesBaseActivity {

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_single_note);
		BestNotesInnerPageFragment fragment = BestNotesInnerPageFragment
				.getInstance(getIntent().getExtras().getInt(
						Constants.BundleKey.NOTE_ID));
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

}
