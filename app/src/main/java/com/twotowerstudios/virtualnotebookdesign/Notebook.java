package com.twotowerstudios.virtualnotebookdesign;

import java.util.Date;

/**
 * Created by Panther II on 30/10/2016.
 */

public class Notebook {
	public String name,color;
	public int pages;
	public Date lastModified;


	public Notebook(String name,
					String color,
					int pages,
					Date lastModified
					){
		this.name = name;
		this.lastModified = lastModified;
		this.color = color;
		this.pages = pages;
	}
}
