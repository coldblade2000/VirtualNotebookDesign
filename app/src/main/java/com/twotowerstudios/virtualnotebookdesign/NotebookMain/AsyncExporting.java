package com.twotowerstudios.virtualnotebookdesign.NotebookMain;



import android.os.AsyncTask;

import com.twotowerstudios.virtualnotebookdesign.Objects.ChildBase;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by ftlab on 8/30/17.
 */

public class AsyncExporting extends AsyncTask<ArrayList<ChildBase>, Void, File> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected File doInBackground(ArrayList<ChildBase>... arrayLists) {
        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(File file) {
        super.onPostExecute(file);
    }
}
