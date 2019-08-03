package com.fearefull.todoreminder.data.model.db;

import com.fearefull.todoreminder.data.model.other.type.HourType;

import java.io.Serializable;

public class Settings implements Serializable {
    private boolean isChanged;
    private HourType hourType;

    public Settings() {
        isChanged = false;
        this.hourType = HourType.FULL_HOUR;
    }

    public boolean isChanged() {
        return isChanged;
    }

    public void setChanged(boolean changed) {
        isChanged = changed;
    }

    public HourType getHourType() {
        return hourType;
    }

    public void setHourType(HourType hourType) {
        this.hourType = hourType;
    }

    public void changeHourType() {
        if (hourType == HourType.FULL_HOUR)
            hourType = HourType.HALF_HOUR;
        else
            hourType = HourType.FULL_HOUR;
    }
}
