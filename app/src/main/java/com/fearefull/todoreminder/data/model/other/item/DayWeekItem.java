package com.fearefull.todoreminder.data.model.other.item;

import com.fearefull.todoreminder.data.model.other.type.DayWeekType;

public class DayWeekItem {
    private final DayWeekType type;
    private boolean isSelected;

    public DayWeekItem(DayWeekType type, boolean isSelected) {
        this.type = type;
        this.isSelected = isSelected;
    }


    public DayWeekType getType() {
        return type;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
