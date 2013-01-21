package com.fanshuo.android.bestnotes.app.adapters;

import java.util.List;
import java.util.Map;

import com.fanshuo.android.bestnotes.R;
import com.fanshuo.android.bestnotes.app.model.BestNotesTextNoteModel;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;

/**
 * @author fanshuo
 * @date 2012-12-28 下午10:34:37
 * @version V1.0
 */
public class SlideLeftExpandableListAdapter extends SimpleExpandableListAdapter {

private Context context;
	
	public SlideLeftExpandableListAdapter(Context context,
			List<? extends Map<String, ?>> groupData, int groupLayout,
			String[] groupFrom, int[] groupTo,
			List<? extends List<? extends Map<String, ?>>> childData,
			int childLayout, String[] childFrom, int[] childTo) {
		super(context, groupData, groupLayout, groupFrom, groupTo, childData,
				childLayout, childFrom, childTo);
		this.context = context;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.item_slide_left_list, null);
		}
		ImageView iv = (ImageView) convertView.findViewById(R.id.expander);
		if(isExpanded){
			iv.setImageResource(R.drawable.expander_open_holo_light);
		}else{
			iv.setImageResource(R.drawable.expander_close_holo_light);
		}
		TextView num = (TextView) convertView.findViewById(R.id.row_num);
		num.setText(getChildrenCount(groupPosition)+"");
		return super.getGroupView(groupPosition, isExpanded, convertView, parent);
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.item_slide_left_list_child, null);
		}
		TextView tv = (TextView) convertView.findViewById(R.id.row_title);
		BestNotesTextNoteModel note = (BestNotesTextNoteModel)(((Map<String, BestNotesTextNoteModel>)(getChild(groupPosition, childPosition))).get("child"));
		tv.setText(note.getTitle());
		getChild(groupPosition, childPosition);
		return convertView;
	}
	
}
