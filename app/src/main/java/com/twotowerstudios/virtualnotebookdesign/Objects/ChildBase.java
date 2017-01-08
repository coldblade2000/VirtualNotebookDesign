package com.twotowerstudios.virtualnotebookdesign.Objects;

import android.content.Context;

import com.twotowerstudios.virtualnotebookdesign.Misc.Helpers;

import org.parceler.Parcel;

@Parcel
public class ChildBase {
	public final int TEXT=0,IMAGE=1,DRIVE=2;
	String title;
	String UID16;
	String parentUID;
	public ChildBase(){}
	int childType;
	/**Drive*/
	String driveId;
	int driveType;
	public ChildBase(int driveType, String name, String parentUID, String driveId){
		this.driveType=driveType;
		this.title=name;
		this.driveId=driveId;
		this.UID16= "d"+ Helpers.generateUniqueId(16);
		this.parentUID=parentUID;
		this.childType=DRIVE;
	}
	public int getType(){return driveType;}
	public void setDriveId(String driveId) {
		this.driveId = driveId;
	}
	public String getDriveId() {
		return driveId;
	}
	/**Image*/
	String ImageUID;
	String path;
	public ChildBase(String name, String ImageUID, String parentUID, Context context){
		this.title =name;
		this.ImageUID=ImageUID;
		path=context.getFilesDir()+"/"+ImageUID;
		char[] charar = ImageUID.toCharArray();
		charar[0]='c';
		UID16 = charar.toString();
		this.parentUID=parentUID;
		this.childType=IMAGE;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getPath() {
		return path;
	}
	public String getImageUID() {
		return ImageUID;
	}
	public void setImageUID(String imageUID) {
		ImageUID = imageUID;
	}

	/**Text*/
	String text;
	public ChildBase(String title, String text, String parentUID){
		this.title=title;
		this.text=text;
		UID16= "t"+Helpers.generateUniqueId(16);
		this.parentUID=parentUID;
		this.childType=TEXT;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

	/**   */
	public String getParentUID() {
		return parentUID;
	}
	public String getUID16() {
		return UID16;
	}
	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}
	public int getChildType() {return childType;}

}
