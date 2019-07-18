package com.fearefull.todoreminder.utils;

import com.fearefull.todoreminder.data.model.db.Repeat;
import com.fearefull.todoreminder.data.model.other.type.MonthType;
import com.fearefull.todoreminder.data.model.other.RepeatItem;
import com.fearefull.todoreminder.data.model.other.type.HalfHourType;

import java.util.ArrayList;
import java.util.List;

public final class AlarmUtils {

    private AlarmUtils() {

    }

    public static List<String> get12Hours() {
        return getNumbers(1, 12);
    }

    public static List<String> get24Hours() {
        return getNumbers(1, 24);
    }

    private static List<String> getNumbers(int start, int end) {
        List<String> list = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            list.add(String.valueOf(i));
        }
        return list;
    }

    private static List<String> getNumbersWithZero(int start, int end) {
        List<String> list = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            if (i < 10)
                list.add("0" + i);
            else
                list.add(String.valueOf(i));
        }
        return list;
    }

    public static List<String> getMinutes() {
        return getNumbersWithZero(0, 59);
    }

    public static List<String> getHalfHourTypes() {
        List<String> stringTypes = new ArrayList<>();
        stringTypes.add(HalfHourType.AM.getPersianText());
        stringTypes.add(HalfHourType.PM.getPersianText());
        return stringTypes;
    }

    public static List<String> getMonths() {
        List<String> stringTypes = new ArrayList<>();
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

    public static List<String> getDays(MonthType monthType) {
        return getNumbers(1, monthType.getDays());
    }

    public static int indexToHour(int index) {
        return index + 1;
    }

    public static int hourToIndex(int hour) {
        return hour - 1;
    }

    public static HalfHourType indexToTimeType(int index) {
        if (index == 0)
            return HalfHourType.AM;
        return HalfHourType.PM;
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

    public static List<RepeatItem> getRepeatItems(Repeat defaultRepeat) {
        List<RepeatItem> list = new ArrayList<>();
        boolean isFind = false;
        if (defaultRepeat == Repeat.ONCE) {
            list.add(new RepeatItem(Repeat.ONCE, true));
            isFind = true;
        }
        else
            list.add(new RepeatItem(Repeat.ONCE, false));
        if (!isFind && defaultRepeat == Repeat.HOURLY) {
            list.add(new RepeatItem(Repeat.HOURLY, true));
            isFind = true;
        }
        else
            list.add(new RepeatItem(Repeat.HOURLY, false));
        if (!isFind && defaultRepeat == Repeat.DAILY) {
            list.add(new RepeatItem(Repeat.DAILY, true));
            isFind = true;
        }
        else
            list.add(new RepeatItem(Repeat.DAILY, false));
        if (!isFind && defaultRepeat == Repeat.WEEKLY) {
            list.add(new RepeatItem(Repeat.WEEKLY, true));
            isFind = true;
        }
        else
            list.add(new RepeatItem(Repeat.WEEKLY, false));
        if (!isFind && defaultRepeat == Repeat.MONTHLY) {
            list.add(new RepeatItem(Repeat.MONTHLY, true));
            isFind = true;
        }
        else
            list.add(new RepeatItem(Repeat.MONTHLY, false));
        if (!isFind && defaultRepeat == Repeat.YEARLY) {
            list.add(new RepeatItem(Repeat.YEARLY, true));
            isFind = true;
        }
        else
            list.add(new RepeatItem(Repeat.YEARLY, false));
        if (!isFind && defaultRepeat == Repeat.CUSTOM) {
            list.add(new RepeatItem(Repeat.CUSTOM, true));
        }
        else
            list.add(new RepeatItem(Repeat.CUSTOM, false));
        return list;
    }
}
