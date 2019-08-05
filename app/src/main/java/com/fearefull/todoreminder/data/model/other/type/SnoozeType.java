package com.fearefull.todoreminder.data.model.other.type;

import java.io.Serializable;

public enum SnoozeType implements Serializable {
    ONCE(1),
    TWICE(2),
    THREE_TIMES(3),
    FOUR_TIMES(4),
    FIVE_TIMES(5),
    SIX_TIMES(6),
    SEVEN_TIMES(7),
    EIGHT_TIMES(8),
    NINE_TIMES(9),
    TEN_TIMES(10);

    private int value;

    SnoozeType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SnoozeType getSnoozeTypeByValue(int value) {
        for (SnoozeType type: values()) {
            if (type.value == value)
                return type;
        }
        return SnoozeType.THREE_TIMES;
    }

    public static SnoozeType getDefaultSnoozeType() {
        return SnoozeType.THREE_TIMES;
    }

    public static SnoozeType setNextSnooze(SnoozeType type) {
        if (type.value != 10) {
            return getSnoozeTypeByValue(type.value + 1);
        }
        return SnoozeType.TEN_TIMES;
    }

    public static SnoozeType setBeforeSnooze(SnoozeType type) {
        if (type.value != 1) {
            return getSnoozeTypeByValue(type.value - 1);
        }
        return SnoozeType.ONCE;
    }

}
