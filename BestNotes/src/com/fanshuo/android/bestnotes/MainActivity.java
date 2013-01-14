package com.fanshuo.android.bestnotes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.util.LangUtils;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.fanshuo.android.bestnotes.app.activities.BestNotesAddNoteActivity;
import com.fanshuo.android.bestnotes.app.activities.BestNotesInnerPagerActivity;
import com.fanshuo.android.bestnotes.app.activities.BestNotesSingleNoteActivity;
import com.fanshuo.android.bestnotes.app.adapters.BestNotesTextNoteAdapter;
import com.fanshuo.android.bestnotes.app.fragments.SlideRightFragment;
import com.fanshuo.android.bestnotes.app.fragments.SlideLeftListFragment;
import com.fanshuo.android.bestnotes.app.model.BestNotesTextNoteModel;
import com.fanshuo.android.bestnotes.app.utils.Debug;
import com.fanshuo.android.bestnotes.app.view.SelectionListView;
import com.fanshuo.android.bestnotes.db.DAO.BestNotesTextNoteDao;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingFragmentActivity;

public class MainActivity extends SlidingFragmentActivity implements
		ActionBar.OnNavigationListener,
		OnItemClickListener,
		OnInfoWindowClickListener{
	private SlideLeftListFragment leftFrag;
//	private ListFragment rightFrag;
	private SelectionListView lv;
	BestNotesTextNoteAdapter adapter;
	List<BestNotesTextNoteModel> list;
	GoogleMap mMap;

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

		FragmentTransaction t = this.getSupportFragmentManager()
				.beginTransaction();
		leftFrag = new SlideLeftListFragment();
		t.replace(R.id.frame_left, leftFrag);
//		rightFrag = new SlideRightFragment();
//		t.replace(R.id.frame_right, rightFrag);
		t.commit();

		getSupportActionBar().setTitle("");
		getSupportActionBar().setHomeButtonEnabled(true);
		// 增加下拉列表
		getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		SpinnerAdapter mSpinnerAdapter = ArrayAdapter.createFromResource(this,
				R.array.main_action_spinner,
				android.R.layout.simple_list_item_1);
		getSupportActionBar().setListNavigationCallbacks(mSpinnerAdapter, this);

		// /////////////////初始化ListView
		list = new ArrayList<BestNotesTextNoteModel>();
		BestNotesTextNoteDao dao = new BestNotesTextNoteDao(this);
		list = dao.getAllNotes(BestNotesTextNoteModel.MODIFY_TIME, false);
		lv = (SelectionListView) findViewById(R.id.main_lv);
		adapter = new BestNotesTextNoteAdapter(this);
		for (BestNotesTextNoteModel item : list) {
			adapter.add(item);
		}
		lv.setAdapter(adapter);

		lv.setOnItemClickListener(this);
		
		initMap();
	}

	@Override
	protected void onResume() {
		super.onResume();

		BestNotesTextNoteDao dao = new BestNotesTextNoteDao(this);
		list = dao.getAllNotes(BestNotesTextNoteModel.MODIFY_TIME, false);
		adapter.clear();
		for (BestNotesTextNoteModel item : list) {
			adapter.add(item);
		}
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

	private void bnStartActivity(Class<?> cls, Bundle bundle) {
		Intent intent = new Intent(this, cls);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		startActivity(intent);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = new Intent(this, BestNotesInnerPagerActivity.class);
		intent.putExtra(Constants.BundleKey.CLICKED_POSITION, position);
		startActivity(intent);
	}
	
	private Map<String, BestNotesTextNoteModel> notesMarkerIdMap = new HashMap<String, BestNotesTextNoteModel>();
	private void initMap(){
		mMap = ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
		mMap.setMyLocationEnabled(true);
		if(list != null){
			for (BestNotesTextNoteModel item : list) {
				if(item.getLatitude() != 0 && item.getLongtitude() != 0){
					LatLng position = new LatLng(item.getLatitude(), item.getLongtitude());
					Marker marker = mMap.addMarker(new MarkerOptions().position(position).title(item.getTitle()));
					notesMarkerIdMap.put(marker.getId(), item);
				}
			}
		}
		mMap.setOnInfoWindowClickListener(this);
	}

	@Override
	public void onInfoWindowClick(Marker arg0) {
		Intent intent = new Intent(this, BestNotesSingleNoteActivity.class);
		Bundle bundle = new Bundle();
		int id = notesMarkerIdMap.get(arg0.getId()).get_id();
		String title = notesMarkerIdMap.get(arg0.getId()).getTitle();
		bundle.putInt(Constants.BundleKey.NOTE_ID, id);
		bundle.putString(Constants.BundleKey.NOTE_TITLE, title);
		intent.putExtras(bundle);
		startActivity(intent);
	}
}
