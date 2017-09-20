package com.twotowerstudios.virtualnotebookdesign.Misc;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.twotowerstudios.virtualnotebookdesign.Objects.Notebook;
import com.twotowerstudios.virtualnotebookdesign.Objects.Page;
import com.twotowerstudios.virtualnotebookdesign.R;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Helpers {

	private static final String TAG = "Helpers";
	private static Gson gson = new Gson();
	private static Random random = new Random();
	private static final String POSSIBILITY = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_?";
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
	 * 2: yyyy/MM/dd
	 */
	public static String millisDateToString(Long millis, int format) {
		SimpleDateFormat formatter;
		switch(format){
			case 1:
				formatter = new SimpleDateFormat("yyyy/MM/dd, HH:mm:ss");
				break;
			case 2:
				formatter = new SimpleDateFormat("yyyy/MM/dd");
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
	public static void writeStringToFile(String input, String name) {
        try {
            //outputStream = context.openFileOutput(name, Context.MODE_PRIVATE);
            FileOutputStream outputStream = new FileOutputStream(Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+name);
            outputStream.write(input.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    static void writeStringToFile(String input, File file) {
        try {
            //outputStream = context.openFileOutput(name, Context.MODE_PRIVATE);
            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(input.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	public static String getStringFromName(String filename, Context context) {
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+filename);
		BufferedReader input = null;
		try {
			//input = new BufferedReader(new InputStreamReader(context.openFileInput(filename)));
			input = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String line;
		StringBuilder buffer = new StringBuilder();
		try {
			while ((line = input.readLine()) != null) {
				buffer.append(line + "\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}

	public static String getStringFromFile(File file) {
		BufferedReader input = null;
		try {
			//input = new BufferedReader(new InputStreamReader(context.openFileInput(filename)));
			input = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String line;
		StringBuilder buffer = new StringBuilder();
		try {
			while ((line = input.readLine()) != null) {
				buffer.append(line + "\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}

	public static ArrayList<Notebook> getNotebookList(Context context){
		ArrayList<Notebook> notebookList;
		Log.d("Helpers", "context = "+context.getPackageCodePath());
		String fileString = getStringFromName("Notebooks.json", context);
		int reps = (fileString.length()/4000)+1;
		for (int i = 0; i < reps; i++) {
			if((i+1)*4000>fileString.length()) {
				Log.v("Helpers", "getNotebookList: \n" + fileString.substring(i * 4000, fileString.length()));
			}else{
				Log.v("Helpers", "getNotebookList: \n" + fileString.substring(i * 4000, (i + 1) * 4000));

			}
		}
		if (!fileString.equalsIgnoreCase("")) {
			Type type = new TypeToken<ArrayList<Notebook>>(){}.getType();
			notebookList = gson.fromJson(fileString,type);
		} else {
			Log.d("getNotebookList", "notebooks.json is empty, returning empty arraylist");
			notebookList = new ArrayList<>();
		}
		return notebookList;
	}

	public static void writeListToFile(ArrayList<Notebook> notebookList){
		String outputString = gson.toJson(notebookList);
		writeStringToFile(outputString, "Notebooks.json");
	}
	public static void addToNotebookList(Notebook notebook, Context context){
		ArrayList<Notebook> list = getNotebookList(context);
		boolean bookalreadyexists=false;
		try {
			for(Notebook a: list){
				if(a.getUID16().equals(notebook.getUID16())){
					bookalreadyexists=true;
					//Toast.makeText(context, "Can't add notebook, already exists", Toast.LENGTH_SHORT).show();
					list.set(list.indexOf(a), notebook);
					break;
				}
			}
			for(int i=0; i<list.size(); i++){
				if(list.get(i).getName().equalsIgnoreCase(notebook.getName().toLowerCase())){
					bookalreadyexists=true;
					//Toast.makeText(context, "Can't add notebook, already exists", Toast.LENGTH_SHORT).show();
					list.set(i,notebook);
					break;
				}
			}
		} catch (NullPointerException e) {
			Log.d("NewNotebookFrag","Notebooklist was empty, adding notebook");
		}
		if (!bookalreadyexists) {
			list.add(notebook);
		}
		writeListToFile(list);
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

	private static ArrayList<Integer> getColorAccents(Context context){
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
		return getColorAccents(context).get(colors.indexOf(color));
	}

	public static String generateUniqueId(int length){
		/** BTW to ease confusion:
		 * n prefix = Notebook
		 * p prefix = Page
		 * i prefix = Image file
		 * t prefix = ChildText
		 * d prefix = ChildDriveDoc
		 * c prefix = ChildImage
		 */
		String result="";
		for(int i=0;i<length;i++){
			result=result+POSSIBILITY.charAt(random.nextInt(63));
		}
		return result;
	}

	public static Notebook getNotebookFromUID(String UID16, Context context){
		ArrayList<Notebook> list=Helpers.getNotebookList(context);
		for(Notebook a: list){
			if(a.getUID16().equals(UID16)){
				return a;
			}
		}
		return null;
	}

	public static Page getPageFromUID(String UID16,String parentUID ,Context context){
		ArrayList<Page> list= getNotebookFromUID(parentUID,context).getPages();
		for(Page a:list){
			if(a.getUID().equals(UID16)){
				return a;
			}
		}
		return null;
	}
	public static void addPageFromUID16(String parentUID, Page page, Context context){
		Notebook notebook = getNotebookFromUID(parentUID, context);
		ArrayList<Page> pageList = notebook.getPages();
		boolean doesPageExistAlready = false;
		for(int i=0;i<pageList.size();i++){
			if(pageList.get(i).getUID().equals(page.getUID())){
				pageList.set(i,page);
				doesPageExistAlready=true;
				break;
			}
		}
		if (!doesPageExistAlready) {
			pageList.add(page);
		}
		notebook.setPages(pageList);
		addToNotebookList(notebook, context);
	}
	/**
	This isColorDark method was copied word for word from the "Spectrum" library, written by
	Nathan Walters (and 5 other contributors), published to GitHub with an explicit MIT
	license, which has no restrictions, except that the creator is not liable for anything,
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
	public static File zipFileArray(ArrayList<File> filepaths, String filename, @Nullable Bundle bundle, Context context){

		int BUFFER = 2048;
		File f = null;

		//f = File.createTempFile(filename,".zip");
		//f = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+filename+".zip");
		f = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), filename+".nb");
		Log.d("Helperd", "zipFileArray: "+ f.getAbsolutePath());

		try{
			BufferedInputStream origin = null; // Initialize the input and output streams
			FileOutputStream dest = new FileOutputStream(f);
			ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));
			byte data[] = new byte[BUFFER]; //initializes a buffer for the output stream in order to not run out of memory and lowe the strain on the phone
			if(bundle!=null){
				File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+"j"+generateUniqueId(16)+".json");
				writeStringToFile(bundle.getString("notebookJson"), file);
				Log.d(TAG, file.getAbsolutePath());
				filepaths.add(file);
			}
			for (int i = 0; i < filepaths.size(); i++) { //iterating for every filename
				Log.v("Compress", "Adding: " + filepaths.get(i));
				FileInputStream fi = new FileInputStream(filepaths.get(i));//read the file from the current filepath
				origin = new BufferedInputStream(fi, BUFFER);//makes the fileinputstream have a buffer
				ZipEntry entry = new ZipEntry(filepaths.get(i).getName()
						.substring(filepaths.get(i).getName().lastIndexOf("/") + 1));
				out.putNextEntry(entry); //pure fuckery
				int count;
				while ((count = origin.read(data, 0, BUFFER)) != -1) {
					out.write(data, 0, count);
				}
				origin.close();
			}
			out.finish();
			out.close();
		}catch (Exception e){
			e.printStackTrace();
		}
		if (f != null) {
			f.deleteOnExit();
		}
		return f;
	}
	public void unzip(File inputfile, String _targetLocation) {

		//create target location folder if not exist

		//dirChecker(_targetLocatioan);
		File f = new File(_targetLocation);
		if (!f.isDirectory()) {
			f.mkdirs();
		}
		try {
			FileInputStream fin = new FileInputStream(inputfile);
			ZipInputStream zin = new ZipInputStream(fin);
			ZipEntry ze = null;
			while ((ze = zin.getNextEntry()) != null) {

				//create dir if required while unzipping
				if (ze.isDirectory()) {
					File file = new File(ze.getName());
					if (!file.isDirectory()) {
						file.mkdirs();
					}
				} else {
					FileOutputStream fout = new FileOutputStream(_targetLocation + ze.getName());
					for (int c = zin.read(); c != -1; c = zin.read()) {
						fout.write(c);
					}

					zin.closeEntry();
					fout.close();
				}

			}
			zin.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void unzip(Uri uri,Context context, String _targetLocation) {

		//create target location folder if not exist

		//dirChecker(_targetLocatioan);
		File f = new File(_targetLocation);
		if (!f.isDirectory()) {
			f.mkdirs();
		}
		/**private String readTextFromUri(Uri uri) throws IOException {
		 InputStream inputStream = getContentResolver().openInputStream(uri);
		 BufferedReader reader = new BufferedReader(new InputStreamReader(
		 inputStream));
		 StringBuilder stringBuilder = new StringBuilder();
		 String line;
		 while ((line = reader.readLine()) != null) {
		 stringBuilder.append(line);
		 }
		 fileInputStream.close();
		 parcelFileDescriptor.close();
		 return stringBuilder.toString();
		 }
*/
		 try {
			 InputStream inputStream = context.getContentResolver().openInputStream(uri);
			ZipInputStream zin = new ZipInputStream(inputStream);
			ZipEntry ze = null;
			while ((ze = zin.getNextEntry()) != null) {

				//create dir if required while unzipping
				if (ze.isDirectory()) {
					File file = new File(ze.getName());
					if (!file.isDirectory()) {
						file.mkdirs();
					}
				} else {
					FileOutputStream fout = new FileOutputStream(_targetLocation + ze.getName());
					for (int c = zin.read(); c != -1; c = zin.read()) {
						fout.write(c);
					}

					zin.closeEntry();
					fout.close();
				}
			}
			zin.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
