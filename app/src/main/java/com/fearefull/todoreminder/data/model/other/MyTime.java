package com.fearefull.todoreminder.data.model.other;

import com.fearefull.todoreminder.utils.AlarmUtils;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class MyTime implements Serializable {
    private int hour;
    private int minute;
    private HalfHourType halfHourType;
    private boolean isChanged;

    public MyTime(int hour, int minute, HalfHourType halfHourType) {
        this.hour = hour;
        this.minute = minute;
        this.halfHourType = halfHourType;
        isChanged = false;
    }

    public MyTime() {
        change(new Date());
        isChanged = false;
    }

    public int getHour() {
        return hour;
    }

    public int getHourIndex() {
        return AlarmUtils.hourToIndex(hour);
    }

    public void setHour(int hour) {
        this.hour = hour;
        isChanged = true;
    }

    public int getMinute() {
        return minute;
    }

    public int getMinuteIndex() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
        isChanged = true;
    }

    public HalfHourType getHalfHourType() {
        return halfHourType;
    }

    public int getTimeTypeIndex() {
        return halfHourType.getIndex();
    }

    public void setHalfHourType(HalfHourType halfHourType) {
        this.halfHourType = halfHourType;
        isChanged = true;
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
        halfHourType = calendar.get(Calendar.AM_PM) == Calendar.AM ? HalfHourType.AM : HalfHourType.PM;
    }

    @NotNull
    public String toString() {
        if (minute < 10)
            return hour + ":" + "0" + minute + " " + halfHourType.getPersianText();
        return hour + ":" + minute + " " + halfHourType.getPersianText();
    }
}
