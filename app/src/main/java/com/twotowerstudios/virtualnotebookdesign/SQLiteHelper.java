package com.twotowerstudios.virtualnotebookdesign;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.twotowerstudios.virtualnotebookdesign.Misc.Helpers;

/**
 * Source: http://www.developer.com/ws/android/storing-app-related-data-in-your-android-apps.html
 */

/**
 * Alright Diego, this is how you will use this table.
 *
 * To save data:
 * First you will initialize a new SQLiteHelper.
        SQLiteHelper db = new SQLiteHelper(this);
 *After initializing, you will create a notebook in the following manner:
        db.addNotebook(new Notebook("Spanish", "#2196f3", 27, Helpers.stringDataToMillis("2016/10/21")));
 * The parameters of Notebook are (String name, String color, int pages, long lastModified).
 * To use lastModified, you need a long data type. To convert a date to a long, you can use
 * the Helpers.stringDataToMillis("yyyy/MM/dd, HH:mm") method to get the number of milliseconds
 * correspondent to the date written in there. You can also use the "yyyy/MM/dd" format if you dont want
 * to bother with minutes and hours
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
                "pages INTEGER, " +
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
		Helpers help = new Helpers();
        ContentValues values = new ContentValues();
        values.put("name", notebook.name);
        values.put("color", notebook.color);
        values.put("pages", notebook.pages);
        values.put("lastdate", help.millisDateToString(notebook.lastModified));

        db.insert(TABLE_NOTEBOOKS, null, values);
        db.close();
    }

    public Notebook getNotebook(int id){
        SQLiteDatabase db = this.getReadableDatabase();
		Helpers help = new Helpers();
        Cursor cursor = db.query(TABLE_NOTEBOOKS,
                new String[]{"id", "name", "color", "pages", "lastdate"},
                " id = ?", new String[]{ String.valueOf(id) },
                null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();
        else
            return null;
		//Integer.parseInt(cursor.getString(0)),
        Notebook notebook = new Notebook(
				cursor.getString(cursor.getColumnIndex("name")),
				cursor.getString(cursor.getColumnIndex("color")),
				cursor.getInt(cursor.getColumnIndex("pages")),
				Helpers.stringDataToMillis(cursor.getString(cursor.getColumnIndex("lastdate"))));
        notebook.id = Integer.parseInt(cursor.getString(0));
        notebook.name = cursor.getString(1);
        notebook.color = cursor.getString(2);
        notebook.pages = cursor.getInt(3);
        notebook.lastModified = Helpers.stringDataToMillis(cursor.getString(cursor.getColumnIndex("lastdate")));
		Log.d("getNotebook", "The date in millis should be: "+Helpers.stringDataToMillis(cursor.getString(cursor.getColumnIndex("lastdate"))));
        return notebook;
    }
	public int getNumberOfNotebooks(){
		int number = 0;
		SQLiteDatabase db = this.getReadableDatabase();
		number = (int) DatabaseUtils.queryNumEntries(db, "notebooks");
		Log.d("SQLiteHelper", "Number of notebooks detected = "+number);
		return number;
	}

}
