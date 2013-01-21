package com.fanshuo.android.bestnotes.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.fanshuo.android.bestnotes.R;
import com.fanshuo.android.bestnotes.RuntimeObject;
import com.fanshuo.android.bestnotes.app.model.BestNotesTextNoteModel;
import com.fanshuo.android.bestnotes.app.model.DistanceData;
import com.fanshuo.android.bestnotes.app.utils.BestNotesDateUtil;
import com.fanshuo.android.bestnotes.app.utils.WebServiceAPIUtil;

public class BestNotesTextNoteAdapterByLocation extends ArrayAdapter<BestNotesTextNoteModel>{

	public BestNotesTextNoteAdapterByLocation(Context context) {
		super(context, 0);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.bestnotes_item_mainlist, null);
		}
        TextView title = (TextView) convertView.findViewById(R.id.textview_title);
        TextView time = (TextView) convertView.findViewById(R.id.textview_time);
        TextView content = (TextView) convertView.findViewById(R.id.textview_content);
        title.setText(getItem(position).getTitle());
        //显示笔记和当前位置的距离
        time.setText(getItem(position).getDistance());
        content.setText(getItem(position).getContent());
		return convertView;
	}
	
	

}
