package com.twotowerstudios.virtualnotebookdesign.Objects;

import android.content.Context;

import com.twotowerstudios.virtualnotebookdesign.Misc.Helpers;

import org.parceler.Parcel;

/**
 * Created by Panther II on 30/10/2016.
 */
@Parcel
public class Notebook {
	public String name;
	public int numOfPages,color,accentColor;
	public long lastModified;
	//public ArrayList<Page> pages;
	
	public Notebook(String name, int color,
					int numOfPages, long lastModified, Context context){
		this.name = name;
		this.lastModified = lastModified;
		this.color = color;
		this.numOfPages = numOfPages;
		this.accentColor=Helpers.getSingleColorAccent(context,color);
	}
	public Notebook(String name, int color, int accentColor){
		this.name=name;
		this.color=color;
		this.lastModified=Helpers.getCurrentTimeInMillis();
		this.numOfPages=0;
		this.accentColor=accentColor;
	}
	public Notebook(){}
	public int getColor() {return  color;}

	public void setColor(int color) {this.color = color;}

	public int getAccentColor() {return accentColor;}

	public void setAccentColor(int accentColor) {this.accentColor = accentColor;}

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
