package com.twotowerstudios.virtualnotebookdesign.NotebookSelection;

import com.twotowerstudios.virtualnotebookdesign.Notebook;

/**
 * Created by coldblade2000 on 20/10/16.
 */

public class NotebookSelectionCard {
    private Notebook notebook;

    public NotebookSelectionCard(){

    }
	public NotebookSelectionCard(Notebook notebook){
		this.notebook = notebook;
	}
	public String getColor() {
		return notebook.color;
	}

	public void setColor(String color) {
		notebook.color = color;
	}

	public String getName() {
		return notebook.name;
	}

	public void setName(String name) {notebook.name = name;}

	public long getLastModified() {
		return notebook.lastModified;
	}

	public void setLastModified(long lastModified) {notebook.lastModified = lastModified;}

	public int getNumOfPages() {
		return notebook.pages;
	}

	public int getId(){ return notebook.id;}
	public void setNumOfPages(int numOfPages) {
		notebook.pages = numOfPages;
	}

    /**public NotebookSelectionCard(String color, String name, int numOfPages, String lastModified){
        this.color = color;
        this.name = name;
        this.lastModified = lastModified;
        this.numOfPages = numOfPages;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public int getNumOfPages() {
        return numOfPages;
    }

    public void setNumOfPages(int numOfPages) {
        this.numOfPages = numOfPages;
    }
*/


}
