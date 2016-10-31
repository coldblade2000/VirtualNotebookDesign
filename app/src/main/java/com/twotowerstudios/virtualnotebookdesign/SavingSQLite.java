package com.twotowerstudios.virtualnotebookdesign;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.twotowerstudios.virtualnotebookdesign.Misc.Helpers;

/**
 * Created by coldblade2000 on 31/10/16.
 */

public class SavingSQLite extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.sqlite);

        saveData();
        TextView tv = (TextView)findViewById(R.id.tvsqlite);
        tv.setText("Data saved on SQLite database!");

        loadData();
    }

    private void saveData() {
        SQLiteHelper db = new SQLiteHelper(this);

        /**
         * <notebook name="Spanish" color="#2196f3" pages="27" lastUsed="October 21"></notebook>
         <notebook name="English" color="#f44336" pages="41" lastUsed="October 21"></notebook>
         <notebook name="Chemistry" color="RED" pages="27" lastUsed="October 21"></notebook>
         <notebook name="Art" color="RED" pages="27" lastUsed="October 21"></notebook>
         <notebook name="ECL" color="RED" pages="27" lastUsed="October 21"></notebook>
         <notebook name="DT" color="RED" pages="27" lastUsed="October 21"></notebook>
         <notebook name="Physics" color="RED" pages="27" lastUsed="October 21"></notebook>
         */
        //"yyyy/MM/dd, HH:mm"
        db.addNotebook(new Notebook("Spanish", "#2196f3", 27, Helpers.stringDataToMillis("2016/10/21")));
        db.addNotebook(new Notebook("English", "#f44336", 41, Helpers.stringDataToMillis("2016/10/14")));
        db.addNotebook(new Notebook("Chemistry", "RED", 22, Helpers.stringDataToMillis("2016/10/18")));

    }

    private void loadData() {
        SQLiteHelper db = new SQLiteHelper(this);
        Gamer gamer = db.getGamer(2);

        TextView tv = (TextView)findViewById(R.id.tvsqlite);
        tv.setText(gamer.toString());
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }
}