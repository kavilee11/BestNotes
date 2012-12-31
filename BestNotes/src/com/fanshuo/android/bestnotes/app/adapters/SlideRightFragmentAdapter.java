package com.fanshuo.android.bestnotes.app.adapters;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.fanshuo.android.bestnotes.Constants;
import com.fanshuo.android.bestnotes.R;
import com.fanshuo.android.bestnotes.app.model.BestNotesOperationModel;
import com.fanshuo.android.bestnotes.db.DAO.BestNotesDeletedTextNoteDao;
import com.fanshuo.android.bestnotes.db.DAO.BestNotesTextNoteDao;

/**
 * @author fanshuo
 * @date 2012-12-23
 */
public class SlideRightFragmentAdapter extends ArrayAdapter<BestNotesOperationModel>{
	
	BestNotesTextNoteDao dao = null;
	BestNotesDeletedTextNoteDao dDao = null;
	
	public SlideRightFragmentAdapter(Context context) {
		super(context, 0);
		dao = new BestNotesTextNoteDao(context);
		dDao = new BestNotesDeletedTextNoteDao(context);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_slide_right_list, null);
		}
		BestNotesOperationModel op = getItem(position);
		TextView tv_title = (TextView) convertView.findViewById(R.id.row_title);
		TextView tv_time = (TextView) convertView.findViewById(R.id.row_time);
		String noteTitle = null;
		switch (op.getOperation()) {
		case Constants.Operations.DELETE_NOTE:
			noteTitle = dDao.getTextNoteByNoteId(op.get_id()).getTitle();
			noteTitle = "删除了笔记：" + noteTitle;
			break;
		case Constants.Operations.ADD_NOTE:
			noteTitle = (dao.getTextNoteById(op.getNoteId(), false).getTitle());
			noteTitle = "创建了笔记：" + noteTitle;
			break;
		case Constants.Operations.READ_NOTE:
			noteTitle = (dao.getTextNoteById(op.getNoteId(), false).getTitle());
			noteTitle = "查看了笔记：" + noteTitle;
			break;
		}
//		String time = (String) DateUtils.getRelativeTimeSpanString(op.getCreateTime(), System.currentTimeMillis(), 0);
		String time = (String) DateUtils.getRelativeDateTimeString(getContext(), op.getCreateTime(), 0, DateUtils.WEEK_IN_MILLIS, DateUtils.FORMAT_SHOW_YEAR);
		tv_time.setText(time);
		tv_title.setText(noteTitle);
		return convertView;
	}
	
}
