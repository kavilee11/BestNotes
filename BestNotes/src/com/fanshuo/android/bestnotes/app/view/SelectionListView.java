package com.fanshuo.android.bestnotes.app.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseBooleanArray;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.fanshuo.android.bestnotes.R;

public class SelectionListView extends ListView {
	private SherlockFragmentActivity mActivity;
	ActionMode mActionMode;

	public SelectionListView(Context context) {
		super(context);
		setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		mActivity = (SherlockFragmentActivity) context;
	}

	public SelectionListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		mActivity = (SherlockFragmentActivity) context;
	}

	public SelectionListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		mActivity = (SherlockFragmentActivity) context;
	}

	@Override
	public boolean performItemClick(View view, int position, long id) {
		OnItemClickListener mOnItemClickListener = getOnItemClickListener();
		if (mOnItemClickListener != null) {
			playSoundEffect(SoundEffectConstants.CLICK);
			if (view != null)
				view.sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_CLICKED);
			mOnItemClickListener.onItemClick(this, view, position, id);
			return true;
		}
		return false;
	}

	boolean mSelectionMode = false;
	int mStartPosition;

	@Override
	public boolean onTouchEvent(MotionEvent ev) {

		final int action = ev.getAction();
		final int x = (int) ev.getX();
		final int y = (int) ev.getY();

		if (action == MotionEvent.ACTION_DOWN && x < getWidth() / 7) {
			mSelectionMode = true;
			mStartPosition = pointToPosition(x, y);
		}
		if (!mSelectionMode)
			return super.onTouchEvent(ev);
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			break;
		case MotionEvent.ACTION_MOVE:
			if (pointToPosition(x, y) != mStartPosition)
				mSelectionMode = false;
			break;
		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_UP:
		default:
			mSelectionMode = false;
			int mItemPosition = pointToPosition(x, y);
			if (mStartPosition != ListView.INVALID_POSITION)
				setItemChecked(mItemPosition, !isItemChecked(mItemPosition));
		}

		return true;
	}

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

		mActionMode.setTitle(checkedCount + " selected");

	}

	class ModeCallback implements ActionMode.Callback {

		@Override
		public boolean onCreateActionMode(ActionMode mode, Menu menu) {

			menu.add(getResources().getString(R.string.menu_search))
					.setIcon(R.drawable.ic_menu_search)
					.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

			return true;
		}

		@Override
		public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
			return true;
		}

		@Override
		public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
			Toast.makeText(mActivity, "Delted  items", Toast.LENGTH_SHORT)
					.show();
			mode.finish();
			return true;
		}

		@Override
		public void onDestroyActionMode(ActionMode mode) {
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

}
