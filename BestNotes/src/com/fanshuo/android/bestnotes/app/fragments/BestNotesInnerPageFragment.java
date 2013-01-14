package com.fanshuo.android.bestnotes.app.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.fanshuo.android.bestnotes.Constants;
import com.fanshuo.android.bestnotes.R;
import com.fanshuo.android.bestnotes.app.model.BestNotesTextNoteModel;
import com.fanshuo.android.bestnotes.db.DAO.BestNotesTextNoteDao;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * @author fanshuo
 * @date 2012-12-25 下午5:16:28
 * @version V1.0
 */
public class BestNotesInnerPageFragment extends BestNotesBaseFragment {
	private int noteID;
	private BestNotesTextNoteDao dao = null;
	private WebView webView;
	private GoogleMap mMap;
	private Marker mMaker;
	private LatLng mNoteLoc;
	private BestNotesTextNoteModel note;

	public static BestNotesInnerPageFragment getInstance(int noteId) {
		BestNotesInnerPageFragment fragment = new BestNotesInnerPageFragment();
		Bundle bundle = new Bundle();
		bundle.putInt(Constants.BundleKey.NOTE_ID, noteId);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		noteID = getArguments().getInt(Constants.BundleKey.NOTE_ID);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		dao = new BestNotesTextNoteDao(getActivity());
		note = dao.getTextNoteById(noteID);
		String newContent = (note.getContent()).replace("\n", "<br>");// 把\n换成html中的换行符
		note.setContent(newContent);
		mNoteLoc = new LatLng(note.getLatitude(), note.getLongtitude());
		View view = inflater.inflate(R.layout.bestnotes_fragment_inner_page,
				null);
		webView = (WebView) view.findViewById(R.id.webview);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setSupportZoom(true);
		webView.getSettings().setBuiltInZoomControls(false);
		webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		webView.getSettings().setPluginsEnabled(true);
		webView.addJavascriptInterface(note, "note");
		webView.loadUrl("file:///android_asset/view_note.html");

		return view;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		webView.destroy();
	}

}
