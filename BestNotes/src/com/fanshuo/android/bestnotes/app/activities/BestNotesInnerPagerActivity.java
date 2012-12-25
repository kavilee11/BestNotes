package com.fanshuo.android.bestnotes.app.activities;

import java.util.List;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.fanshuo.android.bestnotes.Constants;
import com.fanshuo.android.bestnotes.R;
import com.fanshuo.android.bestnotes.app.adapters.BestNotesInnerPagerFragmentAdapter;
import com.fanshuo.android.bestnotes.app.model.BestNotesTextNoteModel;
import com.fanshuo.android.bestnotes.db.DAO.BestNotesTextNoteDao;

/**
 * @author fanshuo
 * @date 2012-12-25 下午8:38:53
 * @version V1.0
 */
public class BestNotesInnerPagerActivity extends BestNotesBaseActivity
		implements OnPageChangeListener {

	ViewPager pager;
	BestNotesInnerPagerFragmentAdapter adapter;
	BestNotesTextNoteDao dao;
	List<BestNotesTextNoteModel> list;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		int clickedPosition = getIntent().getIntExtra(Constants.BundleKey.CLICKED_POSITION, 0);
		setContentView(R.layout.bestnotes_activity_inner);
		pager = (ViewPager) findViewById(R.id.viewpager);
		pager.setOnPageChangeListener(this);
		pager.setOffscreenPageLimit(1);
		dao = new BestNotesTextNoteDao(this);
		list = dao.getAllNotes(BestNotesTextNoteModel.MODIFY_TIME, false);
		adapter = new BestNotesInnerPagerFragmentAdapter(getSupportFragmentManager(), list);
		pager.setAdapter(adapter);
		pager.setCurrentItem(clickedPosition);
		getSupportActionBar().setTitle(list.get(clickedPosition).getTitle());
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

	////////////////////////////////
	////	ViewPager Listener
	
	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageSelected(int arg0) {
		getSupportActionBar().setTitle(list.get(arg0).getTitle());
	}
								////
	////////////////////////////////

}
