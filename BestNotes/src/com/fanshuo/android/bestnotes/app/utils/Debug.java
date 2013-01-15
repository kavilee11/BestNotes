package com.fanshuo.android.bestnotes.app.utils;

import org.apache.http.client.ClientProtocolException;

import android.util.Log;

public class Debug {
	public static final String TAG = "BestNotes";

	public static void d(String msg) {
		Log.d(TAG, msg);
	}
	public static void e(String msg, Throwable tr){
		Log.e(TAG, msg, tr);
	}
}
