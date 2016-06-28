package com.hugobrisson.teammatetest.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * DateConverter implementation
 */
public class TMDateConverterUtils {
    private static final String TAG = TMDateConverterUtils.class.getSimpleName();
    private static final int DEFAULT_TIME_VALUE = 0;
    private static final int LAST_HOUR = 23;
    private static final int LAST_MINUTE_OR_SECOND = 59;

    /**
     * get date with simple format
     *
     * @param date the date to convert
     * @return converted Date
     */
    public static Date getDateAndTime(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.FRANCE);
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return convertedDate;
    }

    /**
     * get date with simple format
     *
     * @param date the date to convert
     * @return converted Date
     */
    public static Date getDateAndTime(long date) {
        return new Date(date);
    }

    /**
     * get just date.
     *
     * @param sDate string date
     * @return formatted date
     */
    public static String getDate(String sDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
        return dateFormat.format(getDateAndTime(sDate));
    }

    /**
     * get just date.
     *
     * @param sDate string date
     * @return formatted date
     */
    public static String getDate(long sDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
        return dateFormat.format(getDateAndTime(sDate));
    }

    /**
     * get just date.
     *
     * @param sMilli string millisecond
     * @return formatted date
     */
    public static String getDateComplete(long sMilli) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE dd MMMM yyyy", Locale.FRANCE);
        Date date = new Date(sMilli);
        return dateFormat.format(date);
    }

    /**
     * get just date.
     *
     * @param sDate string date
     * @return formatted date
     */
    public static String getDateWithSimpleYear(long sDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy", Locale.FRANCE);
        return dateFormat.format(getDateAndTime(sDate));
    }

    /**
     * get just date.
     *
     * @param sDate string date
     * @return formatted date
     */
    public static String getDateWithoutYear(long sDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM", Locale.FRANCE);
        return dateFormat.format(getDateAndTime(sDate));
    }

    /**
     * get just time.
     *
     * @param sTime string date
     * @return formatted date
     */
    public static String getTime(String sTime) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.FRANCE);
        return dateFormat.format(getDateAndTime(sTime));
    }

    /**
     * get just time.
     *
     * @param sTime string date
     * @return formatted date
     */
    public static String getTime(long sTime) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.FRANCE);
        return dateFormat.format(getDateAndTime(sTime));
    }

    /**
     * get just time.
     *
     * @param sMilli string date
     * @return formatted date
     */
    public static String getTime(long sMilli, boolean replace) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.FRANCE);
        Date date = new Date(sMilli);
        if (replace) {
            return dateFormat.format(date).replace(":", "h");
        }
        return dateFormat.format(date);
    }

    private Calendar getCalendarAtCurrentDate() {
        Calendar tCalendar = new GregorianCalendar();
        tCalendar.set(Calendar.HOUR_OF_DAY, DEFAULT_TIME_VALUE);
        tCalendar.set(Calendar.MINUTE, DEFAULT_TIME_VALUE);
        tCalendar.set(Calendar.SECOND, DEFAULT_TIME_VALUE);
        tCalendar.set(Calendar.MILLISECOND, DEFAULT_TIME_VALUE);
        return tCalendar;
    }


    /**
     * Convert date in string.
     *
     * @param sMilliseconds long dateTime
     * @return string date
     */
    public String getLastUpdateDate(long sMilliseconds) {
        String date;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(sMilliseconds);
        date = simpleDateFormat.format(calendar.getTime());
        return date;
    }

    /**
     * Convert Calendar to server date format
     *
     * @param calendar Calendar
     * @return String formated calendar
     */
    public String getServerDateFormat(Calendar calendar) {
        String date;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.US);
        date = simpleDateFormat.format(calendar.getTime());
        return date;
    }

    /**
     * Convert time in string
     *
     * @param sMilliseconds long dateTime
     * @return string time
     */
    public String getLastUpdateTime(long sMilliseconds) {
        String time;
        SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("HH:mm", Locale.FRANCE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(sMilliseconds);
        time = simpleTimeFormat.format(calendar.getTime());
        time = time.replace(":", "h");
        return time;
    }

    /**
     * Calculate user age with birth date.
     */
    public static int getAge(Date sBirthDate) {
        Calendar a = Calendar.getInstance();
        a.setTime(sBirthDate);
        Calendar b = Calendar.getInstance();
        int diff = (b.get(Calendar.YEAR) - a.get(Calendar.YEAR)) - 1;
        if (b.get(Calendar.MONTH) >= a.get(Calendar.MONTH) && b.get(Calendar.DAY_OF_YEAR) >= a.get(Calendar.DAY_OF_YEAR)) {
            diff++;
        }
        return diff;
    }
}
