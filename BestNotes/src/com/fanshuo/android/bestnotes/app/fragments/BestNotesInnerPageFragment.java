package com.fanshuo.android.bestnotes.app.fragments;

import com.fanshuo.android.bestnotes.Constants;
import com.fanshuo.android.bestnotes.R;
import com.fanshuo.android.bestnotes.app.model.BestNotesTextNoteModel;
import com.fanshuo.android.bestnotes.db.DAO.BestNotesTextNoteDao;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * @author fanshuo
 * @date 2012-12-25 下午5:16:28
 * @version V1.0
 */
public class BestNotesInnerPageFragment extends BestNotesBaseFragment{
	private int noteID;
	private BestNotesTextNoteDao dao = null;
	
	public static BestNotesInnerPageFragment getInstance(int noteId){
		BestNotesInnerPageFragment fragment = new BestNotesInnerPageFragment();
		Bundle bundle = new Bundle();
		bundle.putInt(Constants.BundleKey.NOTE_ID, noteId);
		fragment.setArguments(bundle);
		return fragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		noteID = getArguments().getInt(Constants.BundleKey.NOTE_ID);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		dao = new BestNotesTextNoteDao(getActivity());
		BestNotesTextNoteModel note = dao.getTextNoteById(noteID);
		View view = inflater.inflate(R.layout.bestnotes_activity_add_note, null);
		EditText et_title = (EditText) view.findViewById(R.id.et_title);
		EditText et_content = (EditText) view.findViewById(R.id.et_content);
		et_title.setText(note.getTitle());
		et_content.setText(note.getContent());
		et_title.setEnabled(false);
		et_content.setEnabled(false);
		return view;
	}

	
	
}
