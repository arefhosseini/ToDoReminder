package com.fearefull.todoreminder.data.model.other.type;

public enum RingtoneType {
    ALARM(""),
    DEFAULT("آهنگ پیش فرض"),
    SILENT("سکوت");

    private String name;

    RingtoneType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static RingtoneType getDefaultRingtone() {
        return RingtoneType.DEFAULT;
    }
}
