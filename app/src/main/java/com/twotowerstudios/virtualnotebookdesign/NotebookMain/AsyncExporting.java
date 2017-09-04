package com.twotowerstudios.virtualnotebookdesign.NotebookMain;



import android.os.AsyncTask;
import android.os.Bundle;

import com.google.gson.Gson;
import com.twotowerstudios.virtualnotebookdesign.Misc.Helpers;
import com.twotowerstudios.virtualnotebookdesign.Objects.ChildBase;
import com.twotowerstudios.virtualnotebookdesign.Objects.Notebook;
import com.twotowerstudios.virtualnotebookdesign.Objects.Page;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by ftlab on 8/30/17.
 */

public class AsyncExporting extends AsyncTask<Notebook, Void, File> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected File doInBackground(Notebook... notebook) {
        ArrayList<File> fileList = new ArrayList<>();
        for (Page b:notebook[0].getPages()) {
            for (ChildBase a: b.getContent()) {
                if (a.getChildType()==1){
                    fileList.add(a.getFile());
                }
            }
        }
        Gson gson = new Gson();
        Bundle bundle = new Bundle();
        bundle.putString("notebookJson", gson.toJson(notebook[0]));

        return Helpers.zipFileArray(fileList, "z"+Helpers.generateUniqueId(16),bundle);

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
