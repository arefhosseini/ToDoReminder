package com.fearefull.todoreminder.ui.alarm_manager;

import androidx.databinding.ObservableField;

import com.fearefull.todoreminder.data.model.other.item.RepeatItem;

public class RepeatItemViewModel {
    private final RepeatItem repeatItem;
    private final ObservableField<String> title = new ObservableField<>();
    private final RepeatItemViewModelListener listener;

    public RepeatItemViewModel(RepeatItem repeatItem, RepeatItemViewModelListener listener) {
        this.repeatItem = repeatItem;
        this.title.set(repeatItem.getRepeat().getText());
        this.listener = listener;
    }

    public ObservableField<String> getTitle() {
        return title;
    }

    public RepeatItem getRepeatItem() {
        return repeatItem;
    }

    public void onItemClick() {
        listener.onItemClick(repeatItem);
    }

    public interface RepeatItemViewModelListener {
        void onItemClick(RepeatItem repeatType);
    }
}
