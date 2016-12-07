package com.twotowerstudios.virtualnotebookdesign.Objects;

import com.twotowerstudios.virtualnotebookdesign.Misc.Helpers;

import org.parceler.Parcel;

import java.util.ArrayList;

/**
 * Created by Panther II on 17/11/2016.
 */
@Parcel
public class Page implements Comparable<Page>{
	public ArrayList<Object> content;
	public String name;
	public int pageNumber;
	public long lastModifiedMillis;
	public boolean isFavorite;

	public Page(String name, int pageNumber){
		this.name = name;
		this.pageNumber = pageNumber;
		this.content = new ArrayList<Object>();
		this.lastModifiedMillis = Helpers.getCurrentTimeInMillis();
		this.isFavorite=false;
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
	public boolean isFavorite(){return isFavorite;}

	@Override
	public int compareTo(Page page) {
		return this.pageNumber-page.pageNumber;
	}
}
