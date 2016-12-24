package com.twotowerstudios.virtualnotebookdesign.Objects.PageChildren;

import android.content.Context;

public class ChildImage {
	public String name;
	public String ImageUID;
	public String path;
	public String UID16;
	public String parentUID;

	public ChildImage(String name, String ImageUID, String parentUID, Context context){
		this.name=name;
		this.ImageUID=ImageUID;
		path=context.getFilesDir()+"/"+ImageUID;
		char[] charar = ImageUID.toCharArray();
		charar[0]='c';
		UID16 = charar.toString();
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}
	public String getUID16() {return UID16;}

	public void setUID16(String UID16) {this.UID16 = UID16;}

	public String getParentUID() {return parentUID;}

	public void setParentUID(String parentUID) {this.parentUID = parentUID;}
}
