package com.twotowerstudios.virtualnotebookdesign;

/**
 * Created by Panther II on 30/10/2016.
 */

public class Notebook {
	public String name,color;
	public int pages,id;
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
}
