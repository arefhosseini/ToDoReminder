package com.fearefull.todoreminder.ui.alarm_manager;

import androidx.databinding.ObservableField;

import com.fearefull.todoreminder.data.model.other.RepeatType;
import com.fearefull.todoreminder.data.model.other.RepeatTypeItem;

public class RepeatItemViewModel {
    private final RepeatTypeItem repeatType;
    private final ObservableField<String> title = new ObservableField<>();
    private final RepeatItemViewModelListener listener;

    public RepeatItemViewModel(RepeatTypeItem repeatType,
                               RepeatItemViewModelListener listener) {
        this.repeatType = repeatType;
        this.title.set(repeatType.getRepeatType().getText());
        this.listener = listener;
    }

    public ObservableField<String> getTitle() {
        return title;
    }

    public RepeatTypeItem getRepeatType() {
        return repeatType;
    }

    public void onItemClick() {
        listener.onItemClick(repeatType);
    }

    public interface RepeatItemViewModelListener {
        void onItemClick(RepeatTypeItem repeatType);
    }
}
