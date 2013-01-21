package com.fanshuo.android.bestnotes.app.activities;

import android.content.Intent;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.fanshuo.android.bestnotes.Constants;
import com.fanshuo.android.bestnotes.MainActivity;
import com.fanshuo.android.bestnotes.R;
import com.fanshuo.android.bestnotes.app.model.BestNotesTextNoteModel;
import com.fanshuo.android.bestnotes.app.model.LocationData;
import com.fanshuo.android.bestnotes.app.utils.ActivityUtil;
import com.fanshuo.android.bestnotes.app.utils.LBSTool;
import com.fanshuo.android.bestnotes.db.DAO.BestNotesTextNoteDao;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * @author shuo
 * @date 2013-1-11 下午2:19:22
 * @version V1.0
 */
public class BestNotesAddLocationActivity extends BestNotesBaseActivity
		implements OnMapClickListener, OnMarkerDragListener{

	private GoogleMap mMap;
	private Marker mMarker;
	private String title = "";

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.bestnotes_activity_add_location);
		getSupportActionBar().setTitle(getResources().getString(R.string.set_position));
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		Bundle bundle = getIntent().getExtras();
		if(bundle != null){
			title = bundle.getString(Constants.BundleKey.ADD_LOCATION_TITLE);
		}
		setUpMapIfNeeded();
	}

	private void setUpMapIfNeeded() {
		// Do a null check to confirm that we have not already instantiated the
		// map.
		if (mMap == null) {
			mMap = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map)).getMap();
			// Check if we were successful in obtaining the map.
			if (mMap != null) {
				// The Map is verified. It is now safe to manipulate the map.
				setUpMap();
			}
		}
	}

	private void setUpMap() {
		mMap.setMyLocationEnabled(true);
		mMap.setOnMarkerDragListener(this);
		mMap.setOnMapClickListener(this);
		
		new AsyncTask<Void, Void, Void>() {
			LBSTool lbs;
			LocationData location;
			@Override
			protected Void doInBackground(Void... params) {
				lbs = new LBSTool(BestNotesAddLocationActivity.this);
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
	public void onMapClick(final LatLng arg0) {
		if (mMarker != null) {
			mMarker.remove();
		}

		if (TextUtils.isEmpty(title)) {
			title = getResources().getString(R.string.untitled_notes);
		}
		mMarker = mMap.addMarker(new MarkerOptions().position(arg0).title(title)
				.draggable(true));
		
		// /////////////////////////////////////////////弹跳
		final Handler handler = new Handler();
		final long start = SystemClock.uptimeMillis();
		Projection proj = mMap.getProjection();
		Point startPoint = proj.toScreenLocation(arg0);
		startPoint.offset(0, -100);
		final LatLng startLatLng = proj.fromScreenLocation(startPoint);
		final long duration = 1500;

		final Interpolator interpolator = new BounceInterpolator();

		handler.post(new Runnable() {
			@Override
			public void run() {
				long elapsed = SystemClock.uptimeMillis() - start;
				float t = interpolator.getInterpolation((float) elapsed
						/ duration);
				double lng = t * arg0.longitude + (1 - t)
						* startLatLng.longitude;
				double lat = t * arg0.latitude + (1 - t) * startLatLng.latitude;
				mMarker.setPosition(new LatLng(lat, lng));

				if (t < 1.0) {
					// Post again 16ms later.
					handler.postDelayed(this, 16);
				}
			}
		});
		// /////////////////////////////////////////////
	}

	// ///////////////////////////////////////////////标记拖动事件
	@Override
	public void onMarkerDrag(Marker arg0) {
		 mMarker = arg0;
	}
	
	@Override
	public void onMarkerDragEnd(Marker arg0) {
		 mMarker = arg0;
	}
	
	@Override
	public void onMarkerDragStart(Marker arg0) {
		 mMarker = arg0;
	}

	// ////////////////////////////////////////////////////////
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.activity_add_location, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			this.finish();
			break;
		case R.id.menu_done:
			//TODO 返回具体的位置信息，而不只是是经纬度
			String result = mMarker.getPosition().latitude + ", " +mMarker.getPosition().longitude;
			Bundle bundle = new Bundle();
			bundle.putString(Constants.BundleKey.ADD_LOCATION_RESULT_STRING, result);
			bundle.putDouble(Constants.BundleKey.ADD_LOCATION_RESULT_LATITUDE, mMarker.getPosition().latitude);
			bundle.putDouble(Constants.BundleKey.ADD_LOCATION_RESULT_LONGITUDE, mMarker.getPosition().longitude);
			Intent intent = new Intent();
			intent.putExtras(bundle);
			setResult(RESULT_OK, intent);
			this.finish();
			break;
		}
		return true;
	}
	
}
