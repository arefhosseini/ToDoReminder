package com.fearefull.todoreminder.data.model.other;

import com.fearefull.todoreminder.utils.TimeUtils;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Date;

import saman.zamani.persiandate.PersianDate;
import timber.log.Timber;

public class MyDate implements Serializable {
    private int year;
    private MonthType month;
    private int day;
    private boolean isChanged;

    public MyDate(int year, MonthType month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
        isChanged = false;
    }

    public MyDate() {
        change(new Date());
        isChanged = false;
    }


    public int getYear() {
        return year;
    }

    public int getYearIndex() {
        return TimeUtils.yearToIndex(year);
    }

    public void setYear(int year) {
        this.year = year;
        isChanged = true;
    }

    public MonthType getMonth() {
        return month;
    }

    public int getMonthIndex() {
        return month.getIndex();
    }

    public void setMonth(MonthType month) {
        this.month = month;
        isChanged = true;
    }

    public int getDay() {
        return day;
    }

    public int getDayIndex() {
        return TimeUtils.dayToIndex(day);
    }

    public void setDay(int day) {
        this.day = day;
        isChanged = true;
    }

    public boolean isChanged() {
        return isChanged;
    }

    public void setChanged(boolean changed) {
        isChanged = changed;
    }

    public void change(Date date) {
        PersianDate persianDate = new PersianDate(date);
        year = persianDate.getShYear();
        month = TimeUtils.getMonth(persianDate.getShMonth());
        Timber.i("ShMonth %d", persianDate.getShMonth());
        day = persianDate.getShDay();
    }

    @NotNull
    public String toString() {
        return day + " " + month.getText() + " " + year;
    }
}
