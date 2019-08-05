package com.fearefull.todoreminder.utils;

public final class AppConstants {

    public static final String DB_NAME = "to_do_reminder.db";
    public static final String PREF_NAME = "to_do_reminder_pref";
    public static final String DEFAULT_RINGTONE_NAME = "زنگ پیش فرض";
    public static final String TIMESTAMP_FORMAT = "yyyyMMdd_HHmmss";
    public static final int START_YEAR = 1397;
    public static final int END_YEAR = 1450;
    public static final String ALARM_KEY = "alarm_key";
    public static final long COUNT_DOWN_ALARM_TIMER = 1000 * 60;
    public static final long DEFAULT_SNOOZE_TIMER = 1000 * 60 * 5;
    public static final long[] VIBRATE_PATTERN = {0, 500, 1000};


    private AppConstants() {
        // This utility class is not publicly instantiable
    }
}
