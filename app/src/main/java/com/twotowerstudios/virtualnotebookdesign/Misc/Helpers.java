package com.twotowerstudios.virtualnotebookdesign.Misc;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.twotowerstudios.virtualnotebookdesign.Notebook;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by coldblade2000 on 31/10/16.
 */

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

	public String millisDateToString(Long millis) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd, HH:mm:ss");
		formatter.setLenient(false);

		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		return formatter.format(calendar.getTime());
	}

	public void writeStringToFile(String input, Context context, String name) {
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
	public ArrayList<Notebook> getNotebookList(Context context){
		ArrayList<Notebook> notebookList = new ArrayList<Notebook>();
		Gson gson = new Gson();

		String fileString = getStringFromFile("Notebooks.json", context);
		Type type = new TypeToken<ArrayList<Notebook>>(){}.getType();
		notebookList = gson.fromJson(fileString,type);

		return notebookList;
	}
	public void writeListToFile(Context context, List<Notebook> notebookList){
		Gson gson = new Gson();
		String outputString = gson.toJson(notebookList);
		writeStringToFile(outputString, context, "Notebooks.json");
	}
	public void addToNotebookList(Notebook notebook, Context context){
		ArrayList<Notebook> list = getNotebookList(context);
		for(int i=0; i<=list.size(); i++){
			if(list.get(i).name.equalsIgnoreCase(notebook.name.toLowerCase())){
				Toast.makeText(context, "Can't add notebook, already exists", Toast.LENGTH_SHORT);
				list.add(notebook);
				break;
			}
		}
	}
	public void deleteNotebookByName(String name, Context context){
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
}
