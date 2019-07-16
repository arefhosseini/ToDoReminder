package com.fearefull.todoreminder.data.model.other;

import com.fearefull.todoreminder.data.model.db.Repeat;

public class RepeatItem {
    private final Repeat repeat;
    private boolean isSelected;

    public RepeatItem(Repeat repeat, boolean isSelected) {
        this.repeat = repeat ;
        this.isSelected = isSelected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public Repeat getRepeat() {
        return repeat;
    }
}
