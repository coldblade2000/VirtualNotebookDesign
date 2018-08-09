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
import com.twotowerstudios.virtualnotebookdesign.Objects.ChildBase;
import com.twotowerstudios.virtualnotebookdesign.Objects.Collection;
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
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
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
        switch (format) {
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

    public static long getCurrentTimeInMillis() {
        Calendar cal = Calendar.getInstance();

        return cal.getTimeInMillis();
    }

    public static void writeStringToFile(String input, String name, Context context) {
        try {
            //outputStream = context.openFileOutput(name, Context.MODE_PRIVATE);
            FileOutputStream outputStream = new FileOutputStream(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS) + "/" + name);
            outputStream.write(input.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeStringToFile(String input, File file) {
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
        File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS) + "/" + filename);
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
    public static String getStringFromFileOld(String filename, Context context) {
        Log.i(TAG, "getStringFromFileOld: "+context.getFilesDir().getAbsolutePath());
        File file = new File(filename);
        Log.i(TAG, "getStringFromFileOld: "+file.getAbsolutePath());
        BufferedReader input = null;
        try {
            input = new BufferedReader(new InputStreamReader(context.openFileInput(filename)));
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

    @Deprecated
    public static ArrayList<Notebook> getNotebookList(Context context) {
        if (SharedPrefs.getInt(context, "filestructure") == -1) {
            Log.d("getnotebooklist", "Using Legacy getnoteboolist");
            ArrayList<Notebook> notebookList;
            String fileString = getStringFromFileOld("Notebooks.json", context);
            int reps = (fileString.length() / 4000) + 1;
            for (int i = 0; i < reps; i++) {
                if ((i + 1) * 4000 > fileString.length()) {
                    Log.v("Helpers", "getNotebookList: \n" + fileString.substring(i * 4000, fileString.length()));
                } else {
                    Log.v("Helpers", "getNotebookList: \n" + fileString.substring(i * 4000, (i + 1) * 4000));

                }
            }
            if (!fileString.equalsIgnoreCase("")) {
                Type type = new TypeToken<ArrayList<Notebook>>() {
                }.getType();
                notebookList = gson.fromJson(fileString, type);
            } else {
                Log.d("getNotebookList", "notebooks.json is empty, returning empty arraylist");
                notebookList = new ArrayList<>();
            }
            return notebookList;
        } else {
            Log.d("getnotebooklist", "Using new getnoteboolist");

            File folder = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath());
            File[] filelist = folder.listFiles();
            ArrayList<Notebook> notebookList = new ArrayList<>();
            for (File f : filelist) {
                if (f.getName().substring(0, 1).equals("j") && !f.isDirectory()) {
                    String json = getStringFromFile(f);
                    Log.v("JSON helper", json);
                    Notebook notebook = gson.fromJson(json, Notebook.class);
                    notebookList.add(notebook);
                }
            }
            return notebookList;
        }
    }

    public static void writeListToFile(ArrayList<Notebook> notebookList, Context context) {
        String outputString = gson.toJson(notebookList);
        writeStringToFile(outputString, "Notebooks.json", context);
    }

    public static void writeNotebookToFile(Notebook notebook, Context context) {
        File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath() + "/j" + notebook.getUID16().substring(1) + ".json");
        writeStringToFile(gson.toJson(notebook), file);
        Log.d("writelisttofile", "Wrote notebook '" + notebook.getName() + "' in location: " + file.getAbsolutePath());
    }

    @Deprecated
    public static void addToNotebookList(Notebook notebook, Context context) {
        ArrayList<Notebook> list = getNotebookList(context);
        boolean bookalreadyexists = false;
        try {
            for (Notebook a : list) {
                if (a.getUID16().equals(notebook.getUID16())) {
                    bookalreadyexists = true;
                    //Toast.makeText(context, "Can't add notebook, already exists", Toast.LENGTH_SHORT).show();
                    list.set(list.indexOf(a), notebook);
                    break;
                }
            }
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getName().equalsIgnoreCase(notebook.getName().toLowerCase())) {
                    bookalreadyexists = true;
                    //Toast.makeText(context, "Can't add notebook, already exists", Toast.LENGTH_SHORT).show();
                    list.set(i, notebook);
                    break;
                }
            }
        } catch (NullPointerException e) {
            Log.d("NewNotebookFrag", "Notebooklist was empty, adding notebook");
        }
        if (!bookalreadyexists) {
            list.add(notebook);
        }
        writeListToFile(list, context);
    }

    public static void addToNotebookList(Notebook notebook, Context context, String collectionUID) {
        Collection collection = getCollectionFromUID(collectionUID, context);

        ArrayList<Notebook> list = getNotebooksFromCollection(collection, context);
        boolean bookalreadyexists = false;
        try {
            for (Notebook a : list) {
                if (a.getUID16().equals(notebook.getUID16())) {
                    bookalreadyexists = true;
                    //Toast.makeText(context, "Can't add notebook, already exists", Toast.LENGTH_SHORT).show();
                    list.set(list.indexOf(a), notebook);
                    break;
                }
            }
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getName().equalsIgnoreCase(notebook.getName().toLowerCase())) {
                    bookalreadyexists = true;
                    //Toast.makeText(context, "Can't add notebook, already exists", Toast.LENGTH_SHORT).show();
                    list.set(i, notebook);
                    break;
                }
            }
        } catch (NullPointerException e) {
            Log.d("NewNotebookFrag", "Notebooklist of " + collectionUID + " was empty, adding notebook");
        }
        if (!bookalreadyexists) {
            list.add(notebook);
            ArrayList<String> uids = new ArrayList<>();
            for(Notebook a:list){
                uids.add(a.getUID16());
            }
            collection.setContentUIDs(uids);
            writeOneCollectionToFile(collection, context);
        }

    }

    public static ArrayList<Integer> getPossibleColors(Context context) {
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(ContextCompat.getColor(context, R.color.md_red_500));
        colors.add(ContextCompat.getColor(context, R.color.md_pink_500));
        colors.add(ContextCompat.getColor(context, R.color.md_purple_500));
        colors.add(ContextCompat.getColor(context, R.color.md_deep_purple_500));
        colors.add(ContextCompat.getColor(context, R.color.md_indigo_500));
        colors.add(ContextCompat.getColor(context, R.color.md_blue_500));
        colors.add(ContextCompat.getColor(context, R.color.md_light_blue_500));
        colors.add(ContextCompat.getColor(context, R.color.md_cyan_500));
        colors.add(ContextCompat.getColor(context, R.color.md_teal_500));
        colors.add(ContextCompat.getColor(context, R.color.md_green_500));
        colors.add(ContextCompat.getColor(context, R.color.md_light_green_500));
        colors.add(ContextCompat.getColor(context, R.color.md_lime_500));
        colors.add(ContextCompat.getColor(context, R.color.md_yellow_500));
        colors.add(ContextCompat.getColor(context, R.color.md_amber_500));
        colors.add(ContextCompat.getColor(context, R.color.md_orange_500));
        colors.add(ContextCompat.getColor(context, R.color.md_deep_orange_500));
        colors.add(ContextCompat.getColor(context, R.color.md_brown_500));
        colors.add(ContextCompat.getColor(context, R.color.md_grey_500));
        colors.add(ContextCompat.getColor(context, R.color.md_black_1000));
        return colors;
    }

    private static ArrayList<Integer> getColorAccents(Context context) {
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(ContextCompat.getColor(context, R.color.md_light_blue_A200));
        colors.add(ContextCompat.getColor(context, R.color.md_green_A200));
        colors.add(ContextCompat.getColor(context, R.color.md_green_A200));
        colors.add(ContextCompat.getColor(context, R.color.md_orange_A200));
        colors.add(ContextCompat.getColor(context, R.color.md_amber_A200));
        colors.add(ContextCompat.getColor(context, R.color.md_amber_A200));
        colors.add(ContextCompat.getColor(context, R.color.md_pink_A200));
        colors.add(ContextCompat.getColor(context, R.color.md_orange_A200));
        colors.add(ContextCompat.getColor(context, R.color.md_deep_orange_A200));
        colors.add(ContextCompat.getColor(context, R.color.md_deep_orange_A200));
        colors.add(ContextCompat.getColor(context, R.color.md_pink_A200));
        colors.add(ContextCompat.getColor(context, R.color.md_red_A200));
        colors.add(ContextCompat.getColor(context, R.color.md_purple_A200));
        colors.add(ContextCompat.getColor(context, R.color.md_blue_A200));
        colors.add(ContextCompat.getColor(context, R.color.md_light_blue_A200));
        colors.add(ContextCompat.getColor(context, R.color.md_deep_orange_A200));
        colors.add(ContextCompat.getColor(context, R.color.md_indigo_A200));
        colors.add(ContextCompat.getColor(context, R.color.md_pink_A200));
        colors.add(ContextCompat.getColor(context, R.color.md_grey_200));

        return colors;
    }

    public static int getSingleColorAccent(Context context, int color) {
        ArrayList<Integer> colors = getPossibleColors(context);
        //Log.d(TAG, "colors.size()== "+colors.size());
        return getColorAccents(context).get(colors.indexOf(color));
    }

    public static String generateUniqueId(int length) {
        /* BTW to ease confusion:
         * n prefix = Notebook
         * p prefix = Page
         * i prefix = Image file
         * t prefix = ChildText
         * d prefix = ChildDriveDoc
         * c prefix = ChildImage
         * j prefix = JSON
         * z prefix = Compressed Notebook
         */
        String result = "";
        for (int i = 0; i < length; i++) {
            result = result + POSSIBILITY.charAt(random.nextInt(63));
        }
        return result;
    }

    public static Notebook getNotebookFromUID(String UID16, Context context) {
        //Log.d(TAG, "getNotebookFromUID: UID = "+UID16);
        if (SharedPrefs.getInt(context, "filestructure") == 1) {
            File directory = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath());
            for (File a : directory.listFiles()) {
                //Log.d(TAG, "getNotebookFromUID: "+ a.getName());
                //Log.d(TAG, "getNotebookFromUID: "+ a.getName().substring(0,16));
                if (a.getName().length() >= 17 && a.getName().substring(1, 17).equals(UID16.substring(1))) {
                    //Log.d(TAG, "getNotebookFromUID: found notebook match");
                    return gson.fromJson(getStringFromFile(a), Notebook.class);
                }
            }
            Log.w(TAG, "getNotebookFromUID: didn't find the notebook");

        } else {
            ArrayList<Notebook> list = Helpers.getNotebookList(context);
            for (Notebook a : list) {
                if (a.getUID16().equals(UID16)) {
                    return a;
                }
            }
        }
        return null;
    }

    public static Page getPageFromUID(String UID16, String parentUID, Context context) {
        ArrayList<Page> list = getNotebookFromUID(parentUID, context).getPages();
        for (Page a : list) {
            if (a.getUID().equals(UID16)) {
                return a;
            }
        }
        return null;
    }

    public static void addPageFromUID16(String parentUID, Page page, Context context) {
        Notebook notebook = getNotebookFromUID(parentUID, context);
        ArrayList<Page> pageList = notebook.getPages();
        boolean doesPageExistAlready = false;
        for (int i = 0; i < pageList.size(); i++) {
            if (pageList.get(i).getUID().equals(page.getUID())) {
                pageList.set(i, page);
                doesPageExistAlready = true;
                break;
            }
        }
        if (!doesPageExistAlready) {
            pageList.add(page);
        }
        notebook.setPages(pageList);
        writeNotebookToFile(notebook, context);
    }

    /**
     * This isColorDark method was copied word for word from the "Spectrum" library, written by
     * Nathan Walters (and 5 other contributors), published to GitHub with an explicit MIT
     * license, which has no restrictions, except that the creator is not liable for anything,
     * and both the license and copyright notices must be written somewhere. This will be done
     * in the journal, references.txt and report.
     * <p>
     * The MIT License (MIT)
     * <p>
     * Copyright (c) 2016 The Blue Alliance
     * <p>
     * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
     * <p>
     * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
     * <p>
     * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
     * <p>
     * <p>
     * Walters, N. (2016). the-blue-alliance/spectrum. GitHub. Retrieved 15 November 2016, from https://github.com/the-blue-alliance/spectrum/blob/master/spectrum/src/main/java/com/thebluealliance/spectrum/internal/ColorUtil.java
     */
    public static boolean isColorDark(int color) {
        double brightness = Color.red(color) * 0.299 +
                Color.green(color) * 0.587 +
                Color.blue(color) * 0.114;
        return brightness < 160;
    }

    public static File zipFileArray(ArrayList<File> filepaths, String filename, @Nullable Bundle bundle, Context context) {
        int BUFFER = 2048;
        File f = null;
        //f = File.createTempFile(filename,".zip");
        //f = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+filename+".zip");
        f = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), filename + ".nb");
        f.deleteOnExit();
        Log.d("Helpers", "zipFileArray: " + f.getAbsolutePath());
        try {
            BufferedInputStream origin = null; // Initialize the input and output streams
            FileOutputStream dest = new FileOutputStream(f);
            ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));
            byte data[] = new byte[BUFFER]; //initializes a buffer for the output stream in order to not run out of memory and lowe the strain on the phone
            if (bundle != null) {
                File file = new File(context.getFilesDir().getAbsolutePath() + "/" + "ncd" + bundle.getString("UID16", "ERROR").substring(1) + ".json");
                file.deleteOnExit();
                writeStringToFile(bundle.getString("notebookJson"), file);
                Log.d(TAG, file.getAbsolutePath());
                filepaths.add(0, file);
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
                out.closeEntry();
                fi.close();
                origin.close();
            }
            out.finish();
            out.close();
            dest.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        f.deleteOnExit();
        return f;
    }

    public static void unzip(Uri uri, Context context, String _targetLocation) {
        //create target location folder if not exist
        //dirChecker(_targetLocatioan);
        File f = new File(_targetLocation);
        if (!f.isDirectory()) {
            f.mkdirs();
        }
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            ZipInputStream zin = new ZipInputStream(inputStream);
            ZipEntry ze;

            while ((ze = zin.getNextEntry()) != null) {
                Log.d(TAG, "Starting zip entry: " + ze.getName());

                //create dir if required while unzipping
                if (ze.isDirectory()) {
                    File file = new File(ze.getName());
                    if (!file.isDirectory()) {
                        file.mkdirs();
                    }
                } else {
                    BufferedInputStream bufferedInputStream = new BufferedInputStream(zin);
                    FileOutputStream fout = new FileOutputStream(_targetLocation + "/" + ze.getName());
                    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fout);
                    byte[] arr = new byte[1024 * 1024];
                    int available;
                    while ((available = bufferedInputStream.read(arr)) > 0) {
                        bufferedOutputStream.write(arr, 0, available);
                    }
                    bufferedOutputStream.close();
                    zin.closeEntry();
                    fout.close();
                }
            }
            zin.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*public void changeFileStructure(Context context) {
        ArrayList<Notebook> notebooklist = getNotebookList(context);
        for (Notebook a : notebooklist) {
            File newFolderDoc = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath() + "/" + a.getUID16() + "/");
            if (!newFolderDoc.isDirectory()) {
                newFolderDoc.mkdir();
            }
            File newFolderPIC = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/" + a.getUID16() + "/");
            if (!newFolderPIC.isDirectory()) {
                newFolderPIC.mkdir();
            }
            ArrayList<File> fileList = new ArrayList<>();
            for (Page b : a.getPages()) {
                for (ChildBase c : b.getContent()) {
                    if (c.getChildType() == 1) {
                        File image = c.getFile();
                        File dest = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/" + a.getUID16() + "/" + image.getName());
                        Log.d("changeFileStructure", "Moving " + image.getAbsolutePath() + "to " + dest.getAbsolutePath());
                        if (image.renameTo(dest)) {
                            c.setPath(dest.getAbsolutePath());
                        }
                    }
                }
            }
            String notebookjson = gson.toJson(a);
            File notebookjsonfolder = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath(), a.getUID16() + ".json");
            writeStringToFile(notebookjson, notebookjsonfolder);
        }
        SharedPrefs.setInt(context, "filestructure", 2);
    }*/

    public static void deleteNotebookByUID(String UID16, Context context) {
        Notebook notebook = getNotebookFromUID(UID16, context);
        /**for (Page a : notebook.getPages()) {
         for (ChildBase b : a.getContent()) {
         if (b.getChildType() == 1) {
         String path = b.getFile().getAbsolutePath();
         if( b.getFile().delete()){
         Log.d(TAG, "deleteNotebookByUID: deleted file: "+path);
         }else{
         Log.e(TAG, "deleteNotebookByUID: couldn't delete file: "+path);
         }
         }
         }
         }*/
        File folder = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath(), "n" + UID16.substring(1));
        try {
            for (File a : folder.listFiles()) {
                a.delete();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        File json = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath(), "j" + UID16.substring(1) + ".json");
        if (json.delete()) {
            Log.d(TAG, "deleteNotebookByUID: deleted file: " + json.getAbsolutePath());
        } else {
            Log.e(TAG, "deleteNotebookByUID: couldn't delete file: " + json.getAbsolutePath());
        }
        if (folder.delete()) {
            Log.d(TAG, "deleteNotebookByUID: deleted file: " + folder.getAbsolutePath());
        } else {
            Log.e(TAG, "deleteNotebookByUID: couldn't delete file: " + folder.getAbsolutePath());
        }
    }

    public static ArrayList<Collection> getCollections(Context context) {
        File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath());
        for (File a : file.listFiles()) {
            if ((a.getName().equals("Collections.json"))) {
                return gson.fromJson(getStringFromFile(a), new TypeToken<ArrayList<Collection>>() {
                }.getType());
            }
        }
        return null;
    }

    public static void writeCollectionsToFile(ArrayList<Collection> collections, Context context) {
        File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath(), "Collections.json");
        writeStringToFile(gson.toJson(collections), file);
        Log.d("writelisttofile", "Wrote collections in location: " + file.getAbsolutePath());
    }

    public static ArrayList<Notebook> getNotebooksFromCollection(Collection collection, Context context) {
        ArrayList<Notebook> notebooks = new ArrayList<>();
        Notebook book;
        for (String a : collection.getContentUIDs()) {
            //Log.d(TAG, "getNotebooksFromCollection: ContentUID = "+a);
            book = getNotebookFromUID(a, context);
            notebooks.add(book);
            Log.d(TAG, "Name: " + book.getName());
        }
        return notebooks;
    }

    public static Collection getCollectionFromUID(String UID8, Context context) {
        for (Collection a : getCollections(context)) {
            if (a.getUID8().equals(UID8)) {
                return a;
            }
        }
        return null;
    }

    public static void writeOneCollectionToFile(Collection collection, Context context) {
        ArrayList<Collection> collections = getCollections(context);
        boolean hasBeenUpdated = false;
        for (int i = 0; i < collections.size(); i++) {
            if (collections.get(i).getUID8().equals(collection.getUID8())) {
                collections.set(i, collection);
                hasBeenUpdated = true;
                break;
            }
        }
        if (!hasBeenUpdated) {
            collections.add(collection);
        }
        writeCollectionsToFile(collections, context);
    }
}
