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
import com.fanshuo.android.bestnotes.app.model.BestNotesTextNoteModel;
import com.fanshuo.android.bestnotes.app.utils.BestNotesDateUtil;

public class BestNotesTextNoteAdapter extends ArrayAdapter<BestNotesTextNoteModel>{

	public BestNotesTextNoteAdapter(Context context) {
		super(context, 0);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.bestnotes_item_mainlist, null);
		}
//		CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);
//        checkBox.setChecked(((ListView)parent).isItemChecked(position));
        TextView title = (TextView) convertView.findViewById(R.id.textview_title);
        TextView time = (TextView) convertView.findViewById(R.id.textview_time);
        TextView content = (TextView) convertView.findViewById(R.id.textview_content);
        title.setText(getItem(position).getTitle());
        String timeStr= BestNotesDateUtil.formatTimeStampString(getContext(), getItem(position).getCreateTime());
        time.setText(timeStr);
        content.setText(getItem(position).getContent());
		return convertView;
	}
	
	

}
