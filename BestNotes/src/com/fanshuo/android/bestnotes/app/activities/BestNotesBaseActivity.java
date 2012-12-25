package com.fanshuo.android.bestnotes.app.activities;

import android.os.Bundle;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.fanshuo.android.bestnotes.db.ormsqlite.BestNotesDBHelper;
import com.j256.ormlite.android.apptools.OpenHelperManager;

public class BestNotesBaseActivity extends SherlockFragmentActivity{

	protected BestNotesDBHelper dbHelper = null;
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		/* 
         * 释放资源 
         */  
        if (dbHelper != null) {  
            OpenHelperManager.releaseHelper();  
            dbHelper = null;  
        }  
	}
	
	protected BestNotesDBHelper getDbHelper() {
		if(dbHelper == null){
			dbHelper = OpenHelperManager  
                    .getHelper(this, BestNotesDBHelper.class);
		}
		return dbHelper;
	}

	
}
