package com.fearefull.todoreminder.ui.alarm_manager.repeat.weekly_repeat;

import androidx.databinding.ObservableField;

import com.fearefull.todoreminder.data.model.other.item.DayWeekItem;

public class DayWeekItemViewModel {
    private final DayWeekItem dayWeekItem;
    private final ObservableField<String> title;
    private final WeekItemViewModelListener listener;

    public DayWeekItemViewModel(DayWeekItem dayWeekItem, WeekItemViewModelListener listener) {
        this.dayWeekItem = dayWeekItem;
        title = new ObservableField<>();
        this.title.set(dayWeekItem.getType().getText());
        this.listener = listener;
    }

    public DayWeekItem getDayWeekItem() {
        return dayWeekItem;
    }

    public ObservableField<String> getTitle() {
        return title;
    }

    public void onItemClick() {
        listener.onItemClick(dayWeekItem);
    }

    public interface WeekItemViewModelListener {
        void onItemClick(DayWeekItem dayWeekItem);
    }
}
