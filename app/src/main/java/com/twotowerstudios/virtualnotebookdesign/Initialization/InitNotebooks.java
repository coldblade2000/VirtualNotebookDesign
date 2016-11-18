package com.twotowerstudios.virtualnotebookdesign.Initialization;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.twotowerstudios.virtualnotebookdesign.Misc.Helpers;
import com.twotowerstudios.virtualnotebookdesign.Misc.SharedPrefs;
import com.twotowerstudios.virtualnotebookdesign.Objects.Notebook;

import java.util.ArrayList;

/**
 * Created by Panther II on 30/10/2016.
 */

public class InitNotebooks {


	public static boolean isDebug(Context context){
		return SharedPrefs.getBoolean(context, "debug");
	}
	public static boolean doDebugBooksExist(Context context){
		try {
			Helpers help = new Helpers();
			ArrayList<Notebook> list = help.getNotebookList(context);
			for(int i=0; i<=list.size();i++){
				if(list.get(i).getName().equalsIgnoreCase("debug")){
					Log.d("DebugBook", "Debug book exists");
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		Log.d("DebugBook", "Debug book doesnt exist GRACE");
		return false;
	}
	public static void populateDebugBooks(Context context, boolean isDebugTrue){
		if (isDebugTrue && !doDebugBooksExist(context)){

			try {
				ArrayList<Notebook> list = new Helpers().getNotebookList(context);

				Notebook a = new Notebook("debug", "#999999", 404, Helpers.stringDataToMillis("2016/01/01"));list.add(a);
				a = new Notebook("Science","#795548", 29, Helpers.stringDataToMillis("2016/11/10"));list.add(a);
				a = new Notebook("ECL","#607d8b", 18, Helpers.stringDataToMillis("2016/12/26"));list.add(a);
				a = new Notebook("Art","#3f51b5", 32, Helpers.stringDataToMillis("2016/08/15"));list.add(a);
				a = new Notebook("Science","#00bcd4", 22, Helpers.stringDataToMillis("2016/01/20"));list.add(a);
				a = new Notebook("Science","#8bc34a", 44, Helpers.stringDataToMillis("2016/12/07"));list.add(a);
				a = new Notebook("Biology","#009688", 65, Helpers.stringDataToMillis("2016/04/29"));list.add(a);
				a = new Notebook("Robotics","#795548", 11, Helpers.stringDataToMillis("2016/06/06"));list.add(a);
				a = new Notebook("English","#e91e63", 65, Helpers.stringDataToMillis("2016/07/18"));list.add(a);
				a = new Notebook("Biology","#9c27b0", 83, Helpers.stringDataToMillis("2016/01/15"));list.add(a);
				a = new Notebook("Economy","#607d8b", 47, Helpers.stringDataToMillis("2016/11/04"));list.add(a);
				a = new Notebook("Politics","#ff9800", 50, Helpers.stringDataToMillis("2016/05/22"));list.add(a);
				a = new Notebook("Politics","#ffc107", 28, Helpers.stringDataToMillis("2016/12/27"));list.add(a);
				a = new Notebook("Politics","#00bcd4", 95, Helpers.stringDataToMillis("2016/11/02"));list.add(a);
				a = new Notebook("Science","#ffeb3b", 30, Helpers.stringDataToMillis("2016/07/21"));list.add(a);
				a = new Notebook("Robotics","#ff9800", 78, Helpers.stringDataToMillis("2016/10/29"));list.add(a);Log.d("populateDebugBooks", "successfully wrote all debug books");
				Gson gson = new Gson();

				new Helpers().writeStringToFile(gson.toJson(list), context, "Notebooks.json");
			} catch (Exception e) {
				ArrayList<Notebook> list = new ArrayList<>();

				Notebook a = new Notebook("debug", "#999999", 404, Helpers.stringDataToMillis("2016/01/01"));list.add(a);
				a = new Notebook("Science","#795548", 29, Helpers.stringDataToMillis("2016/11/10"));list.add(a);
				a = new Notebook("ECL","#607d8b", 18, Helpers.stringDataToMillis("2016/12/26"));list.add(a);
				a = new Notebook("Art","#3f51b5", 32, Helpers.stringDataToMillis("2016/08/15"));list.add(a);
				a = new Notebook("Science","#00bcd4", 22, Helpers.stringDataToMillis("2016/01/20"));list.add(a);
				a = new Notebook("Science","#8bc34a", 44, Helpers.stringDataToMillis("2016/12/07"));list.add(a);
				a = new Notebook("Biology","#009688", 65, Helpers.stringDataToMillis("2016/04/29"));list.add(a);
				a = new Notebook("Robotics","#795548", 11, Helpers.stringDataToMillis("2016/06/06"));list.add(a);
				a = new Notebook("English","#e91e63", 65, Helpers.stringDataToMillis("2016/07/18"));list.add(a);
				a = new Notebook("Biology","#9c27b0", 83, Helpers.stringDataToMillis("2016/01/15"));list.add(a);
				a = new Notebook("Economy","#607d8b", 47, Helpers.stringDataToMillis("2016/11/04"));list.add(a);
				a = new Notebook("Politics","#ff9800", 50, Helpers.stringDataToMillis("2016/05/22"));list.add(a);
				a = new Notebook("Politics","#ffc107", 28, Helpers.stringDataToMillis("2016/12/27"));list.add(a);
				a = new Notebook("Politics","#00bcd4", 95, Helpers.stringDataToMillis("2016/11/02"));list.add(a);
				a = new Notebook("Science","#ffeb3b", 30, Helpers.stringDataToMillis("2016/07/21"));list.add(a);
				a = new Notebook("Robotics","#ff9800", 78, Helpers.stringDataToMillis("2016/10/29"));list.add(a);Log.d("populateDebugBooks", "successfully wrote all debug books");

				Log.d("populateDebugBooks", "successfully wrote all debug books");
				Gson gson = new Gson();

				new Helpers().writeStringToFile(gson.toJson(list), context, "Notebooks.json");
			}
		}
	}
}
