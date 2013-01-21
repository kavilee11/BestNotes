package com.fanshuo.android.bestnotes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.fanshuo.android.bestnotes.app.activities.BestNotesAboutActivity;
import com.fanshuo.android.bestnotes.app.activities.BestNotesAddLocationActivity;
import com.fanshuo.android.bestnotes.app.activities.BestNotesAddNoteActivity;
import com.fanshuo.android.bestnotes.app.activities.BestNotesInnerPagerActivity;
import com.fanshuo.android.bestnotes.app.activities.BestNotesSingleNoteActivity;
import com.fanshuo.android.bestnotes.app.adapters.BestNotesTextNoteAdapter;
import com.fanshuo.android.bestnotes.app.adapters.BestNotesTextNoteAdapterByLocation;
import com.fanshuo.android.bestnotes.app.asynctasks.BestNotesCheckUpdateAsyncTask;
import com.fanshuo.android.bestnotes.app.asynctasks.BestNotesUpdateDistanceAsyncTask;
import com.fanshuo.android.bestnotes.app.fragments.SlideLeftListFragment;
import com.fanshuo.android.bestnotes.app.interfaces.BestNotesAsyncTaskCallBackInterface;
import com.fanshuo.android.bestnotes.app.model.BestNotesTextNoteModel;
import com.fanshuo.android.bestnotes.app.model.LocationData;
import com.fanshuo.android.bestnotes.app.utils.LBSTool;
import com.fanshuo.android.bestnotes.app.view.SelectionListView;
import com.fanshuo.android.bestnotes.db.DAO.BestNotesTextNoteDao;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingFragmentActivity;
import com.umeng.analytics.MobclickAgent;

