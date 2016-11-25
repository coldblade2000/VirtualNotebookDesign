package com.twotowerstudios.virtualnotebookdesign.Objects;

import com.twotowerstudios.virtualnotebookdesign.Misc.Helpers;

import org.parceler.Parcel;

import java.util.ArrayList;

/**
 * Created by Panther II on 17/11/2016.
 */
@Parcel
public class Page {
	public ArrayList<Object> content;
	public String name;
	public int pageNumber;
	public long lastModifiedMillis;

	public Page(String name, int pageNumber){
		this.name = name;
		this.pageNumber = pageNumber;
		this.content = new ArrayList<Object>();
		this.lastModifiedMillis = Helpers.getCurrentTimeInMillis();
	}
	public Page(){}

	public void addToPage(Object newObject){content.add(newObject);}
	public void setLastModifiedMillis(long lastModifiedMillis) {
		this.lastModifiedMillis = lastModifiedMillis;}
	public void setName(String name){this.name = name;}
	public void setPageNumber(int pageNumber){this.pageNumber = pageNumber;}
	public ArrayList<Object> getContent(){return content;}
	public int getPageNumber(){return pageNumber;}
	public long getLastModifiedMillis() {return lastModifiedMillis;}
	public String getName(){return name;}
	public int getNumberOfItems(){return content.size();}
}
