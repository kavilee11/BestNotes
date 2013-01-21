package com.fanshuo.android.bestnotes;
/**
 * @author fanshuo
 * @date 2012-12-25 下午5:21:54
 * @version V1.0
 */
public class Constants {

	public class BundleKey{
		public static final String NOTE_ID = "bestnotes.NOTE_ID";
		public static final String CLICKED_POSITION = "bestnotes.CLICKED_POSITION";
		public static final String ADD_LOCATION_TITLE = "bestnotes.ADD_LOCATION_TITLE";
		public static final String ADD_LOCATION_RESULT_STRING = "bestnotes.ADD_LOCATION_RESULT_STRING";
		public static final String ADD_LOCATION_RESULT_LATITUDE = "bestnotes.ADD_LOCATION_RESULT_LATITUDE";
		public static final String ADD_LOCATION_RESULT_LONGITUDE = "bestnotes.ADD_LOCATION_RESULT_LONGITUDE";
		public static final String NOTE_TITLE = "bestnotes.NOTE_TITLE";
		public static final String CHECK_UPDATE_LINK = "bestnotes.CHECK_UPDATE_LINK";
		public static final String CHECK_UPDATE_VERSION_INFO = "bestnotes.CHECK_UPDATE_VERSION_INFO";
		public static final String NOTE_CONTENT = "bestnotes.NOTE_CONTENT";
		public static final String NOTE_LOCATION = "bestnotes.NOTE_LOCATION";
	}
	
	public class Operations{
		public static final int ADD_NOTE = 100;
		public static final int UPDATE_NOTE = 101;
		public static final int DELETE_NOTE = 102;
		public static final int SEARCH_NOTE = 103;
		public static final int FIRST_START = 104;//首次进入程序
		public static final int READ_NOTE = 105;//读了某一条笔记
		//TODO 每条笔记的阅读次数，修改次数，做个排行
	}
	
	public class RequestCodes{
		public static final int REQUEST_CODE_ADD_POSITION = 1000;//增加笔记位置，进入AddLocationActivity时用
	}
	
	public class ResultCodes{
		public static final int RESULT_CODE_ADD_POSITION = 2000;//从AddLocationActivity返回时用
	}
	
	public class API_URLS{
		public static final String API_CHECK_UPDATE = "http://froid.sinaapp.com/bestnotes.php";//检查版本更新
	}
	
	public class MSG_WHAT{
		public static final int WHAT_REFRESH_LISTVIEW = 3000;
	}
	
	public class UMENG_EVENT{
		public static final String DONATE = "Donate";
		public static final String VERSION = "Version";
	}
	
}
