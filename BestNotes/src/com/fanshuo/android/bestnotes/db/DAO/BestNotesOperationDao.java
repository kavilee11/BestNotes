package com.fanshuo.android.bestnotes.db.DAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.fanshuo.android.bestnotes.Constants;
import com.fanshuo.android.bestnotes.app.model.BestNotesOperationModel;
import com.fanshuo.android.bestnotes.db.ormsqlite.BestNotesDBHelper;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

/**
 * @author fanshuo
 * @date 2012-12-29 上午1:09:53
 * @version V1.0
 */
public class BestNotesOperationDao {

	private Dao<BestNotesOperationModel, Integer> dao = null;
	BestNotesDBHelper helper=null;
	private Context context=null;  
	public BestNotesOperationDao(Context context) {
		super();
		this.context = context;
		helper=OpenHelperManager.getHelper(context,BestNotesDBHelper.class);
		try {
			dao = helper.getDao(BestNotesOperationModel.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 插入一个操作
	 * @param noteID 笔记ID，没有就传入0
	 * @param operationID 操作，如添加、查询、删除、刷新、查找等等..,在{@link Constants.Operations}
	 * @return 成功返回1;   异常返回-1
	 */
	public int addOperation(int noteID, int operationID){
		BestNotesOperationModel o = new BestNotesOperationModel();
		o.setCreateTime(System.currentTimeMillis());
		o.setNoteId(noteID);
		o.setOperation(operationID);
		
		try {
			return dao.create(o);
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	/**
	 * 获取所有操作
	 * @param orderByFiledName 排序所依据的字段
	 * @param ascending	是否升序排列
	 * @return
	 */
	public List<BestNotesOperationModel> getAll(String orderByFiledName , boolean ascending){
		List<BestNotesOperationModel> list = new ArrayList<BestNotesOperationModel>();
		QueryBuilder<BestNotesOperationModel, Integer> builder = dao.queryBuilder();
		PreparedQuery<BestNotesOperationModel> prepare;
		try {
			prepare = builder.orderBy(orderByFiledName, ascending).prepare();
			list = dao.query(prepare);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	protected void finalize() throws Throwable {
		OpenHelperManager.releaseHelper();
		helper = null;
		super.finalize();
	} 
	
}
