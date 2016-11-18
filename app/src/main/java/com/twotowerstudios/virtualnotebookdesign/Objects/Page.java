package com.twotowerstudios.virtualnotebookdesign.Objects;

import java.util.ArrayList;

/**
 * Created by Panther II on 17/11/2016.
 */

public class Page {
	public ArrayList<Object> content;
	public String name;

	public Page(String name){
		this.name = name;
	}
	public ArrayList<Object> getContent(){
		return content;
	}
	public void addToPage(Object newObject){
		content.add(newObject);
	}

}
