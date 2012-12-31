package com.fanshuo.android.bestnotes.app.fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.fanshuo.android.bestnotes.R;
import com.fanshuo.android.bestnotes.app.adapters.SlideLeftExpandableListAdapter;
import com.fanshuo.android.bestnotes.app.model.BestNotesTextNoteModel;
import com.fanshuo.android.bestnotes.db.DAO.BestNotesTextNoteDao;

/**
 * @author fanshuo
 * @date 2012-12-28 下午11:31:46
 * @version V1.0
 */
public class SlideLeftListFragment extends BestNotesBaseFragment{

	ExpandableListView listView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_slide_left_list, null);
		listView = (ExpandableListView) view.findViewById(android.R.id.list);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
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
		List<List<Map<String, String>>> childs = new ArrayList<List<Map<String, String>>>();
		List<Map<String, String>> child;
		for (Map<String, String> map : monthList) {
			String month = map.get("group");
			child = new ArrayList<Map<String, String>>();
			for (BestNotesTextNoteModel item : list) {
				if (month.equals(item.getYear() + "-" + item.getMonth())) {
					Map<String, String> child1Map = new HashMap<String, String>();
					child1Map.put("child", item.getTitle());
					child.add(child1Map);
				}
			}
			childs.add(child);
		}
		SlideLeftExpandableListAdapter listAdapter = new SlideLeftExpandableListAdapter(
				getActivity(), monthList, R.layout.item_slide_left_list,new String[] { "group" },
				new int[] { R.id.row_title },childs, R.layout.item_slide_left_list_child,
				new String[] { "child" }, new int[] { R.id.row_title });
		listView.setAdapter(listAdapter);
	}

	
	
}
