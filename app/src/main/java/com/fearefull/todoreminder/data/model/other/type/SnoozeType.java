package com.fearefull.todoreminder.data.model.other.type;

import java.io.Serializable;

public enum SnoozeType implements Serializable {
    NONE(0),
    FIRST(1),
    SECOND(2),
    THIRD(3),
    FOURTH(4),
    FIFTH(5),
    SIXTH(6),
    SEVENTH(7),
    EIGHTH(8),
    NINTH(9),
    TENTH(10);

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
        return SnoozeType.THIRD;
    }

    public static SnoozeType getDefaultSnoozeType() {
        return SnoozeType.THIRD;
    }

    public static SnoozeType setNextSnooze(SnoozeType type) {
        if (type.value != 10) {
            return getSnoozeTypeByValue(type.value + 1);
        }
        return SnoozeType.TENTH;
    }

    public static SnoozeType setBeforeSnooze(SnoozeType type) {
        if (type.value != 0) {
            return getSnoozeTypeByValue(type.value - 1);
        }
        return SnoozeType.NONE;
    }

}
