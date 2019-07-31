package com.fearefull.todoreminder.data.model.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.fearefull.todoreminder.data.model.other.DataConverter;
import com.fearefull.todoreminder.data.model.other.type.AlarmTitleType;
import com.fearefull.todoreminder.data.model.other.type.DayMonthType;
import com.fearefull.todoreminder.data.model.other.type.DayWeekType;
import com.fearefull.todoreminder.data.model.other.type.HalfHourType;
import com.fearefull.todoreminder.data.model.other.type.MonthType;
import com.fearefull.todoreminder.data.model.other.persian_date.PersianDate;
import com.fearefull.todoreminder.data.model.other.RepeatModel;
import com.fearefull.todoreminder.data.model.other.item.RepeatManagerItem;
import com.fearefull.todoreminder.data.model.other.type.SnoozeType;
import com.fearefull.todoreminder.utils.AppConstants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import timber.log.Timber;

@Entity(tableName = "alarms")
@TypeConverters({DataConverter.class})
public class Alarm implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private Long id;

    @NonNull
    @ColumnInfo(name = "is_enable")
    private Boolean isEnable;

    @NonNull
    @ColumnInfo(name = "title")
    private String title;

    @NonNull
    @ColumnInfo(name = "title_type")
    private AlarmTitleType titleType;

    @NonNull
    @ColumnInfo(name = "ringtone")
    private String ringtone;

    @ColumnInfo(name = "note")
    private String note;

    @ColumnInfo(name = "repeats")
    private List<Repeat> repeats;

    @ColumnInfo(name = "repeats_status")
    private List<Boolean> repeatsStatus;

    @ColumnInfo(name = "snooze_type")
    private SnoozeType snoozeType;

    @ColumnInfo(name = "snooze_delay")
    private Long snoozeDelay;

    @ColumnInfo(name = "minutes")
    private List<Integer> minutes;

    @ColumnInfo(name = "hours")
    private List<Integer> hours;

    @ColumnInfo(name = "days_weeks")
    private List<List<Integer>> daysWeeks;

    @ColumnInfo(name = "days_month")
    private List<Integer> daysMonth;

    @ColumnInfo(name = "weeks_month")
    private List<Integer> weeksMonth;

    @ColumnInfo(name = "weeks_year")
    private List<Integer> weeksYear;

    @ColumnInfo(name = "months")
    private List<Integer> months;

    @ColumnInfo(name = "years")
    private List<Integer> years;

    @Ignore
    private PersianDate defaultPersianDate;

    @Ignore
    private int defaultMinute;

    @Ignore
    private int defaultHour;

    @Ignore
    private int defaultDayWeek;

    @Ignore
    private int defaultDayMonth;

    @Ignore
    private int defaultWeekMonth;

    @Ignore
    private int defaultWeekYear;

    @Ignore
    private int defaultMonth;

    @Ignore
    private int defaultYear;

    @Ignore
    private int nowMinute;

    @Ignore
    private int nowHour;

    @Ignore
    private int nowDay;

    @Ignore
    private int nowMonth;

    @Ignore
    private int nowYear;

    @Ignore
    private long nearestTime;

    /**
     * Control {@link #id}
     */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Control {@link #isEnable}
     */
    @NonNull
    public Boolean getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(@NonNull Boolean isEnable) {
        this.isEnable = isEnable;
    }

    /**
     * Control {@link #title}
     */
    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }


    /**
     * Control {@link #title}
     */
    @NonNull
    public AlarmTitleType getTitleType() {
        return titleType;
    }

    public void setTitleType(@NonNull AlarmTitleType titleType) {
        this.titleType = titleType;
    }

    /**
     * Control {@link #repeats}
     */
    public List<Repeat> getRepeats() {
        return repeats;
    }

    public void setRepeats(List<Repeat> repeats) {
        this.repeats = repeats;
    }

    public void addRepeat(Repeat repeat) {
        repeats.add(repeat);
    }

    public Repeat getRepeat(int index) {
        return repeats.get(index);
    }

    public Repeat getDefaultRepeat() {
        if (!repeats.isEmpty())
            return repeats.get(repeats.size() - 1);
        return Repeat.ONCE;
    }

    /**
     * Control {@link #repeatsStatus}
     */
    public List<Boolean> getRepeatsStatus() {
        return repeatsStatus;
    }

    public void setRepeatsStatus(List<Boolean> repeatsStatus) {
        this.repeatsStatus = repeatsStatus;
    }

    public void addRepeatStatus(Boolean repeatStatus) {
        repeatsStatus.add(repeatStatus);
    }


    /**
     * Control {@link #snoozeType}
     */
    public SnoozeType getSnoozeType() {
        return snoozeType;
    }

    public void setSnoozeType(SnoozeType snoozeType) {
        this.snoozeType = snoozeType;
    }

    @Ignore
    public void forwardSnoozeType() {
        snoozeType = SnoozeType.setNextSnooze(snoozeType);
    }

    @Ignore
    public void backwardSnoozeType() {
        snoozeType = SnoozeType.setBeforeSnooze(snoozeType);
    }

    /**
     * Control {@link #snoozeDelay}
     */
    public Long getSnoozeDelay() {
        return snoozeDelay;
    }

    public void setSnoozeDelay(Long snoozeDelay) {
        this.snoozeDelay = snoozeDelay;
    }

    @Ignore
    public void setSnoozeDelay(int delayMinute) {
        this.snoozeDelay = (long) (delayMinute * 60 * 1000);
    }

    @Ignore
    public int getSnoozeDelayMinute() {
        return (int) (snoozeDelay / (60 * 1000));
    }

    /**
     * Control {@link #ringtone}
     */
    @NonNull
    public String getRingtone() {
        return ringtone;
    }

    public void setRingtone(@NonNull String ringtone) {
        this.ringtone = ringtone;
    }


    /**
     * Control {@link #note}
     */
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }


    /**
     * Control {@link #minutes}
     */
    public List<Integer> getMinutes() {
        return minutes;
    }

    public int getMinute(int index) {
        return minutes.get(index);
    }

    public void setMinutes( List<Integer> minutes) {
        this.minutes = minutes;
    }

    @Ignore
    public void addMinuteByIndex(int indexMinute) {
        int minute = indexToMinute(indexMinute);
        addMinuteByValue(minute);
    }

    @Ignore
    public void addMinuteByValue(int minute) {
        minutes.add(minute);
    }

    @Ignore
    public void editMinuteByIndex(int indexMinute, int indexList) {
        int minute = indexToMinute(indexMinute);
        editMinuteByValue(minute, indexList);
    }

    @Ignore
    public void editMinuteByValue(int minute, int indexList) {
        minutes.set(indexList, minute);
    }


    /**
     * Control {@link #hours}
     */
    public List<Integer> getHours() {
        return hours;
    }

    public int getHour(int index) {
        return hours.get(index);
    }

    public void setHours( List<Integer> hours) {
        this.hours = hours;
    }

    @Ignore
    public void add24HourByIndex(int indexHour) {
        int hour = indexTo24Hour(indexHour);
        add24HourByValue(hour);
    }

    @Ignore
    public void add24HourByValue(int hour) {
        hours.add(hour);
    }

    @Ignore
    public void edit24HourByIndex(int indexHour, int indexList) {
        int hour = indexTo24Hour(indexHour);
        edit24HourByValue(hour, indexList);
    }

    @Ignore
    public void edit24HourByValue(int hour, int indexList) {
        hours.set(indexList, hour);
    }

    @Ignore
    public void add12HourByIndex(int indexHour, HalfHourType halfHourType) {
        int hour = indexTo12Hour(indexHour);
        add12HourByValue(hour, halfHourType);
    }

    @Ignore
    public void add12HourByValue(int halfHour, HalfHourType halfHourType) {
        int hour = halfHourToHour(halfHour, halfHourType);
        hours.add(hour);
    }

    @Ignore
    public void edit12HourByIndex(int indexHour, HalfHourType halfHourType, int index) {
        int hour = indexTo12Hour(indexHour);
        edit12HourByValue(hour, halfHourType, index);
    }

    @Ignore
    public void edit12HourByValue(int hour, HalfHourType halfHourType, int index) {
        if (halfHourType == HalfHourType.AM) {
            if (hour == 12)
                hours.set(0, index);
            else
                hours.set(hour, index);
        }
        else if (halfHourType == HalfHourType.PM) {
            if (hour == 12)
                hours.set(12, index);
            else
                hours.set(hour + 12, index);
        }
    }


    /**
     * Control {@link #daysWeeks}
     */
    public List<List<Integer>> getDaysWeeks() {
        return daysWeeks;
    }

    public List<Integer> getDaysWeek(int index) {
        return daysWeeks.get(index);
    }

    public void setDaysWeeks( List<List<Integer>> daysWeeks) {
        this.daysWeeks = daysWeeks;
    }

    @Ignore
    public void addDaysWeek(List<Integer> daysWeek) {
        daysWeeks.add(daysWeek);
    }

    @Ignore
    public void editDaysWeekBy(List<Integer> daysWeek, int indexList) {
        daysWeeks.set(indexList, daysWeek);
    }

    /**
     * Control {@link #daysMonth}
     */
    public List<Integer> getDaysMonth() {
        return daysMonth;
    }

    public int getDayMonth(int index) {
        return daysMonth.get(index);
    }

    public void setDaysMonth( List<Integer> daysMonth) {
        this.daysMonth = daysMonth;
    }

    @Ignore
    public void addDayMonthByIndex(int indexDayMonth) {
        int dayMonth = indexToDayMonth(indexDayMonth);
        addDayMonthByValue(dayMonth);
    }

    @Ignore
    public void addDayMonthByValue(int dayMonth) {
        daysMonth.add(dayMonth);
    }

    @Ignore
    public void editDayMonthByIndex(int indexDayMonth, int indexList) {
        int dayMonth = indexToDayMonth(indexDayMonth);
        editDayMonthByValue(dayMonth, indexList);
    }

    @Ignore
    public void editDayMonthByValue(int dayMonth, int indexList) {
        daysMonth.set(indexList, dayMonth);
    }


    /**
     * Control {@link #weeksMonth}
     */
    public List<Integer> getWeeksMonth() {
        return weeksMonth;
    }

    public int getWeekMonth(int index) {
        return weeksMonth.get(index);
    }

    public void setWeeksMonth( List<Integer> weeksMonth) {
        this.weeksMonth = weeksMonth;
    }


    /**
     * Control {@link #weeksYear}
     */
    public List<Integer> getWeeksYear() {
        return weeksYear;
    }

    public int getWeekYear(int index) {
        return weeksYear.get(index);
    }

    public void setWeeksYear( List<Integer> weeksYear) {
        this.weeksYear = weeksYear;
    }


    /**
     * Control {@link #months}
     */
    public List<Integer> getMonths() {
        return months;
    }

    public int getMonth(int index) {
        return months.get(index);
    }

    public void setMonths(List<Integer> months) {
        this.months = months;
    }

    @Ignore
    public void addMonthByIndex(int indexMonth) {
        int month = indexToMonth(indexMonth);
        addMonthByValue(month);
    }

    @Ignore
    public void addMonthByValue(int month) {
        months.add(month);
    }

    @Ignore
    public void editMonthByIndex(int indexMonth, int indexList) {
        int month = indexToMonth(indexMonth);
        editMonthByValue(month, indexList);
    }

    @Ignore
    public void editMonthByValue(int month, int indexList) {
        months.set(indexList, month);
    }


    /**
     * Control {@link #years}
     */
    public List<Integer> getYears() {
        return years;
    }

    public int getYear(int index) {
        return years.get(index);
    }

    public void setYears( List<Integer> years) {
        this.years = years;
    }

    @Ignore
    public void addYearByValue(int year) {
        years.add(year);
    }

    @Ignore
    public void editYearByValue(int year, int indexList) {
        years.set(indexList, year);
    }


    public PersianDate getDefaultPersianDate() {
        return defaultPersianDate;
    }

    public void setDefaultPersianDate(PersianDate defaultPersianDate) {
        this.defaultPersianDate = defaultPersianDate;
    }

    public int getDefaultMinute() {
        return defaultMinute;
    }

    public int getDefaultHour() {
        return defaultHour;
    }

    public int getDefaultDayWeek() {
        return defaultDayWeek;
    }

    public int getDefaultDayMonth() {
        return defaultDayMonth;
    }

    public int getDefaultWeekMonth() {
        return defaultWeekMonth;
    }

    public int getDefaultWeekYear() {
        return defaultWeekYear;
    }

    public int getDefaultMonth() {
        return defaultMonth;
    }

    public int getDefaultYear() {
        return defaultYear;
    }

    public int getNowMinute() {
        return nowMinute;
    }

    public int getNowHour() {
        return nowHour;
    }

    public int getNowDay() {
        return nowDay;
    }

    public int getNowMonth() {
        return nowMonth;
    }

    public int getNowYear() {
        return nowYear;
    }

    public long getNearestTime() {
        return nearestTime;
    }

    @Ignore
    public String getNearestTimeString() {
        if (System.currentTimeMillis() < nearestTime) {
            PersianDate persianDate = new PersianDate(nearestTime);
            setNowTime();
            boolean isFinished = false;
            StringBuilder stringBuilder = new StringBuilder();

            if (nowMonth == persianDate.getShMonth() && nowYear == persianDate.getShYear()) {
                if (nowDay - persianDate.getShDay() < 3) {
                    stringBuilder
                            .append(getTime12StringByValue(persianDate.getMinute(), persianDate.getHour()))
                            .append(" ");
                    if (nowDay == persianDate.getShDay())
                        stringBuilder.append("امروز");
                    else if (nowDay + 1 == persianDate.getShDay())
                        stringBuilder.append("فردا");
                    else if (nowDay + 2 == persianDate.getShDay())
                        stringBuilder.append("پس فردا");
                }
                else {
                    stringBuilder
                            .append(persianDate.getShDay())
                            .append(" ")
                            .append(MonthType.getMonthType(persianDate.getShMonth()).getText());
                }
                isFinished = true;
            }
            if (!isFinished) {
                stringBuilder
                        .append(persianDate.getShDay())
                        .append(MonthType.getMonthType(persianDate.getShMonth()).getText());

                if (nowYear != persianDate.getShYear()) {
                    stringBuilder.append(" ").append(persianDate.getShYear());
                }
            }
            Timber.e(stringBuilder.toString());
            return stringBuilder.toString();
        }
        return "گذشته است";
    }

    public void setNearestTime(long nearestTime) {
        this.nearestTime = nearestTime;
    }

    public Alarm() {
        this.isEnable = true;
        this.titleType = AlarmTitleType.getDefault();
        this.title = titleType.getText();
        this.ringtone = "DEFAULT";
        this.note = "";
        this.snoozeType = SnoozeType.getDefaultSnoozeType();
        this.snoozeDelay = AppConstants.DEFAULT_SNOOZE_TIMER;
        repeats = new ArrayList<>();
        repeatsStatus = new ArrayList<>();
        minutes = new ArrayList<>();
        hours = new ArrayList<>();
        daysWeeks = new ArrayList<>();
        daysMonth = new ArrayList<>();
        weeksMonth = new ArrayList<>();
        weeksYear = new ArrayList<>();
        months = new ArrayList<>();
        years = new ArrayList<>();
        nearestTime = Long.MAX_VALUE;

        setDefaultValues();
        setNowTime();
    }


    /**
    private void checkRepeat() {
        int minutesSize = minutes.size();
        int hoursSize = hours.size();
        int daysWeekSize = daysWeek.size();
        int daysMonthSize = daysMonth.size();
        int weeksMonthSize = weeksMonth.size();
        int weeksYearSize = weeksYear.size();
        int monthsYearSize = months.size();
        int yearsSize = years.size();

        if (minutesSize == 1 && hoursSize == 1 && daysMonthSize == 1 && monthsYearSize == 1 && yearsSize == 1) {
            repeat = Repeat.ONCE;
        }
        else if (minutesSize >= 1 && hoursSize == 0 && daysWeekSize == 0 && daysMonthSize == 0 &&
                weeksMonthSize == 0 && weeksYearSize == 0 && monthsYearSize == 0 && yearsSize == 0) {
            repeat = Repeat.HOURLY;
        }
        else if (minutesSize >= 1 && hoursSize >= 1 && daysWeekSize == 0 && daysMonthSize == 0 &&
                weeksMonthSize == 0 && weeksYearSize == 0 && monthsYearSize == 0 && yearsSize == 0) {
            repeat = Repeat.DAILY;
        }
        else if (minutesSize == 1 && hoursSize == 1 && daysWeekSize >= 1 && daysMonthSize == 0 &&
                weeksMonthSize == 0 && weeksYearSize == 0 && monthsYearSize == 0 && yearsSize == 0) {
            repeat = Repeat.WEEKLY;
        }
        else if (minutesSize == 1 && hoursSize == 1 && daysWeekSize == 0 && daysMonthSize == 1 &&
                weeksMonthSize == 0 && weeksYearSize == 0 && monthsYearSize == 0 && yearsSize == 0) {
            repeat = Repeat.MONTHLY;
        }
        else if (minutesSize == 1 && hoursSize == 1 && daysWeekSize == 0 && daysMonthSize == 1 &&
                weeksMonthSize == 0 && weeksYearSize == 0 && monthsYearSize == 1 && yearsSize == 0) {
            repeat = Repeat.YEARLY;
        }
    }
     */

    @Ignore
    public void setDefaultValues() {
        boolean nextDay = false;
        defaultPersianDate = new PersianDate();
        int hour = defaultPersianDate.getHour();
        if (hour == 23) {
            hour = 0;
            nextDay = true;
        }
        else
            hour ++;
        if (nextDay)
            defaultPersianDate.addDay(1);

        defaultMinute = 0;
        defaultHour = hour;
        defaultDayMonth = defaultPersianDate.getShDay();
        defaultMonth = defaultPersianDate.getShMonth();
        defaultYear = defaultPersianDate.getShYear();
        defaultDayWeek = 0;
        defaultWeekMonth = dayMonthToWeekMonth(defaultDayMonth);
        defaultWeekYear = dayToWeekYear(defaultDayMonth, defaultMonth);

        if (defaultMonth == 12 && defaultDayMonth == 30)
            defaultDayMonth = 29;
    }

    @Ignore
    public void setNowTime() {
        PersianDate persianDate = new PersianDate();
        nowMinute = persianDate.getMinute();
        nowHour = persianDate.getHour();
        nowDay = persianDate.getShDay();
        nowMonth = persianDate.getShMonth();
        nowYear = persianDate.getShYear();
    }

    @Ignore
    public static HalfHourType indexToHalfHourType(int index) {
        if (index == 0)
            return HalfHourType.AM;
        return HalfHourType.PM;
    }

    @Ignore
    public static int halfHourTypeToIndex(HalfHourType halfHourType) {
        return halfHourType.getIndex();
    }

    @Ignore
    public static Repeat indexToRepeat(int index) {
        return Repeat.getRepeat(index);
    }

    @Ignore
    public static int repeatToIndex(Repeat repeat) {
        return repeat.getValue();
    }

    @Ignore
    public static int indexToMinute(int index) {
        return index;
    }

    @Ignore
    public static int minuteToIndex(int minute) {
        return minute;
    }

    @Ignore
    public static int indexTo12Hour(int index) {
        return index + 1;
    }

    @Ignore
    public static int halfHourToIndex(int halfHour) {
        return halfHour - 1;
    }

    @Ignore
    public static int halfHourToHour(int halfHour, HalfHourType halfHourType) {
        if (halfHourType == HalfHourType.AM) {
            if (halfHour == 12)
                return 0;
            else
                return halfHour;
        }
        else if (halfHourType == HalfHourType.PM) {
            if (halfHour == 12)
                return halfHour;
            else
                return halfHour + 12;
        }
        return 0;
    }

    @Ignore
    public static int hourToHalfHour(int hour) {
        if (hour == 0 || hour == 12)
            return 12;
        if (hour < 12)
            return hour;
        else
            return hour - 12;
    }

    @Ignore
    public static int indexTo24Hour(int index) {
        return index;
    }

    @Ignore
    public static int hourToIndex(int hour) {
        return hour;
    }

    @Ignore
    public static int indexToDayMonth(int index) {
        return index + 1;
    }

    @Ignore
    public static int dayWeekToIndex(int dayWeek) {
        return dayWeek - 1;
    }

    @Ignore
    public static int indexToDayWeek(int index) {
        return index + 1;
    }

    @Ignore
    public static int dayMonthToIndex(int dayMonth) {
        return dayMonth - 1;
    }

    @Ignore
    public static int indexToMonth(int index) {
        return index + 1;
    }

    @Ignore
    public static int monthToIndex(int month) {
        return month - 1;
    }

    @Ignore
    public String getTime12StringByIndex(int indexMinute, int indexHour) {
        int minute = minutes.get(indexMinute);
        int halfHour = hourToHalfHour(hours.get(indexHour));
        if (minute < 10)
            return halfHour + ":" + "0" + minute + " " + getHalfHourType(hours.get(indexHour)).getPersianShortText();
        return halfHour + ":" + minute + " " + getHalfHourType(hours.get(indexHour)).getPersianShortText();
    }

    @Ignore
    public String getTime12StringByValue(int minute, int hour) {
        int halfHour = hourToHalfHour(hour);
        if (minute < 10)
            return halfHour + ":" + "0" + minute + " " + getHalfHourType(hour).getPersianShortText();
        return halfHour + ":" + minute + " " + getHalfHourType(hour).getPersianShortText();
    }

    @Ignore
    public String getTime24StringByIndex(int indexMinute, int indexHour) {
        int minute = minutes.get(indexMinute);
        int hour = hours.get(indexHour);
        if (minute < 10)
            return hour + ":" + "0" + minute;
        return hour + ":" + minute;
    }

    @Ignore
    public String getTime24StringByValue(int minute, int hour) {
        if (minute < 10)
            return hour + ":" + "0" + minute;
        return hour + ":" + minute;
    }

    @Ignore
    public String getDaysWeekString(int indexDaysWeek) {
        StringBuilder weekString = new StringBuilder();
        List<Integer> daysWeek = getDaysWeek(indexDaysWeek);

        boolean isSequence = false;
        int size = daysWeek.size();
        int sequenceCounter = 0;

        if (size > 2) {
            for (int index = 1; index < size; index++) {
                if (daysWeek.get(index) == daysWeek.get(index - 1) + 1)
                    sequenceCounter ++;
            }
        }

        if (sequenceCounter == size - 1 && sequenceCounter >= 2)
            isSequence = true;

        if (isSequence) {
            weekString.append(DayWeekType.getDayWeekTypeByValue(daysWeek.get(0)).getText());
            weekString.append(" تا ");
            weekString.append(DayWeekType.getDayWeekTypeByValue(daysWeek.get(size - 1)).getText());
        }
        else {
            for (int index = 0; index < size; index++) {
                weekString.append(DayWeekType.getDayWeekTypeByValue(daysWeek.get(index)).getText());
                weekString.append(" ");
            }
        }
        Timber.e("daysWeek size: %d", size);
        Timber.e(weekString.toString());
        return weekString.toString();
    }

    @Ignore
    public String getDateByDayMonthAndMonthAndYear(int indexDayMonth, int indexMonth, int indexYear) {
        String result = DayMonthType.getDayMonthTypeByValue(daysMonth.get(indexDayMonth)).getValue() + " " +
                MonthType.getMonthType(months.get(indexMonth)).getText();
        if (years.get(indexYear) == defaultYear)
            return result;
        result += " " + years.get(indexYear);
        return result;
    }

    @Ignore
    public String getDateByDayMonthAndMonthByIndex(int indexDayMonth, int indexMonth) {
        return DayMonthType.getDayMonthTypeByValue(daysMonth.get(indexDayMonth)).getValue() + " " +
                MonthType.getMonthType(months.get(indexMonth)).getText();
    }

    @Ignore
    public String getDateByDayMonthAndMonthByValue(int dayMonth, int month) {
        return DayMonthType.getDayMonthTypeByValue(dayMonth).getValue() + " " +
                MonthType.getMonthType(month).getText();
    }

    @Ignore
    public String getRepeatManagerStringByOnce(int index) {
        return getTime12StringByIndex(indexMinuteByIndexRepeat(index), indexHourByIndexRepeat(index)) + "-" +
                getDateByDayMonthAndMonthAndYear(indexDayMonthByIndexRepeat(index), indexMonthByIndexRepeat(index), indexYearByIndexRepeat(index)) +
                " (" + repeats.get(index).getText() + ")";
    }

    @Ignore
    public String getRepeatManagerStringByDaily(int index) {
        return getTime12StringByIndex(indexMinuteByIndexRepeat(index), indexHourByIndexRepeat(index)) + " (" +
                repeats.get(index).getText() + ")";
    }

    @Ignore
    public String getRepeatManagerStringByWeekly(int index) {
        return getTime12StringByIndex(indexMinuteByIndexRepeat(index), indexHourByIndexRepeat(index)) +
                " " + getDaysWeekString(indexDayWeekByIndexRepeat(index));
    }

    @Ignore
    public String getRepeatManagerStringByMonthly(int index) {
        return getTime12StringByIndex(indexMinuteByIndexRepeat(index), indexHourByIndexRepeat(index)) + " " +
                DayMonthType.getDayMonthTypeByValue(daysMonth.get(indexDayMonthByIndexRepeat(index))).getTextTh() +
                " هرماه";
    }

    @Ignore
    public String getRepeatManagerStringByYearly(int index) {
        return getTime12StringByIndex(indexMinuteByIndexRepeat(index), indexHourByIndexRepeat(index)) + "-" +
                getDateByDayMonthAndMonthByIndex(indexDayMonthByIndexRepeat(index), indexMonthByIndexRepeat(index)) +
                " (" + repeats.get(index).getText() + ")";
    }

    @Ignore
    public int getIndexHour12Hour(int indexList) {
        int hour = hours.get(indexList);
        if (hour == 0 || hour == 12)
            return 11;
        else if (hour < 12)
            return hour - 1;
        else
            return hour - 12 - 1;
    }

    @Ignore
    public int getIndexHour24Hour(int indexList) {
        return hours.get(indexList);
    }

    @Ignore
    public int getIndexHalfHourType(int indexList) {
        int hour = hours.get(indexList);
        if (hour >= 0 && hour < 12)
            return HalfHourType.AM.getIndex();
        return HalfHourType.PM.getIndex();
    }

    @Ignore
    public HalfHourType getHalfHourType() {
        if (defaultHour >= 0 && defaultHour < 12)
            return HalfHourType.AM;
        return HalfHourType.PM;
    }

    @Ignore
    public HalfHourType getHalfHourType(int hour) {
        if (hour >= 0 && hour < 12)
            return HalfHourType.AM;
        return HalfHourType.PM;
    }

    @Ignore
    public int getIndexMinute(int indexList) {
        return minutes.get(indexList);
    }

    @Ignore
    public int getRepeatCount() {
        return repeats.size();
    }

    @Ignore
    public void addRepeatModel(RepeatModel model) {
        addRepeat(model.getRepeat());
        addRepeatStatus(true);
        if (model.getRepeat() == Repeat.ONCE)
            addRepeatModelByOnce(model);
        else if (model.getRepeat() == Repeat.DAILY)
            addRepeatModelByDaily(model);
        else if (model.getRepeat() == Repeat.WEEKLY)
            addRepeatModelByWeekly(model);
        else if (model.getRepeat() == Repeat.MONTHLY)
            addRepeatModelByMonthly(model);
        else if (model.getRepeat() == Repeat.YEARLY)
            addRepeatModelByYearly(model);
    }

    @Ignore
    public void addRepeatModelByOnce(RepeatModel model) {
        addMinuteByValue(model.getMinute());
        add24HourByValue(model.getHour());
        addDayMonthByValue(model.getDayMonth());
        addMonthByValue(model.getMonth());
        addYearByValue(model.getYear());
    }

    @Ignore
    public void addRepeatModelByDaily(RepeatModel model) {
        addMinuteByValue(model.getMinute());
        add24HourByValue(model.getHour());
    }

    @Ignore
    public void addRepeatModelByWeekly(RepeatModel model) {
        addMinuteByValue(model.getMinute());
        add24HourByValue(model.getHour());
        addDaysWeek(new ArrayList<>(model.getDaysWeek()));
    }

    @Ignore
    public void addRepeatModelByMonthly(RepeatModel model) {
        addMinuteByValue(model.getMinute());
        add24HourByValue(model.getHour());
        addDayMonthByValue(model.getDayMonth());
    }

    @Ignore
    public void addRepeatModelByYearly(RepeatModel model) {
        addMinuteByValue(model.getMinute());
        add24HourByValue(model.getHour());
        addDayMonthByValue(model.getDayMonth());
        addMonthByValue(model.getMonth());
    }

    public String getRepeatManagerString(int index) {
        if (repeats.get(index) == Repeat.ONCE)
            return getRepeatManagerStringByOnce(index);
        if (repeats.get(index) == Repeat.DAILY)
            return getRepeatManagerStringByDaily(index);
        if (repeats.get(index) == Repeat.WEEKLY)
            return getRepeatManagerStringByWeekly(index);
        if (repeats.get(index) == Repeat.MONTHLY)
            return getRepeatManagerStringByMonthly(index);
        if (repeats.get(index) == Repeat.YEARLY)
            return getRepeatManagerStringByYearly(index);
        return "";
    }

    @Ignore
    public Observable<List<RepeatManagerItem>> getRepeatManagerItemList() {
        return Observable.fromCallable(() -> {
            List<RepeatManagerItem> list = new ArrayList<>();
            for (int index = 0; index < getRepeatCount(); index++) {
                list.add(new RepeatManagerItem(getRepeatManagerString(index)));
            }
            return list;
        });
    }

    @Ignore
    public void removeRepeatManagerDataByOnce(int index) {
        minutes.remove(indexMinuteByIndexRepeat(index));
        hours.remove(indexHourByIndexRepeat(index));
        daysMonth.remove(indexDayMonthByIndexRepeat(index));
        months.remove(indexMonthByIndexRepeat(index));
        years.remove(indexYearByIndexRepeat(index));
    }

    @Ignore
    public void removeRepeatManagerDataByDaily(int index) {
        minutes.remove(indexMinuteByIndexRepeat(index));
        hours.remove(indexHourByIndexRepeat(index));
    }

    @Ignore
    public void removeRepeatManagerDataByWeekly(int index) {
        minutes.remove(indexMinuteByIndexRepeat(index));
        hours.remove(indexHourByIndexRepeat(index));
        daysWeeks.remove(indexDayWeekByIndexRepeat(index));
    }

    @Ignore
    public void removeRepeatManagerDataByMonthly(int index) {
        minutes.remove(indexMinuteByIndexRepeat(index));
        hours.remove(indexHourByIndexRepeat(index));
        daysMonth.remove(indexDayMonthByIndexRepeat(index));
    }

    @Ignore
    public void removeRepeatManagerDataByYearly(int index) {
        minutes.remove(indexMinuteByIndexRepeat(index));
        hours.remove(indexHourByIndexRepeat(index));
        daysMonth.remove(indexDayMonthByIndexRepeat(index));
        months.remove(indexMonthByIndexRepeat(index));
    }

    @Ignore
    public void removeRepeatManagerData(int index) {
        if (repeats.get(index) == Repeat.ONCE)
            removeRepeatManagerDataByOnce(index);
        else if (repeats.get(index) == Repeat.DAILY)
            removeRepeatManagerDataByDaily(index);
        else if (repeats.get(index) == Repeat.WEEKLY)
            removeRepeatManagerDataByWeekly(index);
        else if (repeats.get(index) == Repeat.MONTHLY)
            removeRepeatManagerDataByMonthly(index);
        else if (repeats.get(index) == Repeat.YEARLY)
            removeRepeatManagerDataByYearly(index);

        // remove it last
        repeatsStatus.remove(index);
        repeats.remove(index);
    }

    @Ignore
    public RepeatModel getRepeatModel(int index) {
        if (repeats.get(index) == Repeat.ONCE)
            return getRepeatModelByOnce(index);
        if (repeats.get(index) == Repeat.DAILY)
            return getRepeatModelByDaily(index);
        if (repeats.get(index) == Repeat.WEEKLY)
            return getRepeatModelByWeekly(index);
        if (repeats.get(index) == Repeat.MONTHLY)
            return getRepeatModelByMonthly(index);
        if (repeats.get(index) == Repeat.YEARLY)
            return getRepeatModelByYearly(index);
        return new RepeatModel();
    }

    @Ignore
    private RepeatModel getRepeatModelByOnce(int index) {
        RepeatModel repeatModel = new RepeatModel();
        repeatModel.setRepeat(Repeat.ONCE);
        repeatModel.setMinute(minutes.get(indexMinuteByIndexRepeat(index)));
        repeatModel.setHour(hours.get(indexHourByIndexRepeat(index)));
        repeatModel.setDayMonth(daysMonth.get(indexDayMonthByIndexRepeat(index)));
        repeatModel.setMonth(months.get(indexMonthByIndexRepeat(index)));
        repeatModel.setYear(years.get(indexYearByIndexRepeat(index)));

        return repeatModel;
    }

    @Ignore
    private RepeatModel getRepeatModelByDaily(int index) {
        RepeatModel repeatModel = new RepeatModel();
        repeatModel.setRepeat(Repeat.DAILY);
        repeatModel.setMinute(minutes.get(indexMinuteByIndexRepeat(index)));
        repeatModel.setHour(hours.get(indexHourByIndexRepeat(index)));

        return repeatModel;
    }

    @Ignore
    private RepeatModel getRepeatModelByWeekly(int index) {
        RepeatModel repeatModel = new RepeatModel();
        repeatModel.setRepeat(Repeat.WEEKLY);
        repeatModel.setMinute(minutes.get(indexMinuteByIndexRepeat(index)));
        repeatModel.setHour(hours.get(indexHourByIndexRepeat(index)));
        repeatModel.setDaysWeek(new ArrayList<>(daysWeeks.get(indexDayWeekByIndexRepeat(index))));

        return repeatModel;
    }

    @Ignore
    private RepeatModel getRepeatModelByMonthly(int index) {
        RepeatModel repeatModel = new RepeatModel();
        repeatModel.setRepeat(Repeat.MONTHLY);
        repeatModel.setMinute(minutes.get(indexMinuteByIndexRepeat(index)));
        repeatModel.setHour(hours.get(indexHourByIndexRepeat(index)));
        repeatModel.setDayMonth(daysMonth.get(indexDayMonthByIndexRepeat(index)));

        return repeatModel;
    }

    @Ignore
    private RepeatModel getRepeatModelByYearly(int index) {
        RepeatModel repeatModel = new RepeatModel();
        repeatModel.setRepeat(Repeat.YEARLY);
        repeatModel.setMinute(minutes.get(indexMinuteByIndexRepeat(index)));
        repeatModel.setHour(hours.get(indexHourByIndexRepeat(index)));
        repeatModel.setDayMonth(daysMonth.get(indexDayMonthByIndexRepeat(index)));
        repeatModel.setMonth(months.get(indexMonthByIndexRepeat(index)));

        return repeatModel;
    }

    @Ignore
    public static int dayMonthToWeekMonth(int dayMonth) {
        if (dayMonth >= 1 && dayMonth < 8)
            return 1;
        else if (dayMonth >= 8 && dayMonth < 15)
            return 2;
        else if (dayMonth >= 15 && dayMonth < 22)
            return 3;
        return 4;
    }

    @Ignore
    public static int allDaysUntilMonth(int month) {
        if (month == 0)
            return 0;
        else {
            int sum = 0;
            for (int value = 1; value <= month; value++) {
                sum += MonthType.getMonthType(value).getDays();
            }
            return sum;
        }
    }

    @Ignore
    public static int dayToWeekYear(int dayMonth, int month) {
        int days = allDaysUntilMonth(month - 1) + dayMonth;
        int weekYear = (days / 7) + 1;
        if (weekYear > 52)
            return 52;
        return weekYear;
    }

    @Ignore
    public int indexMinuteByIndexRepeat(int indexRepeat) {
        return indexRepeat;
    }

    @Ignore
    public int indexHourByIndexRepeat(int indexRepeat) {
        return indexRepeat;
    }

    @Ignore
    public int indexDayWeekByIndexRepeat(int indexRepeat) {
        int index = -1;
        Repeat repeat;
        for (int counter = 0; counter <= indexRepeat; counter++) {
            repeat = repeats.get(counter);
            if (repeat == Repeat.WEEKLY)
                index ++;
        }
        return index;
    }

    @Ignore
    public int indexDayMonthByIndexRepeat(int indexRepeat) {
        int index = -1;
        Repeat repeat;
        for (int counter = 0; counter <= indexRepeat; counter++) {
            repeat = repeats.get(counter);
            if (repeat == Repeat.ONCE || repeat == Repeat.MONTHLY || repeat == Repeat.YEARLY)
                index ++;
        }
        return index;
    }

    @Ignore
    public int indexWeekMonthByIndexRepeat(int indexRepeat) {
        int index = -1;
        Repeat repeat;
        for (int counter = 0; counter <= indexRepeat; counter++) {
            repeat = repeats.get(counter);
            if (repeat == Repeat.MONTHLY)
                index ++;
        }
        return index;
    }

    @Ignore
    public int indexWeekYearByIndexRepeat(int indexRepeat) {
        int index = -1;
        Repeat repeat;
        for (int counter = 0; counter <= indexRepeat; counter++) {
            repeat = repeats.get(counter);
            if (repeat == Repeat.MONTHLY)
                index ++;
        }
        return index;
    }

    @Ignore
    public int indexMonthByIndexRepeat(int indexRepeat) {
        int index = -1;
        Repeat repeat;
        for (int counter = 0; counter <= indexRepeat; counter++) {
            repeat = repeats.get(counter);
            if (repeat == Repeat.ONCE || repeat == Repeat.YEARLY)
                index ++;
        }
        return index;
    }

    @Ignore
    public int indexYearByIndexRepeat(int indexRepeat) {
        int index = -1;
        Repeat repeat;
        for (int counter = 0; counter <= indexRepeat; counter++) {
            repeat = repeats.get(counter);
            if (repeat == Repeat.ONCE)
                index ++;
        }
        return index;
    }

    @Ignore
    public static HalfHourType hourToHalfHourType(int hour) {
        if (hour >= 0 && hour < 12)
            return HalfHourType.AM;
        return HalfHourType.PM;
    }
}
