package com.fanshuo.android.bestnotes.db.DAO;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;

import com.fanshuo.android.bestnotes.app.model.BestNotesDeletedTextNoteModel;
import com.fanshuo.android.bestnotes.db.ormsqlite.BestNotesDBHelper;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

/**
 * @author fanshuo
 * @date 2012-12-25 下午2:41:41
 * @version V1.0
 */
public class BestNotesDeletedTextNoteDao {

	private Dao<BestNotesDeletedTextNoteModel, Integer> dao = null;
	private Context context=null;  
	BestNotesDBHelper helper=null;
	public BestNotesDeletedTextNoteDao(Context context) {
		super();
		this.context = context;
		helper=OpenHelperManager.getHelper(context,BestNotesDBHelper.class);
		try {
			dao = helper.getDao(BestNotesDeletedTextNoteModel.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据ID查询笔记
	 * @param id
	 * @return
	 */
	public BestNotesDeletedTextNoteModel getTextNoteByNoteId(int id){
		BestNotesDeletedTextNoteModel note = null;
		try {
			Map<String, Integer> map = new HashMap<String, Integer>();
			map.put(BestNotesDeletedTextNoteModel.NOTE_ID, id);
			note = dao.queryForFieldValuesArgs(map);
		} catch (SQLException e) {
			e.printStackTrace(map);
		}
		return note;
	}
	
	
	/**
	 * 插入一个已删除的笔记
	 * @param note
	 * @return 成功返回1;   异常返回-1
	 */
	public int addNote(BestNotesDeletedTextNoteModel note){
		int ret= -1;
		try {
			ret = dao.create(note);
			return ret;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	/**
	 * 删除笔记
	 * @param note
	 * @return
	 */
	public int deleteNotes(List<BestNotesDeletedTextNoteModel> toBeDelete){
		try {
			return dao.delete(toBeDelete);
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	
	
	@Override
	protected void finalize() throws Throwable {
		OpenHelperManager.releaseHelper();
		helper = null;
		super.finalize();
	}  
}
