package com.fearefull.todoreminder.data.model.db;

import com.fearefull.todoreminder.data.model.other.type.HourType;

import java.io.Serializable;

public class Setting implements Serializable {
    private HourType hourType;

    public Setting() {
        this.hourType = HourType.FULL_HOUR;
    }

    public HourType getHourType() {
        return hourType;
    }

    public void setHourType(HourType hourType) {
        this.hourType = hourType;
    }
}
