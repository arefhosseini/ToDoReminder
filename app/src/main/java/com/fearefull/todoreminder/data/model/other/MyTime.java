package com.fearefull.todoreminder.data.model.other;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class MyTime implements Serializable {
    private int hour;
    private int minute;
    private TimeType timeType;
    private boolean isChanged;

    public MyTime(int hour, int minute, TimeType timeType) {
        this.hour = hour;
        this.minute = minute;
        this.timeType = timeType;
        isChanged = false;
    }

    public MyTime(Date date) {
        this.change(date);
        isChanged = false;
    }

    public int getHour() {
        return hour;
    }

    public int getHourIndex() {
        return hour - 1;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public TimeType getTimeType() {
        return timeType;
    }

    public void setTimeType(TimeType timeType) {
        this.timeType = timeType;
    }

    public boolean isChanged() {
        return isChanged;
    }

    public void setChanged(boolean changed) {
        isChanged = changed;
    }

    public void change(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        hour = calendar.get(Calendar.HOUR);
        minute = calendar.get(Calendar.MINUTE);
        timeType = calendar.get(Calendar.AM_PM) == Calendar.AM ? TimeType.AM : TimeType.PM;
    }

    @NotNull
    public String toString() {
        if (minute < 10)
            return hour + ":" + "0" + minute + " " + timeType.getPersianText();
        return hour + ":" + minute + " " + timeType.getPersianText();
    }
}
