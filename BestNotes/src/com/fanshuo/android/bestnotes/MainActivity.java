package com.fanshuo.android.bestnotes;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.ActionMode.Callback;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.ShareActionProvider;
import com.fanshuo.android.bestnotes.app.activities.BestNotesAddNoteActivity;
import com.fanshuo.android.bestnotes.app.adapters.BestNotesTextNoteAdapter;
import com.fanshuo.android.bestnotes.app.adapters.SlideLeftFragmentAdapter;
import com.fanshuo.android.bestnotes.app.fragments.SlideLeftFragment;
import com.fanshuo.android.bestnotes.app.model.BestNotesTextNoteModel;
import com.fanshuo.android.bestnotes.app.model.SlideLeftListItem;
import com.fanshuo.android.bestnotes.app.view.SelectionListView;
import com.fanshuo.android.bestnotes.db.DAO.BestNotesTextNoteDao;
import com.fanshuo.android.bestnotes.db.ormsqlite.BestNotesDBHelper;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingFragmentActivity;

public class MainActivity extends SlidingFragmentActivity implements ActionBar.OnNavigationListener{

	private ListFragment leftFrag, rightFrag;
	private SelectionListView lv;
	BestNotesTextNoteAdapter adapter;
	List<BestNotesTextNoteModel> list;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setBehindContentView(R.layout.fram_slide_left);
		SlidingMenu sm = getSlidingMenu();
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeDegree(0.35f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		sm.setMode(SlidingMenu.LEFT_RIGHT);
		sm.setSecondaryMenu(R.layout.fram_slide_right);
		sm.setSecondaryShadowDrawable(R.drawable.shadowright);
		getSlidingMenu().setBehindScrollScale(0);
		
		FragmentTransaction t = this.getSupportFragmentManager().beginTransaction();
		leftFrag = new SlideLeftFragment();
		t.replace(R.id.frame_left, leftFrag);
		rightFrag = new SlideLeftFragment();
		t.replace(R.id.frame_right, rightFrag);
		t.commit();
		
		getSupportActionBar().setTitle("");
		getSupportActionBar().setHomeButtonEnabled(true);
		//增加下拉列表
		getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		SpinnerAdapter mSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.main_action_spinner, android.R.layout.simple_list_item_1);
		getSupportActionBar().setListNavigationCallbacks(mSpinnerAdapter, this);
		
		
		///////////////////初始化ListView
		list = new ArrayList<BestNotesTextNoteModel>();
		BestNotesTextNoteDao dao = new BestNotesTextNoteDao(this);
		list = dao.getAllNotes(BestNotesTextNoteModel.MODIFY_TIME, false);
		lv = (SelectionListView) findViewById(R.id.main_lv);
		adapter = new BestNotesTextNoteAdapter(this);
		adapter.addAll(list);
		lv.setAdapter(adapter);
		
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				System.out.println("-------onItemClick");
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		BestNotesTextNoteDao dao = new BestNotesTextNoteDao(this);
		list = dao.getAllNotes(BestNotesTextNoteModel.MODIFY_TIME, false);
		adapter.clear();
		adapter.addAll(list);
		adapter.notifyDataSetChanged();
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_new:
			bnStartActivity(BestNotesAddNoteActivity.class, null);
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		return false;
	}
	
	private void bnStartActivity(Class<?> cls, Bundle bundle){
		Intent intent = new Intent(this, cls);
		if(bundle != null){
			intent.putExtras(bundle);
		}
		startActivity(intent);
	}
	
}
