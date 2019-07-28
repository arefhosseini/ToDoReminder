package com.fearefull.todoreminder.ui.home;


import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

import com.fearefull.todoreminder.data.model.db.Alarm;

public class AlarmItemViewModel {
    private final Alarm alarm;
    private final AlarmItemViewModelListener listener;
    private final ObservableField<String> title;
    private final ObservableBoolean isEnabled;

    public AlarmItemViewModel(Alarm alarm, AlarmItemViewModelListener listener) {
        this.alarm = alarm;
        this.listener = listener;
        title = new ObservableField<>();
        isEnabled = new ObservableBoolean();

        title.set(alarm.getTitle());
        isEnabled.set(alarm.getIsEnable());
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

    public void onSwitchChanged(boolean checked) {
        alarm.setIsEnable(checked);
        listener.onSwitchClick(alarm);
    }

    public ObservableField<String> getTitle() {
        return title;
    }

    public ObservableBoolean getIsEnabled() {
        return isEnabled;
    }

    public interface AlarmItemViewModelListener {
        void onItemClick(Alarm alarm);
        void onLongClick(Alarm alarm);
        void onSwitchClick(Alarm alarm);
    }
}
