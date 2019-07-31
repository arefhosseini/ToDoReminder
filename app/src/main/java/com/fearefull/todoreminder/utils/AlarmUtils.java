package com.fearefull.todoreminder.utils;

import com.fearefull.todoreminder.data.model.db.Alarm;
import com.fearefull.todoreminder.data.model.db.Repeat;
import com.fearefull.todoreminder.data.model.other.RepeatModel;
import com.fearefull.todoreminder.data.model.other.item.AlarmTitleItem;
import com.fearefull.todoreminder.data.model.other.item.DayWeekItem;
import com.fearefull.todoreminder.data.model.other.item.RepeatItem;
import com.fearefull.todoreminder.data.model.other.persian_date.PersianDate;
import com.fearefull.todoreminder.data.model.other.type.AlarmTitleType;
import com.fearefull.todoreminder.data.model.other.type.DayMonthType;
import com.fearefull.todoreminder.data.model.other.type.DayWeekType;
import com.fearefull.todoreminder.data.model.other.type.HalfHourType;
import com.fearefull.todoreminder.data.model.other.type.MonthType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;

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
        stringTypes.add(HalfHourType.AM.getPersianShortText());
        stringTypes.add(HalfHourType.PM.getPersianShortText());
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

    public static List<String> getDaysMonth(MonthType monthType) {
        return DayMonthType.getDayMonthString(monthType.getDays());
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

    public static Observable<List<RepeatItem>> getRepeatItems(Repeat defaultRepeat) {
        return Observable.defer(
                () -> {
                    List<RepeatItem> list = new ArrayList<>();
                    boolean isFind = false;
                    if (defaultRepeat == Repeat.ONCE) {
                        list.add(new RepeatItem(Repeat.ONCE, true));
                        isFind = true;
                    }
                    else
                        list.add(new RepeatItem(Repeat.ONCE, false));
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

                    return Observable.just(list);
                }
        );
    }

    public static Observable<List<AlarmTitleItem>> getAlarmTitleItems(AlarmTitleType defaultTitleType) {
        return Observable.defer(
                () -> {
                    List<AlarmTitleItem> list = new ArrayList<>();
                    boolean isFind = false;

                    if (defaultTitleType == AlarmTitleType.CUSTOM) {
                        list.add(new AlarmTitleItem(AlarmTitleType.CUSTOM, true));
                        isFind = true;
                    }
                    else
                        list.add(new AlarmTitleItem(AlarmTitleType.CUSTOM, false));

                    if (!isFind && defaultTitleType == AlarmTitleType.TO_DO) {
                        list.add(new AlarmTitleItem(AlarmTitleType.TO_DO, true));
                        isFind = true;
                    }
                    else
                        list.add(new AlarmTitleItem(AlarmTitleType.TO_DO, false));
                    if (!isFind && defaultTitleType == AlarmTitleType.BIRTHDAY) {
                        list.add(new AlarmTitleItem(AlarmTitleType.BIRTHDAY, true));
                        isFind = true;
                    }
                    else
                        list.add(new AlarmTitleItem(AlarmTitleType.BIRTHDAY, false));
                    if (!isFind && defaultTitleType == AlarmTitleType.ANNIVERSARY) {
                        list.add(new AlarmTitleItem(AlarmTitleType.ANNIVERSARY, true));
                        isFind = true;
                    }
                    else
                        list.add(new AlarmTitleItem(AlarmTitleType.ANNIVERSARY, false));
                    if (!isFind && defaultTitleType == AlarmTitleType.CALL) {
                        list.add(new AlarmTitleItem(AlarmTitleType.CALL, true));
                        isFind = true;
                    }
                    else
                        list.add(new AlarmTitleItem(AlarmTitleType.CALL, false));
                    if (!isFind && defaultTitleType == AlarmTitleType.SHOPPING) {
                        list.add(new AlarmTitleItem(AlarmTitleType.SHOPPING, true));
                        isFind = true;
                    }
                    else
                        list.add(new AlarmTitleItem(AlarmTitleType.SHOPPING, false));
                    if (!isFind && defaultTitleType == AlarmTitleType.GET_UP) {
                        list.add(new AlarmTitleItem(AlarmTitleType.GET_UP, true));
                        isFind = true;
                    }
                    else
                        list.add(new AlarmTitleItem(AlarmTitleType.GET_UP, false));
                    if (!isFind && defaultTitleType == AlarmTitleType.DRINK_WATER) {
                        list.add(new AlarmTitleItem(AlarmTitleType.DRINK_WATER, true));
                        isFind = true;
                    }
                    else
                        list.add(new AlarmTitleItem(AlarmTitleType.DRINK_WATER, false));
                    if (!isFind && defaultTitleType == AlarmTitleType.TAKE_PILL) {
                        list.add(new AlarmTitleItem(AlarmTitleType.TAKE_PILL, true));
                        isFind = true;
                    }
                    else
                        list.add(new AlarmTitleItem(AlarmTitleType.TAKE_PILL, false));
                    if (!isFind && defaultTitleType == AlarmTitleType.MEETING) {
                        list.add(new AlarmTitleItem(AlarmTitleType.MEETING, true));
                        isFind = true;
                    }
                    else
                        list.add(new AlarmTitleItem(AlarmTitleType.MEETING, false));
                    if (!isFind && defaultTitleType == AlarmTitleType.COURSE) {
                        list.add(new AlarmTitleItem(AlarmTitleType.COURSE, true));
                        isFind = true;
                    }
                    else
                        list.add(new AlarmTitleItem(AlarmTitleType.COURSE, false));
                    if (!isFind && defaultTitleType == AlarmTitleType.EXERCISE) {
                        list.add(new AlarmTitleItem(AlarmTitleType.EXERCISE, true));
                        isFind = true;
                    }
                    else
                        list.add(new AlarmTitleItem(AlarmTitleType.EXERCISE, false));
                    if (!isFind && defaultTitleType == AlarmTitleType.GREETING) {
                        list.add(new AlarmTitleItem(AlarmTitleType.GREETING, true));
                        isFind = true;
                    }
                    else
                        list.add(new AlarmTitleItem(AlarmTitleType.GREETING, false));
                    if (!isFind && defaultTitleType == AlarmTitleType.TRAVEL) {
                        list.add(new AlarmTitleItem(AlarmTitleType.TRAVEL, true));
                        isFind = true;
                    }
                    else
                        list.add(new AlarmTitleItem(AlarmTitleType.TRAVEL, false));
                    if (!isFind && defaultTitleType == AlarmTitleType.CREDIT_CARD) {
                        list.add(new AlarmTitleItem(AlarmTitleType.CREDIT_CARD, true));
                        isFind = true;
                    }
                    else
                        list.add(new AlarmTitleItem(AlarmTitleType.CREDIT_CARD, false));

                    return Observable.just(list);
                }
        );
    }

    public static List<DayWeekItem> getDayWeekItems() {
        List<DayWeekItem> list = new ArrayList<>();

        list.add(new DayWeekItem(DayWeekType.DAY_1, true));
        list.add(new DayWeekItem(DayWeekType.DAY_2, false));
        list.add(new DayWeekItem(DayWeekType.DAY_3, false));
        list.add(new DayWeekItem(DayWeekType.DAY_4, false));
        list.add(new DayWeekItem(DayWeekType.DAY_5, false));
        list.add(new DayWeekItem(DayWeekType.DAY_6, false));
        list.add(new DayWeekItem(DayWeekType.DAY_7, false));

        return list;
    }

    public static Observable<List<Alarm>> sortAlarms(List<Alarm> alarmList) {
        return Observable.defer(() -> {
            List<Alarm> allAlarmList = new ArrayList<>();
            List<Alarm> disabledAlarmList = new ArrayList<>();
            List<Alarm> doneAlarmList = new ArrayList<>();
            Alarm currentAlarm = new Alarm();
            long currentTime = System.currentTimeMillis();
            long checkTime = 0;

            for (Alarm alarm: alarmList) {
                if (!alarm.getIsEnable())
                    disabledAlarmList.add(alarm);
                else {
                    for (int index = 0; index < alarm.getRepeatCount(); index++) {
                        if (alarm.getRepeat(index) == Repeat.ONCE) {
                            checkTime = scheduleOnceRepeat(alarm.getRepeatModel(index));
                        }
                        else if (alarm.getRepeat(index) == Repeat.DAILY) {
                            checkTime = scheduleDailyRepeat(alarm.getRepeatModel(index), currentTime, currentAlarm);
                        }
                        else if (alarm.getRepeat(index) == Repeat.WEEKLY) {
                            checkTime = scheduleWeeklyRepeat(alarm.getRepeatModel(index), currentTime, currentAlarm);
                        }
                        else if (alarm.getRepeat(index) == Repeat.MONTHLY) {
                            checkTime = scheduleMonthlyRepeat(alarm.getRepeatModel(index), currentTime, currentAlarm);
                        }
                        else if (alarm.getRepeat(index) == Repeat.YEARLY) {
                            checkTime = scheduleYearlyRepeat(alarm.getRepeatModel(index), currentTime, currentAlarm);
                        }

                        if (
                                alarm.getNearestTime() == Long.MAX_VALUE ||
                                checkTime > currentTime && alarm.getNearestTime() > currentTime && checkTime < alarm.getNearestTime() ||
                                checkTime > currentTime && alarm.getNearestTime() < currentTime ||
                                checkTime < currentTime && alarm.getNearestTime() < currentTime && checkTime < alarm.getNearestTime()
                        )
                            alarm.setNearestTime(checkTime);
                    }
                    if (alarm.getNearestTime() < currentTime && !doneAlarmList.contains(alarm) && !allAlarmList.contains(alarm))
                        doneAlarmList.add(alarm);
                    else if (!disabledAlarmList.contains(alarm) && !doneAlarmList.contains(alarm) && !allAlarmList.contains(alarm)) {
                        allAlarmList.add(alarm);
                    }
                }
            }

            Collections.sort(allAlarmList, (o1, o2) -> {
                if(o1.getNearestTime() == o2.getNearestTime())
                    return 0;
                return o1.getNearestTime() < o2.getNearestTime() ? -1 : 1;
            });

            Collections.sort(doneAlarmList, (o1, o2) -> {
                if(o1.getNearestTime() == o2.getNearestTime())
                    return 0;
                return o1.getNearestTime() < o2.getNearestTime() ? -1 : 1;
            });

            allAlarmList.addAll(doneAlarmList);
            allAlarmList.addAll(disabledAlarmList);
            return Observable.just(allAlarmList);
        });
    }

    private static long scheduleOnceRepeat(RepeatModel repeatModel) {
        PersianDate checkDate = new PersianDate();
        checkDate.setSecond(0);
        checkDate.setMinute(repeatModel.getMinute());
        checkDate.setHour(repeatModel.getHour());
        checkDate.setShDay(repeatModel.getDayMonth());
        checkDate.setShMonth(repeatModel.getMonth());
        checkDate.setShYear(repeatModel.getYear());
        return checkDate.getTime();
    }

    private static long scheduleDailyRepeat(RepeatModel repeatModel, long currentTime, Alarm currentAlarm) {
        PersianDate checkDate = new PersianDate();
        checkDate.setSecond(0);
        checkDate.setMinute(repeatModel.getMinute());
        checkDate.setHour(repeatModel.getHour());
        checkDate.setShDay(currentAlarm.getNowDay());
        checkDate.setShMonth(currentAlarm.getNowMonth());
        checkDate.setShYear(currentAlarm.getNowYear());

        boolean isFindNearTime = false;
        long checkTime = -1;
        while (!isFindNearTime) {
            checkTime = checkDate.getTime() - currentTime;
            if (checkTime > 1000)
                isFindNearTime = true;
            else {
                checkDate.addDay(1);
                checkDate.setMinute(repeatModel.getMinute());
                checkDate.setHour(repeatModel.getHour());
            }
        }
        return checkDate.getTime();
    }

    private static long scheduleWeeklyRepeat(RepeatModel repeatModel, long currentTime, Alarm currentAlarm) {
        PersianDate nowDate;
        PersianDate bestTime = null;
        PersianDate checkDate = new PersianDate();
        checkDate.setSecond(0);
        checkDate.setMinute(repeatModel.getMinute());
        checkDate.setHour(repeatModel.getHour());
        checkDate.setShDay(currentAlarm.getNowDay());
        checkDate.setShMonth(currentAlarm.getNowMonth());
        checkDate.setShYear(currentAlarm.getNowYear());

        boolean isFindNearTime = false;
        long minTime;
        long checkTime;
        while (!isFindNearTime) {
            minTime = Long.MAX_VALUE;
            for (int dayWeek: repeatModel.getDaysWeek()) {
                int dayWeekIndex = Alarm.dayWeekToIndex(dayWeek);
                nowDate = new PersianDate(checkDate.getTime());
                checkTime = nowDate.getTime() - currentTime;
                if (nowDate.dayOfWeek() == dayWeekIndex && checkTime > 1000 && checkTime < minTime) {
                    minTime = checkTime;
                    bestTime = new PersianDate(checkDate.getTime());
                }
                else if (nowDate.dayOfWeek() < dayWeekIndex){
                    nowDate.addDay(dayWeekIndex - nowDate.dayOfWeek());
                    checkTime = nowDate.getTime() - currentTime;
                    if (nowDate.dayOfWeek() == dayWeekIndex && checkTime > 1000 && checkTime < minTime) {
                        minTime = checkTime;
                        bestTime = new PersianDate(checkDate.getTime());
                    }
                }
            }
            if (minTime < Long.MAX_VALUE)
                isFindNearTime = true;
            else {
                checkDate.addDay(7 - checkDate.dayOfWeek() + Alarm.indexToDayWeek(repeatModel.getDaysWeek().get(0)));
            }
        }
        return bestTime.getTime();
    }

    private static long scheduleMonthlyRepeat(RepeatModel repeatModel, long currentTime, Alarm currentAlarm) {
        PersianDate checkDate = new PersianDate();
        checkDate.setSecond(0);
        checkDate.setMinute(repeatModel.getMinute());
        checkDate.setHour(repeatModel.getHour());
        checkDate.setShDay(repeatModel.getDayMonth());
        checkDate.setShMonth(currentAlarm.getNowMonth());
        checkDate.setShYear(currentAlarm.getNowYear());

        boolean isFindNearTime = false;
        long checkTime = -1;
        while (!isFindNearTime) {
            checkTime = checkDate.getTime() - currentTime;
            if (checkTime > 1000)
                isFindNearTime = true;
            else {
                checkDate.addMonth(1);
                checkDate.setMinute(repeatModel.getMinute());
                checkDate.setHour(repeatModel.getHour());
            }
        }
        return checkDate.getTime();
    }

    private static long scheduleYearlyRepeat(RepeatModel repeatModel, long currentTime, Alarm currentAlarm) {
        PersianDate checkDate = new PersianDate();
        checkDate.setSecond(0);
        checkDate.setMinute(repeatModel.getMinute());
        checkDate.setHour(repeatModel.getHour());
        checkDate.setShDay(repeatModel.getDayMonth());
        checkDate.setShMonth(repeatModel.getMonth());
        checkDate.setShYear(currentAlarm.getNowYear());

        boolean isFindNearTime = false;
        long checkTime = -1;
        while (!isFindNearTime) {
            checkTime = checkDate.getTime() - currentTime;
            if (checkTime > 1000)
                isFindNearTime = true;
            else {
                checkDate.addYear(1);
                checkDate.setMinute(repeatModel.getMinute());
                checkDate.setHour(repeatModel.getHour());
                checkDate.setShDay(repeatModel.getDayMonth());
                checkDate.setShMonth(repeatModel.getMonth());
            }
        }
        return checkDate.getTime();
    }
}
