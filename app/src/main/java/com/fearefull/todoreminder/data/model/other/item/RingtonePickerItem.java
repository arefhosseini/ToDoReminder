package com.fearefull.todoreminder.data.model.other.item;

import com.fearefull.todoreminder.data.model.db.Ringtone;
import com.fearefull.todoreminder.databinding.ItemRingtonePickerBinding;

public class RingtonePickerItem {
    private final Ringtone ringtone;
    private boolean isDefault;
    private ItemRingtonePickerBinding binding;

    public RingtonePickerItem(Ringtone ringtone) {
        this.ringtone = ringtone;
        isDefault = false;
        binding = null;
    }

    public Ringtone getRingtone() {
        return ringtone;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    public ItemRingtonePickerBinding getBinding() {
        return binding;
    }

    public void setBinding(ItemRingtonePickerBinding binding) {
        this.binding = binding;
    }
}
