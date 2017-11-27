package com.twotowerstudios.virtualnotebookdesign.Objects;

import com.twotowerstudios.virtualnotebookdesign.Misc.Helpers;

import java.util.ArrayList;

/**
 * Created by ftlab on 11/24/17.
 */

public class Collection {
    String name;
    ArrayList<String> contentUIDs;
    int color;
    String UID8;
    public Collection(String name, ArrayList<String> contentUIDs, int color){
        this.name=name;
        this.contentUIDs=contentUIDs;
        this.color=color;
        this.UID8= "c"+Helpers.generateUniqueId(8);
    }

    public ArrayList<String> getContentUIDs() {
        return contentUIDs;
    }

    public void setContentUIDs(ArrayList<String> contentUIDs) {
        this.contentUIDs = contentUIDs;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUID8() {
        return UID8;
    }
}