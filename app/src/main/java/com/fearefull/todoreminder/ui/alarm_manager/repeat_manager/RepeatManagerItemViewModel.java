package com.fearefull.todoreminder.ui.alarm_manager.repeat_manager;

import androidx.databinding.ObservableField;

import com.fearefull.todoreminder.data.model.other.item.RepeatManagerItem;

public class RepeatManagerItemViewModel {
    private final RepeatManagerItem item;
    private final RepeatManagerItemViewModelListener listener;
    private final ObservableField<String> text;

    public RepeatManagerItemViewModel(RepeatManagerItem item, RepeatManagerItemViewModelListener listener) {
        this.item = item;
        this.listener = listener;
        text = new ObservableField<>();
        text.set(item.getText());
    }

    public RepeatManagerItem getItem() {
        return item;
    }

    public void onRemoveClick() {
        listener.onRemoveClick(item);
    }

    public interface RepeatManagerItemViewModelListener {
        void onRemoveClick(RepeatManagerItem item);
    }

    public ObservableField<String> getText() {
        return text;
    }
}
