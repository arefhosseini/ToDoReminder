package com.fearefull.todoreminder.data.model.other.item;

import com.fearefull.todoreminder.data.model.other.type.AlarmTitleType;

public class AlarmTitleItem {
    private final AlarmTitleType type;
    private boolean isSelected;

    public AlarmTitleItem(AlarmTitleType type, boolean isSelected) {
        this.type = type;
        this.isSelected = isSelected;
    }

    public AlarmTitleType getType() {
        return type;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
