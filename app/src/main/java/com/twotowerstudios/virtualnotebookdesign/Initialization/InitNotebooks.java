package com.twotowerstudios.virtualnotebookdesign.Initialization;

import android.content.Context;
import android.util.Log;

import com.twotowerstudios.virtualnotebookdesign.Misc.Helpers;
import com.twotowerstudios.virtualnotebookdesign.Misc.SharedPrefs;
import com.twotowerstudios.virtualnotebookdesign.Notebook;
import com.twotowerstudios.virtualnotebookdesign.SQLiteHelper;

/**
 * Created by Panther II on 30/10/2016.
 */

public class InitNotebooks {
//TODO make a class that will initialize the Notebooks based on an SQL file

	public static boolean isDebug(Context context){
		return SharedPrefs.getBoolean(context, "debug");
	}
	public static boolean doDebugBooksExist(Context context){
		SQLiteHelper helper = new SQLiteHelper(context);
		try {
			Notebook debugNotebook = helper.getNotebook(1);
			Log.d("DebugBook", "Name of book with id 1 is: "+debugNotebook.name);
			if (debugNotebook.name.equalsIgnoreCase("debug")){
				Log.d("DebugBook", "Debug book exists");
				return true;
			}else{
				Log.d("DebugBook", "Debug book doesnt exist GRACE");
				return false;
			}
		} catch (Exception e) {
			Log.d("DebugBook", "Debug book doesnt exist EXCEPTION");
			e.printStackTrace();
			return false;
		}
	}
	public static void populateDebugBooks(Context context, boolean isDebugTrue){
		if (isDebugTrue && !doDebugBooksExist(context)){
			/**
			 * db.addNotebook(new Notebook("Spanish", "#2196f3", 27, Helpers.stringDataToMillis("2016/10/21")));
			 -        db.addNotebook(new Notebook("English", "#f44336", 41, Helpers.stringDataToMillis("2016/10/14")));
			 -        db.addNotebook(new Notebook("Chemistry", "RED", 22, Helpers.stringDataToMillis("2016/10/18")));
			 */

			SQLiteHelper sql = new SQLiteHelper(context);
			Notebook a = new Notebook("debug", "#999999", 404, Helpers.stringDataToMillis("2016/01/01")); sql.addNotebook(a);
			a = new Notebook("Spanish", "#2196f3", 27, Helpers.stringDataToMillis("2016/10/21")); sql.addNotebook(a);
			a = new Notebook("Art", "#3f51b5", 57, Helpers.stringDataToMillis("2016/10/08") ); sql.addNotebook(a);
			a = new Notebook("English", "#ffc107", 14, Helpers.stringDataToMillis("2016/09/28") ); sql.addNotebook(a);
			a = new Notebook("ECL", "#e91e63", 91, Helpers.stringDataToMillis("2016/08/01") ); sql.addNotebook(a);
			a = new Notebook("Chemistry", "#ff0000", 22, Helpers.stringDataToMillis("2016/10/18")); sql.addNotebook(a);
			a = new Notebook("DT","#607d8b", 31, Helpers.stringDataToMillis("2016/011/30")); sql.addNotebook(a);
			a = new Notebook("Biology","#4caf50", 39, Helpers.stringDataToMillis("2016/04/07")); sql.addNotebook(a);
			a = new Notebook("Math","#ffc107", 65, Helpers.stringDataToMillis("2016/12/22")); sql.addNotebook(a);
			a = new Notebook("Physics","#ff5722", 30, Helpers.stringDataToMillis("2016/08/23")); sql.addNotebook(a);
			a = new Notebook("PE","#009688", 88, Helpers.stringDataToMillis("2016/07/12")); sql.addNotebook(a);
			a = new Notebook("I.C.T.","#ffeb3b", 50, Helpers.stringDataToMillis("2016/06/11")); sql.addNotebook(a);
			a = new Notebook("Robotics","#ffc107", 77, Helpers.stringDataToMillis("2016/06/18")); sql.addNotebook(a);
			a = new Notebook("Economy","#03a9f4", 7, Helpers.stringDataToMillis("2016/09/14")); sql.addNotebook(a);
			a = new Notebook("Philosphy","#ff5722", 45, Helpers.stringDataToMillis("2016/06/01")); sql.addNotebook(a);
			a = new Notebook("Politics","#9c27b0", 11, Helpers.stringDataToMillis("2016/05/20")); sql.addNotebook(a);
			a = new Notebook("Geography","#e91e63", 3, Helpers.stringDataToMillis("2016/04/26")); sql.addNotebook(a);
			Log.d("populateDebugBooks", "successfully wrote all debug books");
		}
	}
}
