package com.twotowerstudios.virtualnotebookdesign.Objects;

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

	public Page(String name, int pageNumber){
		this.name = name;
		this.pageNumber = pageNumber;
		this.content = new ArrayList<Object>();
	}
	public Page(){}
	public ArrayList<Object> getContent(){
		return content;
	}
	public int getPageNumber(){return pageNumber;}
	public String getName(){return name;}


	public void addToPage(Object newObject){
		content.add(newObject);
	}
	public void setName(String name){this.name = name;}
	public void setPageNumber(int pageNumber){this.pageNumber = pageNumber;}
}
