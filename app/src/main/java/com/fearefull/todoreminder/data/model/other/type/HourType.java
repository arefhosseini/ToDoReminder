package com.fearefull.todoreminder.data.model.other.type;

import java.io.Serializable;

public enum HourType implements Serializable {
    FULL_HOUR("24 ساعت"),
    HALF_HOUR("12 ساعت");

    private String persianText;

    HourType(String persianText) {
        this.persianText = persianText;
    }

    public String getPersianText() {
        return persianText;
    }
}
