package com.twotowerstudios.virtualnotebookdesign;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Source: http://www.developer.com/ws/android/storing-app-related-data-in-your-android-apps.html
 */

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "NotebookDB";
    private static final String TABLE_NOTEBOOKS = "notebooks";

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_NOTEBOOKS_TABLE = "CREATE TABLE "
                + TABLE_NOTEBOOKS + " ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "color TEXT, " +
                "pages INTEGER " +
                "lastdate TEXT)";
        db.execSQL(CREATE_NOTEBOOKS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,
                          int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTEBOOKS);
        this.onCreate(db);
    }

    public void addNotebook(Notebook notebook){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", notebook.name);
        values.put("color", notebook.color);
        values.put("pages", notebook.pages);
        values.put("date", notebook.lastModified);

        db.insert(TABLE_NOTEBOOKS, null, values);
        db.close();
    }

    public Gamer getGamer(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_GAMERS,
                new String[]{"id", "name", "ratings"},
                " id = ?", new String[]{ String.valueOf(id) },
                null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();
        else
            return null;

        Gamer gamer = new Gamer();
        gamer.id = Integer.parseInt(cursor.getString(0));
        gamer.name = cursor.getString(1);
        gamer.ratings =
                Integer.parseInt(cursor.getString(2));

        return gamer;
    }
}
