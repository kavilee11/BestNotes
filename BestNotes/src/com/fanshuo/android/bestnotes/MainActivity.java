package com.fanshuo.android.bestnotes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.ActionMode.Callback;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.ShareActionProvider;
import com.fanshuo.android.bestnotes.app.adapters.SlideLeftFragmentAdapter;
import com.fanshuo.android.bestnotes.app.fragments.SlideLeftFragment;
import com.fanshuo.android.bestnotes.app.model.SlideLeftListItem;
import com.fanshuo.android.bestnotes.app.view.SelectionListView;
import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingFragmentActivity;

public class MainActivity extends SlidingFragmentActivity implements ActionBar.OnNavigationListener{

	protected ListFragment leftFrag, rightFrag;
	private ShareActionProvider mShareActionProvider;
	private SelectionListView lv;
	ActionMode.Callback mCallback;
    ActionMode mMode;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setBehindContentView(R.layout.fram_slide_left);
		SlidingMenu sm = getSlidingMenu();
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeDegree(0.35f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		sm.setMode(SlidingMenu.LEFT_RIGHT);
		sm.setSecondaryMenu(R.layout.fram_slide_right);
		sm.setSecondaryShadowDrawable(R.drawable.shadowright);
		getSlidingMenu().setBehindScrollScale(0);
		
		FragmentTransaction t = this.getSupportFragmentManager().beginTransaction();
		leftFrag = new SlideLeftFragment();
		t.replace(R.id.frame_left, leftFrag);
		rightFrag = new SlideLeftFragment();
		t.replace(R.id.frame_right, rightFrag);
		t.commit();
		
		getSupportActionBar().setTitle("");
		getSupportActionBar().setHomeButtonEnabled(true);
		//增加下拉列表
		getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		SpinnerAdapter mSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.main_action_spinner, android.R.layout.simple_list_item_1);
		getSupportActionBar().setListNavigationCallbacks(mSpinnerAdapter, this);
		
		///////////////////Test
		lv = (SelectionListView) findViewById(R.id.main_lv);
		SlideLeftFragmentAdapter adapter = new SlideLeftFragmentAdapter(this);
		adapter.add(new SlideLeftListItem("ITEM1", R.drawable.ic_launcher));
		adapter.add(new SlideLeftListItem("ITEM2", R.drawable.ic_launcher));
		adapter.add(new SlideLeftListItem("ITEM3", R.drawable.ic_launcher));
		adapter.add(new SlideLeftListItem("ITEM4", R.drawable.ic_launcher));
		lv.setAdapter(adapter);
		
		mCallback = new Callback() {
			
			@Override
			public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public void onDestroyActionMode(ActionMode mode) {
				mMode = null;
			}
			
			@Override
			public boolean onCreateActionMode(ActionMode mode, Menu menu) {
				mode.setTitle("Demo");
				getSupportMenuInflater().inflate(R.menu.context_menu, menu);
                return true;
			}
			
			@Override
			public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
				switch(item.getItemId()){
                case R.id.action1:
                    Toast.makeText(getBaseContext(), "Selected Action1 ", Toast.LENGTH_LONG).show();
                    mode.finish();    // Automatically exists the action mode, when the user selects this action
                    break;
                case R.id.action2:
                    Toast.makeText(getBaseContext(), "Selected Action2 ", Toast.LENGTH_LONG).show();
                    break;
                case R.id.action3:
                    Toast.makeText(getBaseContext(), "Selected Action3 ", Toast.LENGTH_LONG).show();
                    break;
                }
                return false;
			}
		};
		
//		lv.setOnItemLongClickListener(new OnItemLongClickListener() {
//			@Override
//			public boolean onItemLongClick(AdapterView<?> parent, View view,
//					int position, long id) {
//				System.out.println("-------OnItemLongClickListener");
//				checkedItems = lv.getCheckedItemPositions();
//				if(checkedItems.get(position, false)){
//					lv.setItemChecked(position, false);
//					checkedItems.put(position, false);
//				}
//				else{
//					lv.setItemChecked(position, true);
//					checkedItems.put(position, true);
//				}
//				return true;
//			}
//		});
		
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				System.out.println("-------onItemClick");
			}
		});
		
	}

	private SparseBooleanArray checkedItems = new SparseBooleanArray();
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.activity_main, menu);
		mShareActionProvider = (ShareActionProvider) menu.findItem(R.id.menu_share).getActionProvider();
	    mShareActionProvider.setShareIntent(getDefaultShareIntent());
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		return super.onOptionsItemSelected(item);
	}

	private Intent getDefaultShareIntent() {
		Intent intent = new Intent(Intent.ACTION_SEND); // 启动分享发送的属性
        intent.setType("text/plain"); // 分享发送的数据类型
        intent.putExtra(Intent.EXTRA_SUBJECT, "subject"); // 分享的主题
        intent.putExtra(Intent.EXTRA_TEXT, "extratext"); // 分享的内容
		return intent;
	}

	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		return false;
	}

}
