package com.twotowerstudios.virtualnotebookdesign.Objects.PageChildren;

import com.twotowerstudios.virtualnotebookdesign.Misc.Helpers;

/**
 * Created by Panther II on 11/12/2016.
 */

public class ChildDriveDoc {
	public final int DOCS=0,SHEETS=1,SLIDES=2,OTHER=3;

    public String name;
    public String driveId;
	public int driveType;

	public String UID16;

	public ChildDriveDoc(int driveType, String name, String driveId){
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
}
