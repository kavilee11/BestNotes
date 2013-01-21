package com.fanshuo.android.bestnotes.app.fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

import com.fanshuo.android.bestnotes.Constants;
import com.fanshuo.android.bestnotes.R;
import com.fanshuo.android.bestnotes.app.activities.BestNotesSingleNoteActivity;
import com.fanshuo.android.bestnotes.app.adapters.SlideLeftExpandableListAdapter;
import com.fanshuo.android.bestnotes.app.model.BestNotesTextNoteModel;
import com.fanshuo.android.bestnotes.db.DAO.BestNotesTextNoteDao;

/**
 * @author fanshuo
 * @date 2012-12-28 下午11:31:46
 * @version V1.0
 */
public class SlideLeftListFragment extends BestNotesBaseFragment implements OnChildClickListener{

	ExpandableListView listView;
	public static Handler handler;
	SlideLeftExpandableListAdapter listAdapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initHandler();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_slide_left_list, null);
		listView = (ExpandableListView) view.findViewById(android.R.id.list);
		listView.setEmptyView(view.findViewById(android.R.id.empty));
		return view;
	}

	private void initData(){
		BestNotesTextNoteDao dao = new BestNotesTextNoteDao(getActivity());
		List<BestNotesTextNoteModel> list = dao.getAllNotes(
				BestNotesTextNoteModel.CREATE_TIME, true);
		
		List<Map<String, String>> monthList = new ArrayList<Map<String, String>>();
		for (BestNotesTextNoteModel item : list) {
			String month = item.getYear() + "-" + item.getMonth();
			Map<String, String> group = new HashMap<String, String>();
			group.put("group", month);
			if (!monthList.contains(group)) {
				monthList.add(group);
			}
		}
		List<List<Map<String, BestNotesTextNoteModel>>> childs = new ArrayList<List<Map<String, BestNotesTextNoteModel>>>();
		List<Map<String, BestNotesTextNoteModel>> child;
		for (Map<String, String> map : monthList) {
			String month = map.get("group");
			child = new ArrayList<Map<String, BestNotesTextNoteModel>>();
			for (BestNotesTextNoteModel item : list) {
				if (month.equals(item.getYear() + "-" + item.getMonth())) {
					Map<String, BestNotesTextNoteModel> child1Map = new HashMap<String, BestNotesTextNoteModel>();
					child1Map.put("child", item);
					child.add(child1Map);
				}
			}
			childs.add(child);
		}
		listAdapter = new SlideLeftExpandableListAdapter(
				getActivity(), monthList, R.layout.item_slide_left_list,new String[] { "group" },
				new int[] { R.id.row_title },childs, R.layout.item_slide_left_list_child,
				new String[] { "child" }, new int[] { R.id.row_title });
		listView.setAdapter(listAdapter);
		listView.setOnChildClickListener(this);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initData();
	}
	
	@Override
	public void onResume() {
		super.onResume();
	}
	
	private void initHandler(){
		handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case Constants.MSG_WHAT.WHAT_REFRESH_LISTVIEW:
					initData();
					break;
				}
			}
			
		};
	}

	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {
		Bundle bundle = new Bundle();
		BestNotesTextNoteModel note = (BestNotesTextNoteModel)(((Map<String, BestNotesTextNoteModel>)(listAdapter.getChild(groupPosition, childPosition))).get("child"));
		bundle.putInt(Constants.BundleKey.NOTE_ID, note.get_id());
		bundle.putString(Constants.BundleKey.NOTE_TITLE, note.getTitle());
		startActivity(BestNotesSingleNoteActivity.class, bundle);
		return false;
	}
	
}
