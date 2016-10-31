package com.twotowerstudios.virtualnotebookdesign;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

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

        db.addNotebook(new Notebook("John", 1829));
        /*db.addGamer(new Gamer("Zoe", 2060));
        db.addGamer(new Gamer("David", 2377));
        db.addGamer(new Gamer("Sandy", 1934));*/
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