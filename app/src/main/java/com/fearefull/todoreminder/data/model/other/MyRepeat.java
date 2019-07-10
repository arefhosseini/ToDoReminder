package com.fearefull.todoreminder.data.model.other;

import org.jetbrains.annotations.NotNull;

public class MyRepeat {
    private RepeatType type;
    private CustomRepeat customRepeat;

    MyRepeat() {
        this.type = RepeatType.NEVER;
        customRepeat = new CustomRepeat();
    }

    public RepeatType getType() {
        return type;
    }

    public void setType(RepeatType type) {
        this.type = type;
    }

    public CustomRepeat getCustomRepeat() {
        return customRepeat;
    }

    public void setCustomRepeat(CustomRepeat customRepeat) {
        this.customRepeat = customRepeat;
    }


    @NotNull
    public String toString() {
        if (type != RepeatType.CUSTOM)
            return type.getText();
        else
            return "Custom";
    }
}
