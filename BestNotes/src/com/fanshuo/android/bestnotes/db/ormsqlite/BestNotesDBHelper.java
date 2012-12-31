package com.fanshuo.android.bestnotes.db.ormsqlite;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.fanshuo.android.bestnotes.app.model.BestNotesDeletedTextNoteModel;
import com.fanshuo.android.bestnotes.app.model.BestNotesOperationModel;
import com.fanshuo.android.bestnotes.app.model.BestNotesTextNoteModel;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

/**
 * @author fanshuo
 * @date 2012-12-25 上午9:40:33
 * @version V1.0
 */
public class BestNotesDBHelper extends OrmLiteSqliteOpenHelper {

	private static final String DATABASE_NAME = "BestNotes.db";
	private static final int DATABASE_VERSION = 1;

	public BestNotesDBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase arg0, ConnectionSource arg1) {
		try {
			TableUtils.createTable(connectionSource,
					BestNotesTextNoteModel.class);
			TableUtils.createTable(connectionSource,
					BestNotesOperationModel.class);
			TableUtils.createTable(connectionSource,
					BestNotesDeletedTextNoteModel.class);
		} catch (SQLException e) {
			Log.e(BestNotesDBHelper.class.getName(), "创建数据库失败", e);
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, ConnectionSource arg1, int arg2,
			int arg3) {
		try {
			TableUtils.dropTable(connectionSource,
					BestNotesTextNoteModel.class, true);
			TableUtils.dropTable(connectionSource,
					BestNotesOperationModel.class, true);
			TableUtils.dropTable(connectionSource,
					BestNotesDeletedTextNoteModel.class, true);
			onCreate(arg0, connectionSource);
		} catch (SQLException e) {
			Log.e(BestNotesDBHelper.class.getName(), "更新数据库失败", e);
			e.printStackTrace();
		}
	}

}
