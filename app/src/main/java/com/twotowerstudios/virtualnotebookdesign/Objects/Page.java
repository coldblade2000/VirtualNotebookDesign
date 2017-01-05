package com.twotowerstudios.virtualnotebookdesign.Objects;

import com.twotowerstudios.virtualnotebookdesign.Misc.Helpers;

import org.parceler.Parcel;

import java.util.ArrayList;

/**
 * Created by Panther II on 17/11/2016.
 */
@Parcel
public class Page implements Comparable<Page>{
	public ArrayList<Object> content;
	private String name;
	int pageNumber;
	long lastModifiedMillis;
	long dateMillis;
	boolean isFavorite;
	String UID16;
	String parentUID;

	public Page(String name, int pageNumber, String parentUID){
		this.name = name;
		this.pageNumber = pageNumber;
		this.content = new ArrayList<Object>();
		this.lastModifiedMillis = Helpers.getCurrentTimeInMillis();
		this.isFavorite=false;
		this.dateMillis=0;
		this.parentUID=parentUID;
		UID16 ="p"+Helpers.generateUniqueId(16);
	}
	public Page(String name, int pageNumber, long dateMillis, String parentUID){
		this.name = name;
		this.pageNumber = pageNumber;
		this.content = new ArrayList<Object>();
		this.lastModifiedMillis = Helpers.getCurrentTimeInMillis();
		this.isFavorite=false;
		this.parentUID=parentUID;
		this.dateMillis=dateMillis;
		UID16 ="p"+Helpers.generateUniqueId(16);
	}
	public Page(){}

	public String getUID() {
		/**if(UID16==null||UID16=="") {
			UID16 = "p"+Helpers.generateUniqueId(16);
			Log.d("Page getUID()","Generated new UID for "+name+": "+UID16);
			ArrayList<Notebook> notebookList = new Helpers().getNotebookList(context);
			for(Notebook a: notebookList){
				if(a.getName().equalsIgnoreCase(parent)){
					for(Page b:a.getPages()){
						if(b.getName().equals(name)){
							b.setUID16(UID16);
							Helpers.writeListToFile(context,notebookList);
							Log.i("Page getUID()","wrote updated list to file");
						}break;
					}
				}break;
			}
			return UID16;
		}else{
			return UID16;
		}*/
		return UID16;
	}

	public String getParentUID() {return parentUID;}

	public void setParentUID(String parentUID) {this.parentUID = parentUID;}

	public void setUID16(String UID16) {this.UID16 = UID16;}

	public void addToPage(Object newObject){content.add(newObject);}
	public void setLastModifiedMillis(long lastModifiedMillis) {
		this.lastModifiedMillis = lastModifiedMillis;}
	public void setName(String name){this.name = name;}
	public void setPageNumber(int pageNumber){this.pageNumber = pageNumber;}
	public ArrayList<Object> getContent(){return content;}
	public int getPageNumber(){return pageNumber;}
	public long getLastModifiedMillis() {return lastModifiedMillis;}
	public String getName(){return name;}
	public int getNumberOfItems(){return content.size();}
	public boolean isFavorite(){return isFavorite;}
	public void setIsFavorite(boolean isFavorite){this.isFavorite=isFavorite;}

	public void setDateMillis(long dateMillis) {this.dateMillis = dateMillis;}

	public long getDateMillis() {return dateMillis;}

	@Override
	public int compareTo(Page page) {

		return this.pageNumber-page.pageNumber;
	}
}
