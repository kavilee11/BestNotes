package com.fanshuo.android.bestnotes.app.model;

import com.j256.ormlite.field.DatabaseField;

public class BestNotesUserModel {

	@DatabaseField(generatedId = true)
	int _id;
	@DatabaseField
	int uid;
	@DatabaseField
	String username;
	@DatabaseField
	String userpwd;
	@DatabaseField
	boolean qqUser;
	@DatabaseField
	boolean sinaWeiboUser;

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserpwd() {
		return userpwd;
	}

	public void setUserpwd(String userpwd) {
		this.userpwd = userpwd;
	}

	public boolean isQqUser() {
		return qqUser;
	}

	public void setQqUser(boolean qqUser) {
		this.qqUser = qqUser;
	}

	public boolean isSinaWeiboUser() {
		return sinaWeiboUser;
	}

	public void setSinaWeiboUser(boolean sinaWeiboUser) {
		this.sinaWeiboUser = sinaWeiboUser;
	}

	@Override
	public String toString() {
		return "BestNotesUserModel [_id=" + _id + ", uid=" + uid
				+ ", username=" + username + ", userpwd=" + userpwd
				+ ", qqUser=" + qqUser + ", sinaWeiboUser=" + sinaWeiboUser
				+ "]";
	}

}
