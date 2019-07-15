package com.fearefull.todoreminder.data.model.other;

public class RepeatTypeItem {
    private final RepeatType repeatType;
    private boolean isSelected;

    public RepeatTypeItem(RepeatType repeatType, boolean isSelected) {
        this.repeatType = repeatType;
        this.isSelected = isSelected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public RepeatType getRepeatType() {
        return repeatType;
    }
}
