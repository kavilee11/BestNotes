package com.fanshuo.android.bestnotes.app.utils;

import android.content.Context;
import android.text.format.DateUtils;
import android.text.format.Time;

/**
 * @author fanshuo
 * @date 2012-12-25 下午1:47:06
 * @version V1.0
 */
public class BestNotesDateUtil {

	public static String formatTimeStampString(Context context, long when) {
        Time then = new Time();
        then.set(when);
        Time now = new Time();
        now.setToNow();

        // Basic settings for formatDateTime() we want for all cases.
        int format_flags = DateUtils.FORMAT_NO_NOON_MIDNIGHT |
                           DateUtils.FORMAT_ABBREV_ALL |
                           DateUtils.FORMAT_CAP_AMPM;

        // If the message is from a different year, show the date and year.
        if (then.year != now.year) {
            format_flags |= DateUtils.FORMAT_SHOW_YEAR | DateUtils.FORMAT_SHOW_DATE;
        } else if (then.yearDay != now.yearDay) {
            // If it is from a different day than today, show only the date.
            format_flags |= DateUtils.FORMAT_SHOW_DATE;
        } else {
            // Otherwise, if the message is from today, show the time.
            format_flags |= DateUtils.FORMAT_SHOW_TIME;
        }

        return DateUtils.formatDateTime(context, when, format_flags);
    }
	
}
