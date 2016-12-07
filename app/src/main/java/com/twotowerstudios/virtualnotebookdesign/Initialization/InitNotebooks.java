package com.twotowerstudios.virtualnotebookdesign.Initialization;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.gson.Gson;
import com.twotowerstudios.virtualnotebookdesign.Misc.Helpers;
import com.twotowerstudios.virtualnotebookdesign.Misc.SharedPrefs;
import com.twotowerstudios.virtualnotebookdesign.Objects.Notebook;
import com.twotowerstudios.virtualnotebookdesign.Objects.Page;
import com.twotowerstudios.virtualnotebookdesign.R;

import java.util.ArrayList;
import java.util.Random;

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

				Notebook a = new Notebook("debug", ContextCompat.getColor(context, R.color.md_grey_500), 404, Helpers.stringDataToMillis("2016/01/01"),context);list.add(a);
				a = new Notebook("Science",ContextCompat.getColor(context, R.color.md_brown_500), 29, Helpers.stringDataToMillis("2016/11/10"),context);list.add(a);
				a = new Notebook("ECL",ContextCompat.getColor(context, R.color.md_blue_grey_500), 18, Helpers.stringDataToMillis("2016/12/26"),context);list.add(a);
				a = new Notebook("Art",ContextCompat.getColor(context, R.color.md_indigo_500), 32, Helpers.stringDataToMillis("2016/08/15"),context);list.add(a);
				a = new Notebook("Science",ContextCompat.getColor(context, R.color.md_cyan_500), 22, Helpers.stringDataToMillis("2016/01/20"),context);list.add(a);
				a = new Notebook("Science",ContextCompat.getColor(context, R.color.md_lime_500), 44, Helpers.stringDataToMillis("2016/12/07"),context);list.add(a);
				a = new Notebook("Biology",ContextCompat.getColor(context, R.color.md_teal_500), 65, Helpers.stringDataToMillis("2016/04/29"),context);list.add(a);
				a = new Notebook("Robotics",ContextCompat.getColor(context, R.color.md_brown_500), 11, Helpers.stringDataToMillis("2016/06/06"),context);list.add(a);
				a = new Notebook("English",ContextCompat.getColor(context, R.color.md_pink_500), 65, Helpers.stringDataToMillis("2016/07/18"),context);list.add(a);
				a = new Notebook("Biology",ContextCompat.getColor(context, R.color.md_purple_500), 83, Helpers.stringDataToMillis("2016/01/15"),context);list.add(a);
				a = new Notebook("Economy",ContextCompat.getColor(context, R.color.md_blue_grey_500), 47, Helpers.stringDataToMillis("2016/11/04"),context);list.add(a);
				a = new Notebook("Politics",ContextCompat.getColor(context, R.color.md_orange_500), 50, Helpers.stringDataToMillis("2016/05/22"),context);list.add(a);
				a = new Notebook("Politics",ContextCompat.getColor(context, R.color.md_amber_500), 28, Helpers.stringDataToMillis("2016/12/27"),context);list.add(a);
				a = new Notebook("Politics",ContextCompat.getColor(context, R.color.md_cyan_500), 95, Helpers.stringDataToMillis("2016/11/02"),context);list.add(a);
				a = new Notebook("Science",ContextCompat.getColor(context, R.color.md_yellow_500), 30, Helpers.stringDataToMillis("2016/07/21"),context);list.add(a);
				a = new Notebook("Robotics",ContextCompat.getColor(context, R.color.md_orange_500), 78, Helpers.stringDataToMillis("2016/10/29"),context);list.add(a);Log.d("populateDebugBooks", "successfully wrote all debug books");
				Gson gson = new Gson();

				new Helpers().writeStringToFile(gson.toJson(list), context, "Notebooks.json");
			} catch (Exception e) {
				ArrayList<Notebook> list = new ArrayList<>();

				Notebook a = new Notebook("debug", ContextCompat.getColor(context, R.color.md_grey_500), 404, Helpers.stringDataToMillis("2016/01/01"),context);list.add(a);
				a = new Notebook("Science",ContextCompat.getColor(context, R.color.md_brown_500), 29, Helpers.stringDataToMillis("2016/11/10"),context);list.add(a);
				a = new Notebook("ECL",ContextCompat.getColor(context, R.color.md_yellow_500), 18, Helpers.stringDataToMillis("2016/12/26"),context);list.add(a);
				a = new Notebook("Art",ContextCompat.getColor(context, R.color.md_indigo_500), 32, Helpers.stringDataToMillis("2016/08/15"),context);list.add(a);
				a = new Notebook("Science",ContextCompat.getColor(context, R.color.md_cyan_500), 22, Helpers.stringDataToMillis("2016/01/20"),context);list.add(a);
				a = new Notebook("Science",ContextCompat.getColor(context, R.color.md_lime_500), 44, Helpers.stringDataToMillis("2016/12/07"),context);list.add(a);
				a = new Notebook("Biology",ContextCompat.getColor(context, R.color.md_teal_500), 65, Helpers.stringDataToMillis("2016/04/29"),context);list.add(a);
				a = new Notebook("Robotics",ContextCompat.getColor(context, R.color.md_brown_500), 11, Helpers.stringDataToMillis("2016/06/06"),context);list.add(a);
				a = new Notebook("English",ContextCompat.getColor(context, R.color.md_pink_500), 65, Helpers.stringDataToMillis("2016/07/18"),context);list.add(a);
				a = new Notebook("Biology",ContextCompat.getColor(context, R.color.md_purple_500), 83, Helpers.stringDataToMillis("2016/01/15"),context);list.add(a);
				a = new Notebook("Economy",ContextCompat.getColor(context, R.color.md_brown_500), 47, Helpers.stringDataToMillis("2016/11/04"),context);list.add(a);
				a = new Notebook("Politics",ContextCompat.getColor(context, R.color.md_orange_500), 50, Helpers.stringDataToMillis("2016/05/22"),context);list.add(a);
				a = new Notebook("Politics",ContextCompat.getColor(context, R.color.md_amber_500), 28, Helpers.stringDataToMillis("2016/12/27"),context);list.add(a);
				a = new Notebook("Politics",ContextCompat.getColor(context, R.color.md_cyan_500), 95, Helpers.stringDataToMillis("2016/11/02"),context);list.add(a);
				a = new Notebook("Science",ContextCompat.getColor(context, R.color.md_yellow_500), 30, Helpers.stringDataToMillis("2016/07/21"),context);list.add(a);
				a = new Notebook("Robotics",ContextCompat.getColor(context, R.color.md_orange_500), 78, Helpers.stringDataToMillis("2016/10/29"),context);list.add(a);Log.d("populateDebugBooks", "successfully wrote all debug books");

				Log.d("populateDebugBooks", "successfully wrote all debug books");
				Gson gson = new Gson();

				new Helpers().writeStringToFile(gson.toJson(list), context, "Notebooks.json");
			}
		}
	}
	public static ArrayList<Page> populateDebugNotebookPages(ArrayList<Page> list, int reps){
		String TAG="InitNotebooks";
		if (list == null||list.size()==0) {
			Random r = new Random();
			reps = r.nextInt(reps);
			String[] names = {"Art","Biology","Chemistry",
					"DT","ECL","English",
					"History","Math","PE",
					"Physics","Science","Spanish",
					"Philosphy","Geography","Politics",
					"Economy","I.C.T.","Robotics"};
			int namesLength = names.length;
			for(int i=0;i<reps;i++){
                Page a = new Page(names[r.nextInt(namesLength)],r.nextInt(100)); list.add(a);
				Log.d(TAG, "populateDebugNotebookPages: added new page.");
			}
			Log.d(TAG, "populateDebugNotebookPages: returned list");
			return list;
		} else {
			Log.d("Helpers","PopulateDebugNotebookPages: list is not null or size != 0, returning list as-is");
			return list;
		}
	}
}
