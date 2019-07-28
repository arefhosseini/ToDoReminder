package com.fearefull.todoreminder.ui.alarm_manager;

import androidx.databinding.ObservableField;

import com.fearefull.todoreminder.data.model.other.item.RepeatItem;

public class RepeatItemViewModel {
    private final RepeatItem item;
    private final ObservableField<String> title = new ObservableField<>();
    private final RepeatItemViewModelListener listener;

    public RepeatItemViewModel(RepeatItem item, RepeatItemViewModelListener listener) {
        this.item = item;
        this.title.set(item.getRepeat().getText());
        this.listener = listener;
    }

    public ObservableField<String> getTitle() {
        return title;
    }

    public RepeatItem getItem() {
        return item;
    }

    public void onItemClick() {
        listener.onItemClick(item);
    }

    public interface RepeatItemViewModelListener {
        void onItemClick(RepeatItem repeatType);
    }
}
