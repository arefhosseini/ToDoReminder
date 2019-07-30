package com.fearefull.todoreminder.ui.home;

public enum AlarmItemViewType {
    FIRST(1),
    ENABLED(2),
    DISABLED(3);

    int value;

    AlarmItemViewType(int value) {
        this.value = value;
    }

    public static AlarmItemViewType getTypeByValue(int value) {
        for (AlarmItemViewType type: values()) {
            if (type.value == value)
                return type;
        }
        return AlarmItemViewType.ENABLED;
    }
}
