package com.twotowerstudios.virtualnotebookdesign.BookLight;

/**
 * Created by Panther II on 09/10/2016.
 */

public class BookLight {
	private String bookColor;
	private String name;
	public BookLight(){

	}
	public BookLight(String name, String color){
		this.name = name;
		this.bookColor = color;
	}
	public String getName(){ return name;}
	public String getBookColor(){return bookColor;}
	public void setName(String name){ this.name = name;}

	public void setBookColor(String color) { this.bookColor = color;}
}
