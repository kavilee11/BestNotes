package com.fanshuo.android.bestnotes.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanshuo.android.bestnotes.R;
import com.fanshuo.android.bestnotes.app.model.SlideLeftListItem;

/**
 * @author fanshuo
 * @date 2012-12-23
 */
public class SlideLeftFragmentAdapter extends ArrayAdapter<SlideLeftListItem>{
	
	public SlideLeftFragmentAdapter(Context context) {
		super(context, 0);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_slide_left_list, null);
		}
		ImageView icon = (ImageView) convertView.findViewById(R.id.row_icon);
		icon.setImageResource(getItem(position).iconRes);
		TextView title = (TextView) convertView.findViewById(R.id.row_title);
		title.setText(getItem(position).tag);

		return convertView;
	}
	
}
