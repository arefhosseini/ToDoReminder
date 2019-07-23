package com.fearefull.todoreminder.data.model.other;

import com.fearefull.todoreminder.data.model.db.Alarm;
import com.fearefull.todoreminder.data.model.db.Repeat;
import com.fearefull.todoreminder.data.model.other.persian_date.PersianDate;
import com.fearefull.todoreminder.data.model.other.type.RepeatResponseType;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class RepeatModel {
    private Repeat repeat;
    private int minute;
    private int hour;
    private List<Integer> daysWeek;
    private int dayMonth;
    private int weekMonth;
    private int weekYear;
    private int month;
    private int year;

    public RepeatModel() {
        reset();
    }

    public Repeat getRepeat() {
        return repeat;
    }

    public void setRepeat(Repeat repeat) {
        this.repeat = repeat;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public List<Integer> getDaysWeek() {
        return daysWeek;
    }

    public void addToDaysWeek(int dayWeek) {
        daysWeek.add(dayWeek);
    }

    public void setDaysWeek(List<Integer> daysWeek) {
        this.daysWeek = daysWeek;
    }

    public int getDayMonth() {
        return dayMonth;
    }

    public void setDayMonth(int dayMonth) {
        this.dayMonth = dayMonth;
    }

    public int getWeekMonth() {
        return weekMonth;
    }

    public void setWeekMonth(int weekMonth) {
        this.weekMonth = weekMonth;
    }

    public int getWeekYear() {
        return weekYear;
    }

    public void setWeekYear(int weekYear) {
        this.weekYear = weekYear;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void reset() {
        repeat = null;
        minute = -1;
        hour = -1;
        if (daysWeek == null)
            daysWeek = new ArrayList<>();
        else
            daysWeek.clear();
        dayMonth = -1;
        weekMonth = -1;
        weekYear = -1;
        month = -1;
        year = -1;
    }

    public RepeatResponseType isValid(Alarm alarm) {
        if (repeat == null)
            return RepeatResponseType.NOT_READY;
        if (checkDuplicate(alarm))
            return RepeatResponseType.DUPLICATE;
        if (repeat == Repeat.ONCE)
            return isOnceRepeatValid();
        if (repeat == Repeat.DAILY)
            return isDailyRepeatValid();
        if (repeat == Repeat.WEEKLY)
            return isWeeklyRepeatValid();
        if (repeat == Repeat.MONTHLY)
            return isMonthlyRepeatValid();
        if (repeat == Repeat.YEARLY)
            return isYearlyRepeatValid();
        return RepeatResponseType.NOT_READY;
    }

    private RepeatResponseType isOnceRepeatValid() {
        if (repeat == Repeat.ONCE && minute != -1 && hour != -1 && dayMonth != -1 && month != -1 && year != -1) {
            PersianDate currentDate = new PersianDate();
            PersianDate checkDate = new PersianDate();
            checkDate.setMinute(minute);
            checkDate.setHour(hour);
            checkDate.setShDay(dayMonth);
            checkDate.setShMonth(month);
            checkDate.setShYear(year);

            if (currentDate.after(checkDate))
                return RepeatResponseType.TRUE;
            checkDate.addDay(1);
            if (currentDate.after(checkDate)) {
                minute = checkDate.getMinute();
                hour = checkDate.getHour();
                dayMonth = checkDate.getShDay();
                month = checkDate.getShMonth();
                year = checkDate.getShYear();
                return RepeatResponseType.TRUE;
            }
            checkDate.addYear(1);
            if (currentDate.after(checkDate)) {
                minute = checkDate.getMinute();
                hour = checkDate.getHour();
                dayMonth = checkDate.getShDay();
                month = checkDate.getShMonth();
                year = checkDate.getShYear();
                return RepeatResponseType.TRUE;
            }
            reset();
            return RepeatResponseType.FALSE;
        }
        return RepeatResponseType.NOT_READY;
    }

    private RepeatResponseType isDailyRepeatValid() {
        if (repeat == Repeat.DAILY && minute != -1 && hour != -1)
            return RepeatResponseType.TRUE;
        return RepeatResponseType.NOT_READY;
    }

    private RepeatResponseType isWeeklyRepeatValid() {
        if (repeat == Repeat.WEEKLY && minute != -1 && hour != -1 && daysWeek.size() > 0)
            return RepeatResponseType.TRUE;
        return RepeatResponseType.NOT_READY;
    }

    private RepeatResponseType isMonthlyRepeatValid() {
        if (repeat == Repeat.MONTHLY && minute != -1 && hour != -1 && dayMonth != -1)
            return RepeatResponseType.TRUE;
        return RepeatResponseType.NOT_READY;
    }

    private RepeatResponseType isYearlyRepeatValid() {
        if (repeat == Repeat.YEARLY && minute != -1 && hour != -1 && dayMonth != -1 && month != -1)
            return RepeatResponseType.TRUE;
        return RepeatResponseType.NOT_READY;
    }

    private boolean checkDuplicate(Alarm alarm) {
        for (int index = 0; index < alarm.getRepeatCount(); index++) {
            if (isSame(alarm.getRepeatModel(index))) {
                return true;
            }
        }
        return false;
    }

    public boolean isSame(RepeatModel model) {
        if (model.getRepeat() == Repeat.ONCE) {
            return isSameByOnce(model);
        }
        if (model.getRepeat() == Repeat.DAILY) {
            return isSameByDaily(model);
        }
        if (model.getRepeat() == Repeat.WEEKLY) {
            return isSameByWeekly(model);
        }
        if (model.getRepeat() == Repeat.MONTHLY) {
            return isSameByMonthly(model);
        }
        if (model.getRepeat() == Repeat.YEARLY) {
            return isSameByYearly(model);
        }
        return false;
    }

    private boolean isSameByOnce(RepeatModel model) {
        return this.minute == model.getMinute() && this.hour == model.getHour() &&
                this.dayMonth == model.dayMonth && this.month == model.getMonth() &&
                this.year == model.getYear();
    }

    private boolean isSameByDaily(RepeatModel model) {
        return this.minute == model.getMinute() && this.hour == model.getHour();
    }

    private boolean isSameByWeekly(RepeatModel model) {
        return this.minute == model.getMinute() && this.hour == model.getHour() && model.daysWeek.containsAll(daysWeek);
    }

    private boolean isSameByMonthly(RepeatModel model) {
        return this.minute == model.getMinute() && this.hour == model.getHour() &&
                this.dayMonth == model.dayMonth;
    }

    private boolean isSameByYearly(RepeatModel model) {
        return this.minute == model.getMinute() && this.hour == model.getHour() &&
                this.dayMonth == model.dayMonth && this.month == model.getMonth();
    }
}
