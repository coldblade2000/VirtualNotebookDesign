package com.twotowerstudios.virtualnotebookdesign.Objects;

import java.util.ArrayList;

/**
 * Created by ftlab on 11/24/17.
 */

public class Collection {
    String name;
    ArrayList<String> contentUIDs;
    int color;
    public Collection(String name, ArrayList<String> contentUIDs, int color){
        this.name=name;
        this.contentUIDs=contentUIDs;
        this.color=color;
    }
}
