package com.fanshuo.android.bestnotes.app.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "TextNotes")
public class BestNotesTextNoteModel {

	public static final String ID = "_id";
	public static final String TITLE = "title";
	public static final String CONTENT = "content";
	public static final String FAVORITE = "favorite";
	public static final String IMPORTANT = "important";
	public static final String CREATE_TIME = "createTime";
	public static final String MODIFY_TIME = "modificationTime";
	
	@DatabaseField(generatedId=true,useGetSet=true,columnName=ID)
	int _id;
	@DatabaseField(useGetSet=true,columnName=TITLE) 
	String title;
	@DatabaseField(useGetSet=true,columnName=CONTENT) 
	String content;
	@DatabaseField(useGetSet=true,columnName=FAVORITE) 
	boolean favorite;// 是否标记为收藏
	@DatabaseField(useGetSet=true,columnName=IMPORTANT) 
	boolean important;// 是否标记为重要
	@DatabaseField(useGetSet=true,columnName=CREATE_TIME) 
	long createTime;// 创建时间
	@DatabaseField(useGetSet=true,columnName=MODIFY_TIME) 
	long modificationTime;// 最后修改时间
	
	
	public BestNotesTextNoteModel() {
		super();
	}
	
	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean getFavorite() {
		return favorite;
	}

	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
	}

	public boolean getImportant() {
		return important;
	}

	public void setImportant(boolean important) {
		this.important = important;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public long getModificationTime() {
		return modificationTime;
	}

	public void setModificationTime(long modificationTime) {
		this.modificationTime = modificationTime;
	}

	@Override
	public String toString() {
		return "BestNotesTextNoteModel [_id=" + _id + ", title=" + title
				+ ", content=" + content + ", favorite=" + favorite
				+ ", important=" + important + ", createTime=" + createTime
				+ ", modificationTime=" + modificationTime + "]";
	}

}
