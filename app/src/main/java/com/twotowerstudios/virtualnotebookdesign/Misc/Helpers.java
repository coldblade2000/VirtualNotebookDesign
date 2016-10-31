package com.twotowerstudios.virtualnotebookdesign.Misc;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by coldblade2000 on 31/10/16.
 */

public class Helpers {

    public long stringDataToMillis(String date){
        //source: http://stackoverflow.com/questions/9671085/convert-date-to-miliseconds
        long millis;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd, HH:mm");
        formatter.setLenient(false);

        try {
            Date tempDate = formatter.parse(date);
            millis = tempDate.getTime();
        } catch (java.text.ParseException e) {
            return 0;
        }

       /* Date curDate = new Date();
        long curMillis = curDate.getTime();
        String curTime = formatter.format(curDate);

        String oldTime = "05.01.2011, 12:45";
        Date oldDate = formatter.parse(oldTime);
        long oldMillis = oldDate.getTime(); */


        return millis;
    }
    public String millisDateToString(Long millis){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd, HH:mm");
        formatter.setLenient(false);


        return dateString;
    }
    /**public static String formatDateTime(Context context, String timeToFormat) {
     //Source http://stackoverflow.com/questions/7363112/best-way-to-work-with-dates-in-android-sqlite


     String finalDateTime = "";

     SimpleDateFormat iso8601Format = new SimpleDateFormat(
     "yyyy-MM-dd HH:mm:ss");

     Date date = null;
     if (timeToFormat != null) {
     try {
     date = iso8601Format.parse(timeToFormat);
     } catch (ParseException e) {
     date = null;
     } catch (java.text.ParseException e) {
     date = null;
     }

     if (date != null) {
     long when = date.getTime();
     int flags = 0;
     flags |= android.text.format.DateUtils.FORMAT_SHOW_TIME;
     flags |= android.text.format.DateUtils.FORMAT_SHOW_DATE;
     flags |= android.text.format.DateUtils.FORMAT_ABBREV_MONTH;
     flags |= android.text.format.DateUtils.FORMAT_SHOW_YEAR;

     finalDateTime = android.text.format.DateUtils.formatDateTime(context,
     when + TimeZone.getDefault().getOffset(when), flags);
     }
     }
     return finalDateTime;
     }*/
}