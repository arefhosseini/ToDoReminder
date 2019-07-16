package com.fearefull.todoreminder.ui.home;


import com.fearefull.todoreminder.data.model.db.Alarm;

public class AlarmItemViewModel {
    private final Alarm alarm;
    private final AlarmItemViewModelListener listener;

    public AlarmItemViewModel(Alarm alarm, AlarmItemViewModelListener listener) {
        this.alarm = alarm;
        this.listener = listener;
    }

    public Alarm getAlarm() {
        return alarm;
    }

    public void onItemClick() {
        listener.onItemClick(alarm);
    }

    public interface AlarmItemViewModelListener {
        void onItemClick(Alarm alarm);
    }
}
