package com.fearefull.todoreminder.ui.base;

import android.view.View;

import com.fearefull.todoreminder.data.model.db.Alarm;
import com.fearefull.todoreminder.ui.home.AlarmAdapter;
import com.fearefull.todoreminder.ui.home.AlarmItemViewModel;

public abstract class BaseAlarmViewHolder extends BaseViewHolder implements AlarmItemViewModel.AlarmItemViewModelListener{
    protected AlarmAdapter.AlarmAdapterListener listener;

    public BaseAlarmViewHolder(View itemView, AlarmAdapter.AlarmAdapterListener listener) {
        super(itemView);
        this.listener = listener;
    }

    public BaseAlarmViewHolder(View root) {
        super(root);
    }

    @Override
    public void onItemClick(Alarm alarm) {
        listener.onAlarmClick(alarm);
    }

    @Override
    public void onLongClick(Alarm alarm) {
        listener.onAlarmLongClick(alarm);
    }

    @Override
    public void onSwitchClick(Alarm alarm) {
        listener.onAlarmSwitchClick(alarm);
    }
}
