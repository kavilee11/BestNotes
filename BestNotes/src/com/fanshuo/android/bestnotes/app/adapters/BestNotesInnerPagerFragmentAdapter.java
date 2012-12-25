package com.fanshuo.android.bestnotes.app.adapters;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.fanshuo.android.bestnotes.app.fragments.BestNotesInnerPageFragment;
import com.fanshuo.android.bestnotes.app.model.BestNotesTextNoteModel;

/**
 * @author fanshuo
 * @date 2012-12-25 下午5:42:01
 * @version V1.0
 */
public class BestNotesInnerPagerFragmentAdapter extends FragmentStatePagerAdapter{

	List<BestNotesTextNoteModel> list;
	
	public BestNotesInnerPagerFragmentAdapter(FragmentManager fm, List<BestNotesTextNoteModel> list) {
		super(fm);
		this.list = list;
	}

	@Override
	public Fragment getItem(int arg0) {
		BestNotesTextNoteModel note = list.get(arg0);
		return BestNotesInnerPageFragment.getInstance(note.get_id());
	}

	@Override
	public int getCount() {
		return list.size();
	}

}
