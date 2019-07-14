package com.fearefull.todoreminder.utils;

import com.fearefull.todoreminder.data.model.other.MonthType;
import com.fearefull.todoreminder.data.model.other.MyTime;
import com.fearefull.todoreminder.data.model.other.RepeatType;
import com.fearefull.todoreminder.data.model.other.TimeType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public final class AlarmUtils {

    private AlarmUtils() {

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

    public static ArrayList<String> getTimeTypes() {
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

    public static ArrayList<String> getYears() {
        return getNumbers(AppConstants.START_YEAR, AppConstants.END_YEAR);
    }

    public static ArrayList<String> getMonths() {
        ArrayList<String> stringTypes = new ArrayList<>();
        stringTypes.add(MonthType.FARVARDIN.getText());
        stringTypes.add(MonthType.ORDIBEHESHT.getText());
        stringTypes.add(MonthType.KHORDAD.getText());
        stringTypes.add(MonthType.TIR.getText());
        stringTypes.add(MonthType.MORDAD.getText());
        stringTypes.add(MonthType.SHAHRIVAR.getText());
        stringTypes.add(MonthType.MEHR.getText());
        stringTypes.add(MonthType.ABAN.getText());
        stringTypes.add(MonthType.AZAR.getText());
        stringTypes.add(MonthType.DEY.getText());
        stringTypes.add(MonthType.BAHMAN.getText());
        stringTypes.add(MonthType.ESFAND.getText());
        return stringTypes;
    }

    public static ArrayList<String> getDays(MonthType monthType) {
        return getNumbers(1, monthType.getDays());
    }

    public static MonthType getMonth(int month) {
        switch (month) {
            case 1:
                return MonthType.FARVARDIN;
            case 2:
                return MonthType.ORDIBEHESHT;
            case 3:
                return MonthType.KHORDAD;
            case 4:
                return MonthType.TIR;
            case 5:
                return MonthType.MORDAD;
            case 6:
                return MonthType.SHAHRIVAR;
            case 7:
                return MonthType.MEHR;
            case 8:
                return MonthType.ABAN;
            case 9:
                return MonthType.AZAR;
            case 10:
                return MonthType.DEY;
            case 11:
                return MonthType.BAHMAN;
            case 12:
                return MonthType.ESFAND;
            default:
                return MonthType.TIR;
        }
    }

    public static int indexToHour(int index) {
        return index + 1;
    }

    public static int hourToIndex(int hour) {
        return hour - 1;
    }

    public static TimeType indexToTimeType(int index) {
        if (index == 0)
            return TimeType.AM;
        return TimeType.PM;
    }

    public static int indexToMinute(int index) {
        return index;
    }

    public static int indexToYear(int index) {
        return index + AppConstants.START_YEAR;
    }

    public static int yearToIndex(int year) {
        return year - AppConstants.START_YEAR;
    }

    public static MonthType indexToMonth(int index) {
        switch (index) {
            case 0:
                return MonthType.FARVARDIN;
            case 1:
                return MonthType.ORDIBEHESHT;
            case 2:
                return MonthType.KHORDAD;
            case 3:
                return MonthType.TIR;
            case 4:
                return MonthType.MORDAD;
            case 5:
                return MonthType.SHAHRIVAR;
            case 6:
                return MonthType.MEHR;
            case 7:
                return MonthType.ABAN;
            case 8:
                return MonthType.AZAR;
            case 9:
                return MonthType.DEY;
            case 10:
                return MonthType.BAHMAN;
            case 11:
                return MonthType.ESFAND;
            default:
                return MonthType.TIR;
        }
    }

    public static int indexToDay(int index) {
        return index + 1;
    }

    public static int dayToIndex(int day) {
        return day - 1;
    }

    private static ArrayList<MonthType> get31DaysMonths() {
        ArrayList<MonthType> list = new ArrayList<>();
        list.add(MonthType.FARVARDIN);
        list.add(MonthType.ORDIBEHESHT);
        list.add(MonthType.KHORDAD);
        list.add(MonthType.TIR);
        list.add(MonthType.MORDAD);
        list.add(MonthType.SHAHRIVAR);
        return list;
    }

    private static ArrayList<MonthType> get30DaysMonths() {
        ArrayList<MonthType> list = new ArrayList<>();
        list.add(MonthType.MEHR);
        list.add(MonthType.ABAN);
        list.add(MonthType.AZAR);
        list.add(MonthType.DEY);
        list.add(MonthType.BAHMAN);
        return list;
    }

    private static ArrayList<MonthType> get29DaysMonths() {
        ArrayList<MonthType> list = new ArrayList<>();
        list.add(MonthType.ESFAND);
        return list;
    }

    public static boolean shouldDayChange(MonthType beforeMonthType, MonthType afterMonthType) {
        ArrayList<MonthType> checkMonths = new ArrayList<>();
        checkMonths.add(beforeMonthType);
        checkMonths.add(afterMonthType);
        return !get31DaysMonths().containsAll(checkMonths) &&
                !get30DaysMonths().containsAll(checkMonths) &&
                !get29DaysMonths().containsAll(checkMonths);
    }

    public static int changeDay(int day, MonthType month) {
        if (month.getDays() > day)
            return day;
        return month.getDays();
    }

    public static ArrayList<String> getRepeatTypes() {
        ArrayList<String> types = new ArrayList<>();
        types.add(RepeatType.ONCE.getText());
        types.add(RepeatType.DAILY.getText());
        types.add(RepeatType.WEEKLY.getText());
        types.add(RepeatType.MONTHLY.getText());
        types.add(RepeatType.YEARLY.getText());
        types.add(RepeatType.CUSTOM.getText());
        return types;
    }

    public static RepeatType indexToRepeatType(int index) {
        switch (index) {
            case 0:
               return RepeatType.ONCE;
            case 1:
                return RepeatType.DAILY;
            case 2:
                return RepeatType.WEEKLY;
            case 3:
                return RepeatType.MONTHLY;
            case 4:
                return RepeatType.YEARLY;
            case 5:
                return RepeatType.CUSTOM;
            default:
                return RepeatType.ONCE;
        }
    }
}
