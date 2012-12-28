package com.fanshuo.android.bestnotes.app.fragments;

import java.util.List;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockListFragment;
import com.fanshuo.android.bestnotes.R;
import com.fanshuo.android.bestnotes.app.adapters.SlideLeftFragmentAdapter;
import com.fanshuo.android.bestnotes.app.model.BestNotesTextNoteModel;
import com.fanshuo.android.bestnotes.db.DAO.BestNotesTextNoteDao;

/**
 * @author fanshuo
 * @date 2012-12-23
 */
public class SlideLeftFragment extends SherlockListFragment{

	ViewPager pager;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_slide_left, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		SlideLeftFragmentAdapter adapter = new SlideLeftFragmentAdapter(
				getActivity());
		BestNotesTextNoteDao dao = new BestNotesTextNoteDao(getActivity());
		List<BestNotesTextNoteModel> list = dao.getAllNotes(
				BestNotesTextNoteModel.CREATE_TIME, true);
		for (BestNotesTextNoteModel item : list) {
			adapter.add(item.getCreateTime());
		}
		setListAdapter(adapter);
	}
}
