package com.twotowerstudios.virtualnotebookdesign;

/**
 * Created by coldblade2000 on 20/10/16.
 */

public class NotebookSelectionCard {
    private String color, name, lastModified;
    private int numOfPages;
    public NotebookSelectionCard(){

    }
    public NotebookSelectionCard(String color, String name, int numOfPages, String lastModified){
        this.color = color;
        this.name = name;
        this.lastModified = lastModified;
        this.numOfPages = numOfPages;
    }

    /**
     * private String bookColor;
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
     */
}
