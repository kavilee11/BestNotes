package com.fanshuo.android.bestnotes.app.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

/**
 * @author shuo
 * @date 2013-1-14 下午6:02:57
 * @version V1.0
 */
public class APPUtil {

	public static int getVersionCode(Context context) {
		PackageManager packageManager = context.getPackageManager();
		PackageInfo packInfo;
		int version = 0;
		try {
			packInfo = packageManager.getPackageInfo(context.getPackageName(),
					0);
			version = packInfo.versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return version;
	}
	
	public static String getVersionName(Context context) {
		PackageManager packageManager = context.getPackageManager();
		PackageInfo packInfo;
		String version = "";
		try {
			packInfo = packageManager.getPackageInfo(context.getPackageName(),
					0);
			version = packInfo.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return version;
	}

}
