package com.fearefull.todoreminder.ui.alarm_manager;

import androidx.databinding.ObservableField;

import com.fearefull.todoreminder.data.model.other.item.RepeatItem;

public class RepeatItemViewModel {
    private final RepeatItem repeatType;
    private final ObservableField<String> title = new ObservableField<>();
    private final RepeatItemViewModelListener listener;

    public RepeatItemViewModel(RepeatItem repeatType, RepeatItemViewModelListener listener) {
        this.repeatType = repeatType;
        this.title.set(repeatType.getRepeat().getText());
        this.listener = listener;
    }

    public ObservableField<String> getTitle() {
        return title;
    }

    public RepeatItem getRepeatType() {
        return repeatType;
    }

    public void onItemClick() {
        listener.onItemClick(repeatType);
    }

    public interface RepeatItemViewModelListener {
        void onItemClick(RepeatItem repeatType);
    }
}
