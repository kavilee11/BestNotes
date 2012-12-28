package com.fanshuo.android.bestnotes.app.adapters;

import java.util.Calendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.fanshuo.android.bestnotes.R;

/**
 * @author fanshuo
 * @date 2012-12-23
 */
public class SlideLeftFragmentAdapter extends ArrayAdapter<Long>{
	
	public SlideLeftFragmentAdapter(Context context) {
		super(context, 0);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_slide_left_list, null);
		}
		Calendar date = Calendar.getInstance();
		date.setTimeInMillis(getItem(position));
		String time = date.get(Calendar.YEAR) + "-" + (date.get(Calendar.MONTH)+1);
		TextView title = (TextView) convertView.findViewById(R.id.row_title);
		TextView num = (TextView) convertView.findViewById(R.id.row_num);
		title.setText(time);
		num.setText("1");
		return convertView;
	}
	
}
