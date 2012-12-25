package com.fanshuo.android.bestnotes.db.DAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.fanshuo.android.bestnotes.app.model.BestNotesTextNoteModel;
import com.fanshuo.android.bestnotes.db.ormsqlite.BestNotesDBHelper;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

/**
 * @author fanshuo
 * @date 2012-12-25 下午2:41:41
 * @version V1.0
 */
public class BestNotesTextNoteDao {

	private Dao<BestNotesTextNoteModel, Integer> dao = null;
	private Context context=null;  
	BestNotesDBHelper helper=null;
	public BestNotesTextNoteDao(Context context) {
		super();
		this.context = context;
		helper=OpenHelperManager.getHelper(context,BestNotesDBHelper.class);
		try {
			dao = helper.getDao(BestNotesTextNoteModel.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 获取所有笔记
	 * @param orderByFiledName 排序所依据的字段
	 * @param ascending	是否升序排列
	 * @return
	 */
	public List<BestNotesTextNoteModel> getAllNotes(String orderByFiledName , boolean ascending){
		List<BestNotesTextNoteModel> list = new ArrayList<BestNotesTextNoteModel>();
		try {
			QueryBuilder<BestNotesTextNoteModel, Integer> builder = dao.queryBuilder();
			PreparedQuery<BestNotesTextNoteModel> prepareQuery = builder.orderBy(orderByFiledName, ascending).prepare();
			list = dao.query(prepareQuery);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 插入一个笔记
	 * @param note
	 * @return 成功返回1;   异常返回-1
	 */
	public int addNote(BestNotesTextNoteModel note){
		try {
			return dao.create(note);
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	/**
	 * 删除一个笔记
	 * @param note
	 * @return
	 */
	public int deleteNotes(BestNotesTextNoteModel note){
		try {
			return dao.delete(note);
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
