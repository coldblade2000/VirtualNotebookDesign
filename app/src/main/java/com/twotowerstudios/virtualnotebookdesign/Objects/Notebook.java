package com.twotowerstudios.virtualnotebookdesign.Objects;

import com.twotowerstudios.virtualnotebookdesign.Misc.Helpers;

import org.parceler.Parcel;

/**
 * Created by Panther II on 30/10/2016.
 */
@Parcel
public class Notebook {
	public String name,color;
	public int numOfPages;
	public long lastModified;
	//public ArrayList<Page> pages;
	
	public Notebook(String name, String color,
					int numOfPages, long lastModified ){
		this.name = name;
		this.lastModified = lastModified;
		this.color = color;
		this.numOfPages = numOfPages;
	}
	public Notebook(String name, String color){
		this.name=name;
		this.color=color;
		this.lastModified=Helpers.getCurrentTimeInMillis();
		this.numOfPages=0;
	}
	public Notebook(){}
	public String getColor() {return  color;}

	public void setColor(String color) {this.color = color;}

	public String getName() {return  name;}

	public void setName(String name) { this.name = name;}

	public long getLastModified() {return  lastModified;}

	public void setLastModified(long lastModified) {
		this.lastModified = lastModified;
	}

	public int getNumOfPages() {return numOfPages;}

	public void setNumOfPages(int numOfPages) {
		 this.numOfPages = numOfPages;
	}
}
