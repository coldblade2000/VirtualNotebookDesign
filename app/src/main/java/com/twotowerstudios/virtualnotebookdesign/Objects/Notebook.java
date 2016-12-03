package com.twotowerstudios.virtualnotebookdesign.Objects;

import android.content.Context;

import com.twotowerstudios.virtualnotebookdesign.Misc.Helpers;

import org.parceler.Parcel;

import java.util.ArrayList;

/**
 * Created by Panther II on 30/10/2016.
 */
@Parcel
public class Notebook {
	public String name;
	public int debugNumOfPages,color,accentColor;
	public long lastModified;
	public ArrayList<Page> pages;

	public Notebook(String name, int color,
					int debugNumOfPages, long lastModified, Context context){
		this.name = name;
		this.lastModified = lastModified;
		this.color = color;
		this.debugNumOfPages = debugNumOfPages;
		this.accentColor=Helpers.getSingleColorAccent(context,color);
		this.pages = new ArrayList<>();
	}
	public Notebook(String name, int color, int accentColor){
		this.name=name;
		this.color=color;
		this.lastModified=Helpers.getCurrentTimeInMillis();
		this.debugNumOfPages =5;
		this.accentColor=accentColor;
		this.pages = new ArrayList<>();
		this.pages.add(new Page("DEBUG PAGE", 405));
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

	public int getDebugNumOfPages() {return debugNumOfPages;}

	public int getNumberOfPages(){return pages.size();}

	public void setDebugNumOfPages(int debugNumOfPages) {
		 this.debugNumOfPages = debugNumOfPages;
	}

	public ArrayList<Page> getPages() {
		return pages;
	}
	public void addPage(Page page){
		pages.add(page);
	}
}