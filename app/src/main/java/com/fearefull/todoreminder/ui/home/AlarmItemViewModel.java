package com.fearefull.todoreminder.ui.home;


import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

import com.fearefull.todoreminder.data.model.db.Alarm;
import com.fearefull.todoreminder.data.model.db.Settings;

public class AlarmItemViewModel {
    private final Alarm alarm;
    private final AlarmItemViewModelListener listener;
    private final ObservableField<String> title;
    private final ObservableField<String> time;
    private final ObservableField<Integer> imageRes;
    private final ObservableBoolean isEnabled;
    private final ObservableBoolean isFirst;
    private final ObservableBoolean isLast;

    public AlarmItemViewModel(Alarm alarm, AlarmItemViewModelListener listener, Settings settings,
                              boolean isFirst, boolean isLast) {
        this.alarm = alarm;
        this.listener = listener;
        this.title = new ObservableField<>();
        this.time = new ObservableField<>();
        this.imageRes = new ObservableField<>();
        this.isEnabled = new ObservableBoolean();
        this.isFirst = new ObservableBoolean(isFirst);
        this.isLast = new ObservableBoolean(isLast);

        title.set(alarm.getTitle());
        if (alarm.getIsEnable())
            time.set(alarm.getNearestTimeString(settings.getHourType()));
        imageRes.set(alarm.getTitleType().getImageRes());
        isEnabled.set(alarm.getIsEnable());;
    }

    public Alarm getAlarm() {
        return alarm;
    }

    public void onItemClick() {
        listener.onItemClick(alarm);
    }

    public boolean onLongClick() {
        listener.onLongClick(alarm);
        return false;
    }

    public void onSwitchClick() {
        alarm.setIsEnable(!alarm.getIsEnable());
        listener.onSwitchClick(alarm);
    }

    public ObservableField<String> getTitle() {
        return title;
    }

    public ObservableField<String> getTime() {
        return time;
    }

    public ObservableField<Integer> getImageRes() {
        return imageRes;
    }

    public ObservableBoolean getIsEnabled() {
        return isEnabled;
    }

    public ObservableBoolean getIsFirst() {
        return isFirst;
    }

    public ObservableBoolean getIsLast() {
        return isLast;
    }

    public interface AlarmItemViewModelListener {
        void onItemClick(Alarm alarm);
        void onLongClick(Alarm alarm);
        void onSwitchClick(Alarm alarm);
    }
}
