package com.me.pipelinedetectionapp.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by linshen on 2019年8月20日15:30:19
 */

public class DateTimeUtil {
    public static final String DATE_FORMAT_YYYYMMDD = "yyyyMMdd";
    public static final String DATE_FORMAT_YYYYMMDD_HH = "yyyyMMdd HH";
    public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
    public static final String DATE_FORMAT_YYYY_MM_DD_CHN = "yyyy年MM月dd日";
    public static final String DATE_FORMAT_YYYYMMDD_HHMMSS = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_MMDD_HHMM = "MM-dd HH:mm";
    public static final String TIME_FORMAT_HHMMSS = "HH:mm:ss";
    private static int daysOfMonth = 0;      //某月的天数
    private static int dayOfWeek = 0;

    //获取到当前时间
    public static String getCurrentDateFromFormat(String format) {

        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = new Date(System.currentTimeMillis());
        String currentTime = sdf.format(date);
        return currentTime;
    }

    public static String getCurrentTime(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }
    /***
     * 返回当前日,如：26
     * @return
     */
    public static int getCurrentDayOfMonth(){
        Calendar calendar= Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_MONTH);
    }


}
