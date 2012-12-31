package com.fanshuo.android.bestnotes.app.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * @author fanshuo
 * @date 2012-12-29 上午1:02:06
 * @version V1.0
 */
@DatabaseTable(tableName = "NoteOperations")
public class BestNotesOperationModel {

	public static final String ID = "_id";
	public static final String OPERATION = "operation";
	public static final String NOTEID = "noteId";
	public static final String CREATE_TIME = "createTime";

	@DatabaseField(generatedId = true, useGetSet = true, columnName = ID)
	private int _id;
	@DatabaseField(useGetSet = true, columnName = OPERATION)
	private int operation;
	@DatabaseField(useGetSet = true, columnName = NOTEID)
	private int noteId;
	@DatabaseField(useGetSet = true, columnName = CREATE_TIME)
	private long createTime;

	public BestNotesOperationModel() {
		super();
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public int getOperation() {
		return operation;
	}

	public void setOperation(int operation) {
		this.operation = operation;
	}

	public int getNoteId() {
		return noteId;
	}

	public void setNoteId(int noteId) {
		this.noteId = noteId;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

}
