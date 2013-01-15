package com.fanshuo.android.bestnotes.app.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.fanshuo.android.bestnotes.R;

/**
 * @author fanshuo
 * @date 2012-12-25 上午11:36:32
 * @version V1.0
 */
public class ActivityUtil {

	private static Toast toast;
	
	
	/**
	 * 显示一个居中的短时间Toast
	 * @param msg
	 */
	public static void showCenterShortToast(Context context, CharSequence text){
		if(toast != null){
			toast.cancel();
		}
		toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}
	
	/**
	 * 重载。显示一个居中的短时间Toast
	 * @param context
	 * @param resId
	 */
	public static void showCenterShortToast(Context context, int resId){
		if(toast != null){
			toast.cancel();
		}
		toast = Toast.makeText(context, context.getResources().getString(resId), Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}
	
	/**
	 * 抖动一个view，幅度是5pix
	 * @param context
	 * @param view
	 */
	public static void shakeView5Pix(Context context, View view){
		Animation anim = AnimationUtils.loadAnimation(context, R.anim.shake_5);
		view.startAnimation(anim);
	}
	
}
