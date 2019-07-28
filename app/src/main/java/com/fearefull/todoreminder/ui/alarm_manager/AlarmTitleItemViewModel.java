package com.fearefull.todoreminder.ui.alarm_manager;

import androidx.databinding.ObservableField;

import com.fearefull.todoreminder.data.model.other.item.AlarmTitleItem;

public class AlarmTitleItemViewModel {
    private final AlarmTitleItem item;
    private final ObservableField<String> title;
    private final ObservableField<Integer> imageRes;
    private final AlarmTitleItemViewModelListener listener;

    public AlarmTitleItemViewModel(AlarmTitleItem item, AlarmTitleItemViewModelListener listener) {
        this.item = item;
        this.listener = listener;

        title = new ObservableField<>();
        imageRes = new ObservableField<>();
        title.set(item.getType().getText());
        imageRes.set(item.getType().getImageRes());
    }

    public AlarmTitleItem getItem() {
        return item;
    }

    public ObservableField<String> getTitle() {
        return title;
    }

    public ObservableField<Integer> getImageRes() {
        return imageRes;
    }

    public void onItemClick() {
        listener.onItemClick(item);
    }

    public interface AlarmTitleItemViewModelListener {
        void onItemClick(AlarmTitleItem item);
    }
}
