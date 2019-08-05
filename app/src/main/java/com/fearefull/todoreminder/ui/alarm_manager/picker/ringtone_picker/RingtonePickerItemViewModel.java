package com.fearefull.todoreminder.ui.alarm_manager.picker.ringtone_picker;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

import com.fearefull.todoreminder.data.model.db.Ringtone;
import com.fearefull.todoreminder.data.model.other.item.RingtonePickerItem;

public class RingtonePickerItemViewModel {
    private final RingtonePickerItem item;
    private final ObservableField<String> text;
    private ObservableBoolean isSelected;
    private final RingtonePickerItemViewModelListener listener;

    public RingtonePickerItemViewModel(RingtonePickerItem item, RingtonePickerItemViewModelListener listener) {
        this.item = item;
        text = new ObservableField<>(item.getRingtone().getTitle());
        isSelected = new ObservableBoolean(item.isDefault());
        this.listener = listener;
    }

    public RingtonePickerItem getItem() {
        return item;
    }

    public ObservableField<String> getText() {
        return text;
    }

    public ObservableBoolean getIsSelected() {
        return isSelected;
    }

    public void changeIsSelected() {
        isSelected.set(!isSelected.get());
    }

    public void onClick() {
        isSelected.set(true);
        listener.onItemClick(item);
    }

    public interface RingtonePickerItemViewModelListener {
        void onItemClick(RingtonePickerItem item);
    }
}
