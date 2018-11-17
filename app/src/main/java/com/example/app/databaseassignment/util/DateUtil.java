package com.example.app.databaseassignment.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtil
{
    public static SimpleDateFormat saveFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.CANADA);
    public static SimpleDateFormat displayFormat = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy", Locale.CANADA);


    public static String parseDateForSaving(String date)
    {
        try
        {
            Date punchedDate = displayFormat.parse(date);
            return saveFormat.format(punchedDate);
        }
        catch (ParseException e)
        {
            return date;
        }
    }

}
