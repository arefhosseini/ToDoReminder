package com.fearefull.todoreminder.utils;

import com.fearefull.todoreminder.data.model.other.MyTime;
import com.fearefull.todoreminder.data.model.other.TimeType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public final class TimeUtils {

    private TimeUtils() {

    }

    public static ArrayList<String> get12Hours() {
        return getNumbers(1, 12);
    }

    public static ArrayList<String> get24Hours() {
        return getNumbers(1, 24);
    }

    private static ArrayList<String> getNumbers(int start, int end) {
        ArrayList<String> list = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            list.add(String.valueOf(i));
        }
        return list;
    }

    private static ArrayList<String> getNumbersWithZero(int start, int end) {
        ArrayList<String> list = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            if (i < 10)
                list.add("0" + i);
            else
                list.add(String.valueOf(i));
        }
        return list;
    }

    public static ArrayList<String> getMinutes() {
        return getNumbersWithZero(0, 59);
    }

    public static ArrayList<TimeType> getTimeTypes() {
        ArrayList<TimeType> types = new ArrayList<>();
        types.add(TimeType.AM);
        types.add(TimeType.PM);
        return types;
    }

    public static ArrayList<String> getStringTimeTypes() {
        ArrayList<String> stringTypes = new ArrayList<>();
        stringTypes.add(TimeType.AM.getPersianText());
        stringTypes.add(TimeType.PM.getPersianText());
        return stringTypes;
    }

    public static Date getTime(MyTime myTime) {
        String time = myTime.getHour() + ":" + myTime.getMinute() + " " + myTime.getTimeType().getEnglishText();
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm aa", Locale.US);
        try {
            return dateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }
}
