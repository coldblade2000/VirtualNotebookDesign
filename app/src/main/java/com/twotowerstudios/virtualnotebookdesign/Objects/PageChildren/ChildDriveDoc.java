package com.twotowerstudios.virtualnotebookdesign.Objects.PageChildren;

import com.twotowerstudios.virtualnotebookdesign.Misc.Helpers;

public class ChildDriveDoc {
	public final int DOCS=0,SHEETS=1,SLIDES=2,OTHER=3;

    private String name;
    private String driveId;
	private int driveType;

	private String UID16;
	private String parentUID;

	public ChildDriveDoc(int driveType, String name, String parentUID, String driveId){
		this.driveType=driveType;
		this.name=name;
		this.driveId=driveId;
		this.UID16= "d"+Helpers.generateUniqueId(16);
	}

	public int getType(){return driveType;}

    public void setDriveId(String driveId) {
        this.driveId = driveId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDriveId() {
        return driveId;
    }

    public String getName() {
        return name;
    }
	public String getUID16() {return UID16;}

	public void setUID16(String UID16) {this.UID16 = UID16;}

	public String getParentUID() {return parentUID;}

	public void setParentUID(String parentUID) {this.parentUID = parentUID;}
}
