package com.twotowerstudios.virtualnotebookdesign.Misc;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.twotowerstudios.virtualnotebookdesign.Objects.Notebook;
import com.twotowerstudios.virtualnotebookdesign.R;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Helpers {

	public static long stringDataToMillis(String date) {
		//source: http://stackoverflow.com/questions/9671085/convert-date-to-miliseconds
		long millis;
		SimpleDateFormat formatter;
		formatter = new SimpleDateFormat("yyyy/MM/dd");
		formatter.setLenient(true);
		Log.d("string2millis", "Date is: " + date);
		try {
			Date tempDate = formatter.parse(date);
			millis = tempDate.getTime();
			Log.d("string2millis", "Millis is: " + millis);
		} catch (java.text.ParseException e) {
			return 0;
		}
		return millis;
	}

	/**
	 * Format meanings
	 * 1: yyyy/MM/dd, HH:mm:ss
	 * 2:
	 */
	public static String millisDateToString(Long millis, int format) {
		SimpleDateFormat formatter;
		switch(format){
			case 1:
				formatter = new SimpleDateFormat("yyyy/MM/dd, HH:mm:ss");
				break;
			case 2:
				formatter = new SimpleDateFormat("yyyy/MM/dd, HH:mm");
				break;
			default:
				formatter = new SimpleDateFormat("yyyy/MM/dd, HH:mm:ss");
		}

		formatter.setLenient(false);

		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		return formatter.format(calendar.getTime());
	}
	public static long getCurrentTimeInMillis(){
		Calendar cal = Calendar.getInstance();

		return cal.getTimeInMillis();
	}
	public static void writeStringToFile(String input, Context context, String name) {
		FileOutputStream outputStream;
		try {
			outputStream = context.openFileOutput(name, Context.MODE_PRIVATE);
			outputStream.write(input.getBytes());
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getStringFromFile(String filename, Context context) {
		BufferedReader input = null;
		try {
			input = new BufferedReader(new InputStreamReader(context.openFileInput(filename)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String line;
		StringBuffer buffer = new StringBuffer();
		try {
			while ((line = input.readLine()) != null) {
				buffer.append(line + "\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}


		return buffer.toString();

	}
	public  ArrayList<Notebook> getNotebookList(Context context){
		ArrayList<Notebook> notebookList;
		Gson gson = new Gson();

		String fileString = getStringFromFile("Notebooks.json", context);
		Type type = new TypeToken<ArrayList<Notebook>>(){}.getType();
		notebookList = gson.fromJson(fileString,type);

		return notebookList;
	}
	public static void writeListToFile(Context context, ArrayList<Notebook> notebookList){
		Gson gson = new Gson();
		String outputString = gson.toJson(notebookList);
		writeStringToFile(outputString, context, "Notebooks.json");
	}
	public void addToNotebookList(Notebook notebook, Context context){
		ArrayList<Notebook> list = getNotebookList(context);
		for(int i=0; i<list.size(); i++){
			if(list.get(i).name.equalsIgnoreCase(notebook.name.toLowerCase())){
				Toast.makeText(context, "Can't add notebook, already exists", Toast.LENGTH_SHORT).show();
				break;
			}
		}
		list.add(notebook);
		writeListToFile(context,list);
	}
	public static void deleteNotebookByName(String name, Context context){
		Toast.makeText(context, "deleteNotebookByName invoked", Toast.LENGTH_SHORT);
		ArrayList<Notebook> list = new Helpers().getNotebookList(context);
		for(int i = 0; i <= list.size(); i++){
			if(list.get(i).name.equalsIgnoreCase(name)){

				String x = "Deleted the following notebook: "+list.get(i).name;
				list.remove(i);
				Toast.makeText(context, x,Toast.LENGTH_SHORT);
				break;
			}
		}
	}

	public static ArrayList<Integer> getPossibleColors(Context context){
		ArrayList<Integer> colors = new ArrayList<>();
		colors.add(ContextCompat.getColor(context,R.color.md_red_500));
		colors.add(ContextCompat.getColor(context,R.color.md_pink_500));
		colors.add(ContextCompat.getColor(context,R.color.md_purple_500));
		colors.add(ContextCompat.getColor(context,R.color.md_deep_purple_500));
		colors.add(ContextCompat.getColor(context,R.color.md_indigo_500));
		colors.add(ContextCompat.getColor(context,R.color.md_blue_500));
		colors.add(ContextCompat.getColor(context,R.color.md_light_blue_500));
		colors.add(ContextCompat.getColor(context,R.color.md_cyan_500));
		colors.add(ContextCompat.getColor(context,R.color.md_teal_500));
		colors.add(ContextCompat.getColor(context,R.color.md_green_500));
		colors.add(ContextCompat.getColor(context,R.color.md_light_green_500));
		colors.add(ContextCompat.getColor(context,R.color.md_lime_500));
		colors.add(ContextCompat.getColor(context,R.color.md_yellow_500));
		colors.add(ContextCompat.getColor(context,R.color.md_amber_500));
		colors.add(ContextCompat.getColor(context,R.color.md_orange_500));
		colors.add(ContextCompat.getColor(context,R.color.md_deep_orange_500));
		colors.add(ContextCompat.getColor(context,R.color.md_brown_500));
		colors.add(ContextCompat.getColor(context,R.color.md_grey_500));

		return colors;
	}

	public static ArrayList<Integer> getColorAccents(Context context){
		ArrayList<Integer> colors = new ArrayList<>();
		colors.add(ContextCompat.getColor(context,R.color.md_light_blue_A200));
		colors.add(ContextCompat.getColor(context,R.color.md_green_A200));
		colors.add(ContextCompat.getColor(context,R.color.md_green_A200));
		colors.add(ContextCompat.getColor(context,R.color.md_orange_A200));
		colors.add(ContextCompat.getColor(context,R.color.md_amber_A200));
		colors.add(ContextCompat.getColor(context,R.color.md_amber_A200));
		colors.add(ContextCompat.getColor(context,R.color.md_pink_A200));
		colors.add(ContextCompat.getColor(context,R.color.md_orange_A200));
		colors.add(ContextCompat.getColor(context,R.color.md_deep_orange_A200));
		colors.add(ContextCompat.getColor(context,R.color.md_deep_orange_A200));
		colors.add(ContextCompat.getColor(context,R.color.md_pink_A200));
		colors.add(ContextCompat.getColor(context,R.color.md_red_A200));
		colors.add(ContextCompat.getColor(context,R.color.md_purple_A200));
		colors.add(ContextCompat.getColor(context,R.color.md_blue_A200));
		colors.add(ContextCompat.getColor(context,R.color.md_light_blue_A200));
		colors.add(ContextCompat.getColor(context,R.color.md_deep_orange_A200));
		colors.add(ContextCompat.getColor(context,R.color.md_indigo_A200));
		colors.add(ContextCompat.getColor(context,R.color.md_pink_A200));

		return colors;
	}
	public static int getSingleColorAccent(Context context, int color){
		ArrayList<Integer> colors = getPossibleColors(context);
		String TAG = "getSingleColorAccent";
		Log.d(TAG, "colors.size()== "+colors.size());
		int accentColor = getColorAccents(context).get(colors.indexOf(color));
		return accentColor;
	}
	/**
	This isColorDark method was copied word for word from the "Spectrum" library, written by
	Nathan Walters (and 5 other contributors), published to GitHub with an explicit MIT
	license, which has no restrictions, except that the creater is not liable for anything,
	and both the license and copyright notices must be written somewhere. This will be done
	 in the journal, references.txt and report.

	 The MIT License (MIT)

	 Copyright (c) 2016 The Blue Alliance

	 Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

	 The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

	 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

	 *
	 * Walters, N. (2016). the-blue-alliance/spectrum. GitHub. Retrieved 15 November 2016, from https://github.com/the-blue-alliance/spectrum/blob/master/spectrum/src/main/java/com/thebluealliance/spectrum/internal/ColorUtil.java
	 */
	 public static boolean isColorDark(int color){
		double brightness = Color.red(color) * 0.299 +
				Color.green(color) * 0.587 +
				Color.blue(color) * 0.114;
		return brightness < 160;
	}
}
