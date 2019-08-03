package com.fearefull.todoreminder.data.model.other.type;

public enum AlarmType {
    DEFAULT(1),
    DONE(2),
    NOT_DONE(3),
    DISABLED(4);

    int value;

    AlarmType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static AlarmType getTypeByValue(int value) {
        for (AlarmType type: values()) {
            if (type.value == value)
                return type;
        }
        return AlarmType.DONE;
    }

    public static AlarmType getDefaultType() {
        return AlarmType.DONE;
    }
}
