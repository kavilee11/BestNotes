package com.fanshuo.android.bestnotes.app.model;

import java.util.Calendar;

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
	public static final String LONGITUDE = "longitude";
	public static final String LATITUDE = "latitude";

	@DatabaseField(generatedId = true, useGetSet = true, columnName = ID)
	int _id;
	@DatabaseField(useGetSet = true, columnName = TITLE)
	String title;
	@DatabaseField(useGetSet = true, columnName = CONTENT)
	String content;
	@DatabaseField(useGetSet = true, columnName = FAVORITE)
	boolean favorite;// 是否标记为收藏
	@DatabaseField(useGetSet = true, columnName = IMPORTANT)
	boolean important;// 是否标记为重要
	@DatabaseField(useGetSet = true, columnName = CREATE_TIME)
	long createTime;// 创建时间
	@DatabaseField(useGetSet = true, columnName = MODIFY_TIME)
	long modificationTime;// 最后修改时间
	@DatabaseField(useGetSet = true, columnName = LONGITUDE)
	double longtitude;// 经度
	@DatabaseField(useGetSet = true, columnName = LATITUDE)
	double latitude;// 纬度

	int year;
	int month;
	int date;

	public int getYear() {
		Calendar date = Calendar.getInstance();
		date.setTimeInMillis(getCreateTime());
		return date.get(Calendar.YEAR);
	}

	public int getMonth() {
		Calendar date = Calendar.getInstance();
		date.setTimeInMillis(getCreateTime());
		return date.get(Calendar.MONTH) + 1;
	}

	public int getDate() {
		Calendar date = Calendar.getInstance();
		date.setTimeInMillis(getCreateTime());
		return date.get(Calendar.DAY_OF_MONTH);
	}

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

	public double getLongtitude() {
		return longtitude;
	}

	public void setLongtitude(double longtitude) {
		this.longtitude = longtitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	@Override
	public String toString() {
		return "BestNotesTextNoteModel [_id=" + _id + ", title=" + title
				+ ", content=" + content + ", favorite=" + favorite
				+ ", important=" + important + ", createTime=" + createTime
				+ ", modificationTime=" + modificationTime + ", longtitude="
				+ longtitude + ", latitude=" + latitude + ", year=" + year
				+ ", month=" + month + ", date=" + date + "]";
	}

}
