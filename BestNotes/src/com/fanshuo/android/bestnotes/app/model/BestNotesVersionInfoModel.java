package com.fanshuo.android.bestnotes.app.model;

/**
 * @author shuo
 * @date 2013-1-14 下午5:41:22
 * @version V1.0
 */
public class BestNotesVersionInfoModel {
	private int versionCode;
	private String versionName;
	private String updateInfo;
	private boolean important;
	private String link;

	public int getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(int versionCode) {
		this.versionCode = versionCode;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public String getUpdateInfo() {
		return updateInfo;
	}

	public void setUpdateInfo(String updateInfo) {
		this.updateInfo = updateInfo;
	}

	public boolean isImportant() {
		return important;
	}

	public void setImportant(boolean important) {
		this.important = important;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

}
