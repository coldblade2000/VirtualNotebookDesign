package com.twotowerstudios.virtualnotebookdesign.Objects;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import com.twotowerstudios.virtualnotebookdesign.Misc.Helpers;

import org.parceler.Parcel;

@Parcel
public class ChildBase {
	//public final int TEXT=0,IMAGE=1,DRIVE=2;
	String title;
	String UID16;
	String pageUID;
	String notebookUID;

	String uri;



	public ChildBase(){}
	int childType;
	/**Drive*/
	String driveId;
	int driveType;

	public Uri getUri() {
		return Uri.parse(uri);
	}

	public boolean doesUriExist(){
		return !(uri == null || uri.equals(""));
	}
	public void setUri(Uri uri) {
		this.uri = uri.toString();
	}

	public ChildBase(int driveType, String name, String notebookUID, String pageUID, String driveId){
		this.driveType=driveType;
		this.title=name;
		this.driveId=driveId;
		this.UID16= "d"+ Helpers.generateUniqueId(16);
		this.pageUID=pageUID;
		this.notebookUID =notebookUID;
		this.childType=2;
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
	public  ChildBase(String name, String ImageUID, String notebookUID, String pageUID, Uri uri, Context context){
		this.title =name;
		this.ImageUID=ImageUID;
		path=context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)+"/"+ImageUID+".png";
		char[] charar = ImageUID.toCharArray();
		charar[0]='c';
		UID16 = charar.toString();
		this.notebookUID = notebookUID;
		this.pageUID=pageUID;
		this.childType=1;
		this.uri=uri.toString();
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setNotebookUID(String notebookUID) {
		this.notebookUID = notebookUID;
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
	public ChildBase(String title, String text, String notebookUID, String pageUID){
		this.title=title;
		this.text=text;
		UID16= "t"+Helpers.generateUniqueId(16);
		this.notebookUID = notebookUID;
		this.pageUID=pageUID;
		this.childType=0;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

	/**   */
	public String getNotebookUID() {
		return notebookUID;
	}
	public String getPageUID() {return pageUID;}

	public void setPageUID(String pageUID) {
		this.pageUID = pageUID;
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
