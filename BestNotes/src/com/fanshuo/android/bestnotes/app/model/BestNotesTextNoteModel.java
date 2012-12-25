package com.fanshuo.android.bestnotes.app.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "TextNotes")
public class BestNotesTextNoteModel {

	public static final String MODI_TIME_FIELD_NAME = "modificationTime";
	
	
	@DatabaseField(generatedId = true)
	int _id;
	@DatabaseField
	String title;
	@DatabaseField
	String content;
	@DatabaseField
	boolean favorite;// 是否标记为收藏
	@DatabaseField
	boolean important;// 是否标记为重要
	@DatabaseField
	long createTime;// 创建时间
	@DatabaseField
	long modificationTime;// 最后修改时间
	
	public BestNotesTextNoteModel() {
		super();
	}
	
	public BestNotesTextNoteModel(int _id, String title, String content,
			boolean favorite, boolean important, long createTime,
			long modificationTime) {
		super();
		this._id = _id;
		this.title = title;
		this.content = content;
		this.favorite = favorite;
		this.important = important;
		this.createTime = createTime;
		this.modificationTime = modificationTime;
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

	public boolean isFavorite() {
		return favorite;
	}

	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
	}

	public boolean isImportant() {
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
