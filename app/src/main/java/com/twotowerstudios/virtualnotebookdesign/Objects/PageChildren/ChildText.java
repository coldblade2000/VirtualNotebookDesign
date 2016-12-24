package com.twotowerstudios.virtualnotebookdesign.Objects.PageChildren;

import com.twotowerstudios.virtualnotebookdesign.Misc.Helpers;

/**
 * Created by Panther II on 11/12/2016.
 */

public class ChildText {
	public String title;
	public String text;
	public String UID16;

	public ChildText(String title, String text){
		this.title=title;
		this.text=text;
		UID16= "t"+Helpers.generateUniqueId(16);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getUID16() {return UID16;}

	public void setUID16(String UID16) {this.UID16 = UID16;}
}
