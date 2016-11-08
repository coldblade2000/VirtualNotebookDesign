package com.twotowerstudios.virtualnotebookdesign;

/**
 * Created by Panther II on 30/10/2016.
 */

public class Notebook {
	public String name,color;
	public int pages;
	public long lastModified;
	
	public Notebook(String name,
					String color,
					int pages,
					long lastModified
					){
		this.name = name;
		this.lastModified = lastModified;
		this.color = color;
		this.pages = pages;
	}
	public Notebook() {}

	public String getColor() {return  color;}

	public void setColor(String color) {this.color = color;}

	public String getName() {return  name;}

	public void setName(String name) { this.name = name;}

	public long getLastModified() {return  lastModified;}

	public void setLastModified(long lastModified) {
		this.lastModified = lastModified;
	}

	public int getNumOfPages() {return  pages;}

	public void setNumOfPages(int numOfPages) {
		 pages = numOfPages;
	}
}
