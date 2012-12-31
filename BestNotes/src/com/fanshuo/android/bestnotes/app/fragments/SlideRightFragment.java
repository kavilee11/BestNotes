package com.fanshuo.android.bestnotes.app.fragments;

import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockListFragment;
import com.fanshuo.android.bestnotes.R;
import com.fanshuo.android.bestnotes.app.adapters.SlideRightFragmentAdapter;
import com.fanshuo.android.bestnotes.app.model.BestNotesOperationModel;
import com.fanshuo.android.bestnotes.db.DAO.BestNotesOperationDao;

/**
 * @author fanshuo
 * @date 2012-12-23
 */
public class SlideRightFragment extends SherlockListFragment {

	BestNotesOperationDao dao = null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_slide_right, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		SlideRightFragmentAdapter adapter = new SlideRightFragmentAdapter(
				getActivity());
		BestNotesOperationDao dao = new BestNotesOperationDao(getActivity());
		List<BestNotesOperationModel> list = dao.getAll(BestNotesOperationModel.CREATE_TIME, false);
		for (BestNotesOperationModel item : list) {
			adapter.add(item);
		}
		setListAdapter(adapter);
	}
}