public class MainActivity extends SlidingFragmentActivity implements
		ActionBar.OnNavigationListener, OnItemClickListener,
		OnInfoWindowClickListener, BestNotesAsyncTaskCallBackInterface{
	private SlideLeftListFragment leftFrag;
	// private ListFragment rightFrag;
	private SelectionListView lv;
	BestNotesTextNoteAdapter adapter;
	List<BestNotesTextNoteModel> list;
	GoogleMap mMap;
	public static Handler handler;
	BestNotesTextNoteAdapterByLocation locationAdapter;
	SortType curType;
	private enum SortType{
		time,location
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//设置actionbar的进度条
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
	    getSherlock().setProgressBarIndeterminateVisibility(true);
	    
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
		// rightFrag = new SlideRightFragment();
		// t.replace(R.id.frame_right, rightFrag);
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
		lv = (SelectionListView) findViewById(R.id.main_lv);
		list = new ArrayList<BestNotesTextNoteModel>();
		adapter = new BestNotesTextNoteAdapter(this);
		locationAdapter = new BestNotesTextNoteAdapterByLocation(this);
		BestNotesTextNoteDao dao = new BestNotesTextNoteDao(this);
		list = dao.getAllNotes(BestNotesTextNoteModel.MODIFY_TIME, false);
		for (BestNotesTextNoteModel item : list) {
			adapter.add(item);
		}
		lv.setAdapter(adapter);

		lv.setOnItemClickListener(this);

		initMap();

		initHandler();
		
		curType = SortType.time;
		
	}

	private void initHandler() {
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case Constants.MSG_WHAT.WHAT_REFRESH_LISTVIEW:
					refreshData();
					break;
				}
			}

		};
	}
	@Override
	public void onResume() {
	    super.onResume();
	    MobclickAgent.onResume(this);
	}
	@Override
	public void onPause() {
	    super.onPause();
	    MobclickAgent.onPause(this);
	}

	private void refreshData() {
		BestNotesTextNoteDao dao = new BestNotesTextNoteDao(this);
		list = dao.getAllNotes(BestNotesTextNoteModel.MODIFY_TIME, false);
		adapter.clear();
		for (BestNotesTextNoteModel item : list) {
			adapter.add(item);
		}
		adapter.notifyDataSetChanged();
		mMap.clear();
		initMap();
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
		case R.id.menu_about:
			bnStartActivity(BestNotesAboutActivity.class, null);
			break;
		case R.id.menu_check_update:
			new BestNotesCheckUpdateAsyncTask(this, getSupportFragmentManager())
					.execute();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		switch (itemPosition) {
		case 0:
			curType = SortType.time;
			adapter.clear();
			BestNotesTextNoteDao dao = new BestNotesTextNoteDao(this);
			list = dao.getAllNotes(BestNotesTextNoteModel.MODIFY_TIME, false);
			for (BestNotesTextNoteModel item : list) {
				adapter.add(item);
			}
			lv.setAdapter(adapter);
			break;
		case 1:
			setProgressBarIndeterminateVisibility(true);
			curType = SortType.location;
			if (mMap != null && mMap.getMyLocation() != null) {
				RuntimeObject.myCurrentLat = mMap.getMyLocation().getLatitude();
				RuntimeObject.myCurrentLng = mMap.getMyLocation()
						.getLongitude();
				
			} else {
				LBSTool lbs = new LBSTool(MainActivity.this);
				LocationData location = lbs.getLocation(120000);
				if (location != null) {
					RuntimeObject.myCurrentLat = Double.parseDouble(location
							.getLat());
					RuntimeObject.myCurrentLng = Double.parseDouble(location
							.getLon());
				}
			}
			new BestNotesUpdateDistanceAsyncTask(MainActivity.this, MainActivity.this).execute();
			break;
		}
		return false;
	}

	private void bnStartActivity(Class<?> cls, Bundle bundle) {
		Intent intent = new Intent(this, cls);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		startActivity(intent);
		this.overridePendingTransition(R.anim.start_activity_enter,
				R.anim.start_activity_exit);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Bundle bundle = new Bundle();
		bundle.putInt(Constants.BundleKey.CLICKED_POSITION, position);
		bnStartActivity(BestNotesInnerPagerActivity.class, bundle);
	}

	private Map<String, BestNotesTextNoteModel> notesMarkerIdMap = new HashMap<String, BestNotesTextNoteModel>();

	private void initMap() {
		mMap = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map)).getMap();
		if (mMap != null) {
			mMap.setMyLocationEnabled(true);
			if (list != null) {
				for (BestNotesTextNoteModel item : list) {
					if (item.getLatitude() != 0 && item.getLongtitude() != 0) {
						LatLng position = new LatLng(item.getLatitude(),
								item.getLongtitude());
						Marker marker = mMap.addMarker(new MarkerOptions()
								.position(position).title(item.getTitle()));
						notesMarkerIdMap.put(marker.getId(), item);
					}
				}
			}
			mMap.setOnInfoWindowClickListener(this);
		}
		new AsyncTask<Void, Void, Void>() {
			LBSTool lbs;
			LocationData location;
			@Override
			protected Void doInBackground(Void... params) {
				lbs = new LBSTool(MainActivity.this);
				location = lbs.getLocation(120000);
				return null;
			}
			@Override
			protected void onPostExecute(Void result) {
				super.onPostExecute(result);
				LatLng cur = new LatLng(Double.parseDouble(location.getLat()), Double.parseDouble(location.getLon()));
				mMap.animateCamera(CameraUpdateFactory.newLatLng(cur));
			}
		}.execute();
	}

	@Override
	public void onInfoWindowClick(Marker arg0) {
		Bundle bundle = new Bundle();
		int id = notesMarkerIdMap.get(arg0.getId()).get_id();
		String title = notesMarkerIdMap.get(arg0.getId()).getTitle();
		bundle.putInt(Constants.BundleKey.NOTE_ID, id);
		bundle.putString(Constants.BundleKey.NOTE_TITLE, title);
		bnStartActivity(BestNotesSingleNoteActivity.class, bundle);
	}

	@Override
	public void onBackPressed() {
		this.finish();
		this.overridePendingTransition(R.anim.finish_activity_enter,
				R.anim.finish_activity_exit);
	}

	@Override
	public void afterDoInBackground() {
		setProgressBarIndeterminateVisibility(false);
		BestNotesTextNoteDao dao = new BestNotesTextNoteDao(this);
		switch (curType) {
		case time:
			list = dao.getAllNotes(BestNotesTextNoteModel.MODIFY_TIME,
					false);
			for (BestNotesTextNoteModel item : list) {
				locationAdapter.add(item);
			}
			lv.setAdapter(locationAdapter);
			break;
		case location:
			locationAdapter.clear();
			list = dao.getAllNotes(BestNotesTextNoteModel.DISTANCE_NUM,
					true);
			for (BestNotesTextNoteModel item : list) {
				locationAdapter.add(item);
			}
			lv.setAdapter(locationAdapter);
			break;
		}
	}

}
