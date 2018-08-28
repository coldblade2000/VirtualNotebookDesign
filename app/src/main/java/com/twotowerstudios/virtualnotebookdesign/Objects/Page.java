package com.twotowerstudios.virtualnotebookdesign.Objects;

import com.twotowerstudios.virtualnotebookdesign.Misc.Helpers;

import org.parceler.Parcel;

import java.util.ArrayList;



@Parcel
public class Page implements Comparable<Page> {
    public ArrayList<ChildBase> content;
    String name;
    int pageNumber;
    long lastModifiedMillis;
    long dateMillis;
    boolean isFavorite;
    String UID16;

    public void setContent(ArrayList<ChildBase> content) {
        this.content = content;
    }

    String parentUID;

    public Page(String name, int pageNumber, String parentUID) {
        this.name = name;
        this.pageNumber = pageNumber;
        this.content = new ArrayList<ChildBase>();
        this.lastModifiedMillis = Helpers.getCurrentTimeInMillis();
        this.isFavorite = false;
        this.dateMillis = 0;
        this.parentUID = parentUID;
        UID16 = "p" + Helpers.generateUniqueId(16);
    }

    public Page(String name, int pageNumber, long dateMillis, String parentUID) {
        this.name = name;
        this.pageNumber = pageNumber;
        this.content = new ArrayList<ChildBase>();
        this.lastModifiedMillis = Helpers.getCurrentTimeInMillis();
        this.isFavorite = false;
        this.parentUID = parentUID;
        this.dateMillis = dateMillis;
        UID16 = "p" + Helpers.generateUniqueId(16);
    }

    public Page() {
    }

    public String getUID() {
        return UID16;
    }

    public String getParentUID() {
        return parentUID;
    }

    public void setParentUID(String parentUID) {
        this.parentUID = parentUID;
    }

    public void setUID16(String UID16) {
        this.UID16 = UID16;
    }

    public void addToPage(ChildBase newObject) {
        content.add(newObject);
    }

    public void removeFromPage(ChildBase object) {
        content.remove(object);
    }

    public void removeFromPage(int index) {
        content.remove(index);
    }

    public void setLastModifiedMillis(long lastModifiedMillis) {
        this.lastModifiedMillis = lastModifiedMillis;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public ArrayList<ChildBase> getContent() {
        return content;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public long getLastModifiedMillis() {
        return lastModifiedMillis;
    }

    public String getName() {
        return name;
    }

    public int getNumberOfItems() {
        return content.size();
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

    public void setDateMillis(long dateMillis) {
        this.dateMillis = dateMillis;
    }

    public long getDateMillis() {
        return dateMillis;
    }

    @Override
    public int compareTo(Page page) {

        return this.pageNumber - page.pageNumber;
    }
}