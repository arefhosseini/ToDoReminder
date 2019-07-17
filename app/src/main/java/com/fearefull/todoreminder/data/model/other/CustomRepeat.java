package com.fearefull.todoreminder.data.model.other;

import java.util.Collections;
import java.util.List;

public class CustomRepeat {
    private CustomRepeatType type;
    private int count;
    private List<WeekType> onWeekList;
    private List<DayMonthType> onDayMonthList;
    private List<MonthType> onMonthList;

    CustomRepeat() {
        this.type = CustomRepeatType.WEEK;
        this.count = 1;
        onWeekList = Collections.singletonList(WeekType.DAY_1);
        onDayMonthList = Collections.singletonList(DayMonthType.DAY_1);
        onMonthList = Collections.singletonList(MonthType.FARVARDIN);
    }

    public CustomRepeatType getType() {
        return type;
    }

    public void setType(CustomRepeatType type) {
        this.type = type;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<WeekType> getOnWeekList() {
        return onWeekList;
    }

    public void setOnWeekList(List<WeekType> onWeekList) {
        this.onWeekList = onWeekList;
    }

    public void addToOnWeekList(WeekType weekType) {
        if (!onWeekList.contains(weekType))
            onWeekList.add(weekType);
    }

    public void removeFromOnWeekList(WeekType weekType) {
        onWeekList.remove(weekType);
    }

    public List<DayMonthType> getOnDayMonthList() {
        return onDayMonthList;
    }

    public void setOnDayMonthList(List<DayMonthType> onDayMonthList) {
        this.onDayMonthList = onDayMonthList;
    }

    public void addToOnDayMonthList(DayMonthType dayMonthType) {
        if (!onDayMonthList.contains(dayMonthType))
            onDayMonthList.add(dayMonthType);
    }

    public void removeFromOnDayMonthList(DayMonthType dayMonthType) {
        onDayMonthList.remove(dayMonthType);
    }

    public List<MonthType> getOnMonthList() {
        return onMonthList;
    }

    public void setOnMonthList(List<MonthType> onMonthList) {
        this.onMonthList = onMonthList;
    }

    public void addToOnMonthList(MonthType monthType) {
        if (!onMonthList.contains(monthType))
            onMonthList.add(monthType);
    }

    public void removeFromOnMonthList(MonthType monthType) {
        onMonthList.remove(monthType);
    }

    public String getOnString() {
        switch (type) {
            case MINUTE:
                return count + " " + type.getText();
            case HOUR:
                return count + " " + type.getText();
            case DAY:
                return count + " " + type.getText();
            case WEEK:
                return getOnWeekString();
            case MONTH:
                return getOnMonthDayString();
            case YEAR:
                return getOnMonthString();
            default:
                return "";
        }
    }

    private String getOnWeekString() {
        StringBuilder result = new StringBuilder();
        for (WeekType weekType: onWeekList) {
            result.append(weekType.getText());
        }
        return result.toString();
    }

    private String getOnMonthDayString() {
        StringBuilder result = new StringBuilder();
        for (DayMonthType dayMonthType : onDayMonthList) {
            result.append(dayMonthType.getTextNormal());
        }
        return result.toString();
    }

    private String getOnMonthString() {
        StringBuilder result = new StringBuilder();
        for (MonthType monthType: onMonthList) {
            result.append(monthType.getText());
        }
        return result.toString();
    }
}
