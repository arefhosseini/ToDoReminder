package com.fearefull.todoreminder.data.model.other;

import com.fearefull.todoreminder.utils.AlarmUtils;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Date;

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
        return AlarmUtils.yearToIndex(year);
    }

    public void setYear(int year) {
        this.year = year;
        isChanged = true;
    }

    public MonthType getMonth() {
        return month;
    }

    public int getMonthIndex() {
        return month.getValue();
    }

    public void setMonth(MonthType month) {
        this.month = month;
        isChanged = true;
    }

    public int getDay() {
        return day;
    }

    public int getDayIndex() {
        return AlarmUtils.dayToIndex(day);
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
        month = MonthType.getMonthType(persianDate.getShMonth());
        day = persianDate.getShDay();
    }

    @NotNull
    public String toString() {
        return day + " " + month.getText() + " " + year;
    }
}
