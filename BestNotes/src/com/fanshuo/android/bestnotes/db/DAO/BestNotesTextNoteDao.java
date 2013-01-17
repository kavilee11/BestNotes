package com.fanshuo.android.bestnotes.db.DAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.fanshuo.android.bestnotes.Constants;
import com.fanshuo.android.bestnotes.app.model.BestNotesDeletedTextNoteModel;
import com.fanshuo.android.bestnotes.app.model.BestNotesTextNoteModel;
import com.fanshuo.android.bestnotes.db.ormsqlite.BestNotesDBHelper;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
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
	BestNotesOperationDao oDao = null;
	BestNotesDeletedTextNoteDao dDao = null;
	public BestNotesTextNoteDao(Context context) {
		super();
		this.context = context;
		helper=OpenHelperManager.getHelper(context,BestNotesDBHelper.class);
		try {
			dao = helper.getDao(BestNotesTextNoteModel.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		oDao = new BestNotesOperationDao(context);
		dDao = new BestNotesDeletedTextNoteDao(context);
	}
	
	/**
	 * 根据ID查询笔记
	 * @param id
	 * @return
	 */
	public BestNotesTextNoteModel getTextNoteById(int id){
		BestNotesTextNoteModel note = null;
		try {
			note = dao.queryForId(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return note;
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
	public int addNote(BestNotesTextNoteModel note, boolean saveOperation){
		int ret= -1;
		try {
			ret = dao.create(note);
			//记录操作行为到Operation
			if(saveOperation)
				oDao.addOperation(note.get_id(), Constants.Operations.ADD_NOTE);
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
	public int deleteNotes(List<BestNotesTextNoteModel> toBeDelete, boolean saveOperation){
		//记录操作行为到Operation
		if(saveOperation)
			for (BestNotesTextNoteModel item : toBeDelete) {
				oDao.addOperation(item.get_id(), Constants.Operations.DELETE_NOTE);
				//在回收站表中创建该笔记
				BestNotesDeletedTextNoteModel deletedNote = new BestNotesDeletedTextNoteModel();
				deletedNote.setDataFromTextNote(item);
				dDao.addNote(deletedNote);
			}
		try {
			return dao.delete(toBeDelete);
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	/**
	 * 修改笔记
	 * @param note 修改后的笔记
	 * @return
	 */
	public int updateNote(BestNotesTextNoteModel note, boolean saveOperation){
		if(saveOperation)
			oDao.addOperation(note.get_id(), Constants.Operations.UPDATE_NOTE);
		try {
			return dao.update(note);
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
