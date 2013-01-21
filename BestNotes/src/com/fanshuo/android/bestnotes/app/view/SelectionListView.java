package com.fanshuo.android.bestnotes.app.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.SparseBooleanArray;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.fanshuo.android.bestnotes.Constants;
import com.fanshuo.android.bestnotes.MainActivity;
import com.fanshuo.android.bestnotes.R;
import com.fanshuo.android.bestnotes.app.adapters.BestNotesTextNoteAdapter;
import com.fanshuo.android.bestnotes.app.fragments.SlideLeftListFragment;
import com.fanshuo.android.bestnotes.app.fragments.dialog.BestNotesConfirmDeleteDialogFragment;
import com.fanshuo.android.bestnotes.app.model.BestNotesTextNoteModel;
import com.fanshuo.android.bestnotes.app.utils.ActivityUtil;
import com.fanshuo.android.bestnotes.db.DAO.BestNotesTextNoteDao;

public class SelectionListView extends ListView{
	private SherlockFragmentActivity mActivity;
	ActionMode mActionMode;
	private boolean inSelectionMode = false;

	public SelectionListView(Context context) {
		super(context);
		setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		mActivity = (SherlockFragmentActivity) context;
		init();
	}

	public SelectionListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		mActivity = (SherlockFragmentActivity) context;
		init();
	}

	public SelectionListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		mActivity = (SherlockFragmentActivity) context;
		init();
	}
	
	private void init(){
		setOnItemLongClickListener(new LongClickListener());
	}

	
	
	@Override
	public boolean performItemClick(View view, int position, long id) {
		OnItemClickListener mOnItemClickListener = getOnItemClickListener();
		if (mOnItemClickListener != null) {
			playSoundEffect(SoundEffectConstants.CLICK);
			if (view != null)
				view.sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_CLICKED);
			if(inSelectionMode){
				setItemChecked(position, !isItemChecked(position));
			}else{
				mOnItemClickListener.onItemClick(this, view, position, id);
			}
			return true;
		}
		return false;
	}

	/*boolean mSelectionMode = false;
	int startX,startY;
	@Override
	public boolean onTouchEvent(MotionEvent ev) {

		final int action = ev.getAction();
		final int x = (int) ev.getX();
		final int y = (int) ev.getY();

		if (action == MotionEvent.ACTION_DOWN && x < getWidth() / 7) {
			mSelectionMode = true;
			startX = x;
			startY = y;
		}
		if (!mSelectionMode)
			return super.onTouchEvent(ev);
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			break;
		case MotionEvent.ACTION_MOVE:
			if(x != startX && y != startY)
				mSelectionMode = false;
			return super.onTouchEvent(ev);
		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_UP:
		default:
			mSelectionMode = false;
			int mItemPosition = pointToPosition(x, y);
			if(mItemPosition != ListView.INVALID_POSITION)
				setItemChecked(mItemPosition, !isItemChecked(mItemPosition));
		}

		return true;
	}*/

	@Override
	public void setItemChecked(int position, boolean value) {
		super.setItemChecked(position, value);
		int checkedCount = getCheckItemIds().length;

		if (checkedCount == 0) {
			if (mActionMode != null)
				mActionMode.finish();
			return;
		}
		if (mActionMode == null)
			mActionMode = mActivity.startActionMode(new ModeCallback());

		mActionMode.setTitle(mActivity.getResources().getString(R.string.selected_num, checkedCount));

	}

	class ModeCallback implements ActionMode.Callback {
		
		@Override
		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
			mActivity.getSupportMenuInflater().inflate(R.menu.activity_main_context, menu);
			inSelectionMode = true;
			return true;
		}

		@Override
		public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
			return true;
		}

		@Override
		public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
			switch (item.getItemId()) {
			case R.id.menu_delete:
				//这里需要在callBack之前获取已选择的条目，貌似dialog出现再消失，选择的条目就不在了，选择条目数目会变成0
				final BestNotesTextNoteDao dao = new BestNotesTextNoteDao(mActivity);
				final List<BestNotesTextNoteModel> toBeDelete = new ArrayList<BestNotesTextNoteModel>();
				int total = getCount();
				for (int i = 0; i < total; i++) {
					if(isItemChecked(i)){
						toBeDelete.add((BestNotesTextNoteModel)getItemAtPosition(i));
					}
				}
				BestNotesConfirmDeleteDialogFragment.ConfirmDeleteDialogCallBack callBack = new BestNotesConfirmDeleteDialogFragment.ConfirmDeleteDialogCallBack() {
					@Override
					public void onPositiveButtonClick() {
						dao.deleteNotes(toBeDelete, true);
						for (BestNotesTextNoteModel bestNotesTextNoteModel : toBeDelete) {
							((BestNotesTextNoteAdapter)getAdapter()).remove(bestNotesTextNoteModel);
						}
						((BestNotesTextNoteAdapter)getAdapter()).notifyDataSetChanged();
						MainActivity.handler.sendEmptyMessage(Constants.MSG_WHAT.WHAT_REFRESH_LISTVIEW);
						SlideLeftListFragment.handler.sendEmptyMessage(Constants.MSG_WHAT.WHAT_REFRESH_LISTVIEW);
						ActivityUtil.showCenterShortToast(mActivity, mActivity.getResources().getString(R.string.delete_success));
					}
				};
				BestNotesConfirmDeleteDialogFragment dialog = new BestNotesConfirmDeleteDialogFragment(callBack);
				dialog.show(mActivity.getSupportFragmentManager(), "");
				break;
			}
			mode.finish();
			return true;
		}

		@Override
		public void onDestroyActionMode(ActionMode mode) {
			inSelectionMode = false;
			mActionMode = null;
			clearChecked();
		}

	}

	public void clearChecked() {
		SparseBooleanArray CItem = getCheckedItemPositions();
		for (int i = 0; i < CItem.size(); i++)
			if (CItem.valueAt(i))
				super.setItemChecked(CItem.keyAt(i), false);
	}
	
	class LongClickListener implements OnItemLongClickListener{

		@Override
		public boolean onItemLongClick(AdapterView<?> parent, View view,
				int position, long id) {
			setItemChecked(position, !isItemChecked(position));
			return true;
		}
		
	}

}
