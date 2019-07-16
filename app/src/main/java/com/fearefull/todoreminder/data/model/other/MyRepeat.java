package com.fearefull.todoreminder.data.model.other;

import com.fearefull.todoreminder.data.model.db.Repeat;

import org.jetbrains.annotations.NotNull;

public class MyRepeat {
    private Repeat type;
    private CustomRepeat customRepeat;

    MyRepeat() {
        this.type = Repeat.ONCE;
        customRepeat = new CustomRepeat();
    }

    public Repeat getType() {
        return type;
    }

    public void setType(Repeat type) {
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
        if (type != Repeat.CUSTOM)
            return type.getText();
        else
            return "Custom";
    }
}
