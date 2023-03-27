package com.example.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {


    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public static SimpleDateFormat sdfT = new SimpleDateFormat("hh:mm:ss");

    public static SimpleDateFormat sdtf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");

    public static SimpleDateFormat dateUpFile = new SimpleDateFormat("ddMMyyyyhhmmss");

    public static Date today() {
        return new Date();
    }

    // get giờ từ chuỗi
    public static Date parse(String date) {
        try {
            return sdfT.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static String todayStr() {
        return sdf.format(today());
    }

    public static String todayStrTime() {
        return sdtf.format(today());
    }

    public static String dateUpFile() {
        return dateUpFile.format(today());
    }

    public static String formattedDate(Date date) {
        return date != null ? sdf.format(date) : todayStr();
    }

    // giờ đổi ra giây
    public static int second(String timeStr) {
        Calendar cal = Calendar.getInstance();
        Date totalT = DateUtil.parse(timeStr);
        cal.setTime(totalT);
        int sum = (cal.get(Calendar.HOUR_OF_DAY) * 3600) + cal.get(Calendar.MINUTE) * 60 + cal.get(Calendar.SECOND);
        return sum;
    }

    // đổi giây ra giờ

    public static String timeStr(int second) {
        int hours = second / 3600;
        int minutes = (second % 3600) / 60;
        int seconds = second % 60;
        String timeStr = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        return timeStr;
    }


}
